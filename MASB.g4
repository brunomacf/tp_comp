grammar MASB;

/* Declarações extras adicionadas ao header do lexer gerado. */
@lexer::header {
   package MASB;
}

/* Declarações extras adicionadas ao header do parser gerado. */
@parser::header {
   package MASB;

   import java.util.LinkedList;

   import MASB.ASTree.*;
   import MASB.ASTree.Exprs.*;
   import MASB.ASTree.Stmts.*;
   import MASB.SymbolTable.*;
   import MASB.CodeGen.*;
}

/* Membros que serão incluídos na classe do parser gerado. */
@parser::members {
   private SymbolTable table = new SymbolTable();
}

/*============================================================*/
// Regras do Parser
/*============================================================*/
program returns [ASTreeNode root, SymbolTable table]
   : block EOF {
      $root = $block.node;
      $table = table;
   }
   ;

block returns [ASTreeNode node] locals [LinkedList<ASTreeNode> list]
   @init {
      // Quando detectamos um novo bloco de declarações vamos
      // iniciar um novo escopo na tabela de símbolos.
      table.createScope();

      // Criamos a lista de statments do bloco.
      $list = new LinkedList<ASTreeNode>();
   }
   : '{' dcls stmts[$list]? '}' {
      $node = new BlockStmt($list);
   }
   ;

dcls
   : dcl dcls
   | /*epsilon*/
   ;

dcl
   : type Id ';' {
      // Adicionamos o id encontrado à tabela de símbolos.
      table.installEntry($Id.text, $type.bType);

      // Imprimimos a nova tabela.
      table.print();
   }
   ;

// Reconhecedor de tipo
type returns [Type bType]
   : t=type '[' Int ']' {
      // Adiciona uma nova dimensão ao tipo base de t.
      $t.bType.addDim($Int.text);

      // Especifica que o tipo base do type (esquerdo) é o mesmo tipo base de t
      // (direito).
      $bType = $t.bType;
   }
   | Basic {
      // Cria o tipo base de acordo com a string encontrada no código fonte.
      $bType = new Type(Type.Tag.fromString($Basic.text));
   }
   ;

// Reconhecedor de statements : Retorna a lista de nós que representam os
// statements encontrados.
stmts[LinkedList<ASTreeNode> list]
   : stmt stmts[$list]? {
      $list.addFirst($stmt.node);
   }
   | /*epsilon*/
   ;

stmt returns [ASTreeNode node]
   : loc '=' bool ';'                           { $node = new AssignStmt($loc.node, $bool.node); }
   | 'if' '(' bool ')' s=stmt                   { $node = new IfElseStmt($bool.node, $s.node, null); }
   | 'if' '(' bool ')' s1=stmt 'else' s2=stmt   { $node = new IfElseStmt($bool.node, $s1.node, $s2.node); }
   | 'while' '(' bool ')' s=stmt                { $node = new WhileStmt($bool.node, $s.node); }
   | 'do' s=stmt 'while' '(' bool ')' ';'       { $node = new DoWhileStmt($s.node, $bool.node); }
   | 'break' ';'                                { $node = new BreakStmt(); }
   | block                                      { $node = $block.node; }
   ;

loc returns [ASTreeNode node]
   : Id {
      // Quando encontramos um id no código relacionado a atribuição, então
      // devemos recuperá-lo da tabela de símbolos.
      SymbolTableEntry entry = table.getEntry($Id.text);

      // Se o símbolo não foi encontrado, então lançamos um erro de símbolo não
      // declarado.
      if(entry == null) {
         throw new RuntimeException("Variável '" + $Id.text + "' não declarada");
      }

      // Caso contrário criamos um nó de variável.
      $node = new VarExpr(entry);
   }
   | l=loc '[' bool ']' {
      $node = $l.node;

   }
   ;

bool returns [ASTreeNode node]
   : b=bool '||' join               { $node = new OrExpr($b.node, $join.node); }
   | join                           { $node = $join.node; }
   ;

join returns [ASTreeNode node]
   : j=join '&&' equality           { $node = new AndExpr($j.node, $equality.node); }
   | equality                       { $node = $equality.node; }
   ;

equality returns [ASTreeNode node]
   : e=equality '==' rel            { $node = new EqExpr($e.node, $rel.node); }
   | e=equality '!=' rel            { $node = new NeqExpr($e.node, $rel.node); }
   | rel                            { $node = $rel.node; }
   ;

rel returns [ASTreeNode node]
   : e1=expr '<' e2=expr            { $node = new LTExpr($e1.node, $e2.node); }
   | e1=expr '<=' e2=expr           { $node = new LTEqExpr($e1.node, $e2.node); }
   | e1=expr '>' e2=expr            { $node = new GTExpr($e1.node, $e2.node); }
   | e1=expr '>=' e2=expr           { $node = new GTEqExpr($e1.node, $e2.node); }
   | expr                           { $node = $expr.node; }
   ;

expr returns [ASTreeNode node]
   : e=expr '+' term                { $node = new AddExpr($e.node, $term.node); }
   | e=expr '-' term                { $node = new SubExpr($e.node, $term.node); }
   | term                           { $node = $term.node; }
   ;

term returns [ASTreeNode node]
   : t=term '*' unary               { $node = new MulExpr($t.node, $unary.node); }
   | t=term '/' unary               { $node = new DivExpr($t.node, $unary.node); }
   | unary                          { $node = $unary.node; }
   ;

unary returns [ASTreeNode node]
   : '!' unary                      { $node = new NotExpr($unary.node); }
   | '-' unary                      { $node = new MinusExpr($unary.node); }
   | factor                         { $node = $factor.node; }
   ;

factor returns [ASTreeNode node]
   : Int                            { $node = new IntLiteralExpr($Int.text); }
   | Float                          { $node = new IntLiteralExpr($Float.text); }
   | Bool                           { $node = new BoolLiteralExpr($Bool.text); }
   | '(' bool ')'                   { $node = $bool.node; }
   | loc                            { $node = $loc.node; }
   ;


/*============================================================*/
// Regras do Lexer
/*============================================================*/
Comment
   : '//' ~('\r' | '\n')* -> skip
   ;

MultilineComment
   : '/*' .*? '*/' -> skip
   ;

Whitespace
   : [ \t\r\n] -> skip
   ;

Int
   : [0-9]+
   ;

Float
   : [0-9]+ '.' [0-9]+
   ;

Bool
   : 'true'
   | 'false'
   ;

Basic
   : 'int'
   | 'float'
   | 'char'
   | 'bool'
   ;

Id
   : [a-zA-Z_] [a-zA-Z0-9_]*
   ;
