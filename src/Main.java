import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import MASB.*;
import MASB.CodeGen.*;
import MASB.SymbolTable.*;

public class Main {
  public static void main(String[] args) throws Exception {
     MASBLexer lexer = new MASBLexer(new ANTLRFileStream(args[0]));
     MASBParser parser = new MASBParser(new CommonTokenStream(lexer));

     MASBParser.ProgramContext ctxt = parser.program();

     // Cria um contexto para a geração do código intermediário.
     CodeGenContext genCtxt = new CodeGenContext(ctxt.table);

     // Gera o código no contexto de geração usando a ASTree.
     ctxt.root.codeGen(genCtxt);

     // Imprime o código gerado pelo contexto de geração.
     genCtxt.codePrint();
  }
}
