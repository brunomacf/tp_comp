grammar MASB;

/* Declarações extras adicionadas ao header do lexer gerado. */
@lexer::header {
   package MASB;
}

/* Declarações extras adicionadas ao header do parser gerado. */
@parser::header {
   package MASB;

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

      // Imprimimos a nova tabela.
      table.print();
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
stmts returns [LinkedList<ASTreeNode> list]
   : stmt stmts[$list]? {
      $list.addFirst($stmt.node);
   }
   | /*epsilon*/
   ;

stmt returns [ASTreeNode node]
   : loc '=' bool ';'                        {}
   | 'if' '(' bool ')' stmt
   | 'if' '(' bool ')' stmt 'else' stmt
   | 'while' '(' bool ')' stmt
   | 'do' stmt 'while' '(' bool ')' ';'
   | 'break' ';'
   | block
   ;

loc
   : loc '[' bool ']'
   | Id {
      // Quando encontramos um id no código relacionado a atribuição, então
      // devemos recuperá-lo da tabela de símbolos.
      SymbolTableEntry entry = table.getEntry($Id.text);

      // Se o símbolo não foi encontrado, então lançamos um erro de símbolo não
      // declarado.
      if(entry == null) {
         throw new RuntimeException("Variável '" + $Id.text + "' não declarada");
      }

      // Caso contrário criamos um nó de variável.
   }
   ;

bool
   : bool '||' join
   | join
   ;

join
   : join '&&' equality
   | equality
   ;

equality
   : equality '==' rel
   | equality '!=' rel
   | rel
   ;

rel
   : expr '<' expr
   | expr '<=' expr
   | expr '>' expr
   | expr '>=' expr
   | expr
   ;

expr
   : expr '+' term
   | expr '-' term
   | term
   ;

term
   : term '*' unary
   | term '/' unary
   | unary
   ;

unary
   : '!' unary
   | '-' unary
   | factor
   ;

factor
   : '(' bool ')'
   | loc
   | Int
   | Float
   | Bool
   ;
