package MASB.ASTree.Stmts;

import java.util.LinkedList;
import MASB.ASTree.ASTreeNode;
import MASB.CodeGen.*;

public class BreakStmt extends ASTreeNode {

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      if(ctxt.loopJumpOutStack().empty()) {
         throw new RuntimeException("BreakStmt : break deve ser definido dentro de algum loop.");
      }

      // Obtém o label de jump out do loop atual.
      String jumpOutLabel = ctxt.loopJumpOutStack().peek();

      // Adiciona o código para o break como sendo um IF que executa necessáriamente.
      ctxt.addQuadruple(new Quadruple(Quadruple.Tag.IF, null, "true", jumpOutLabel));

      // Statments não precisam retornar quádruplas.
      return null;
   }
}
