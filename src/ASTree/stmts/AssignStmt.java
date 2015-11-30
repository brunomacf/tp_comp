package MASB.ASTree.Stmts;

import MASB.ASTree.ASTreeNode;
import MASB.CodeGen.*;

public class AssignStmt extends ASTreeNode {
   private ASTreeNode left;
   private ASTreeNode right;

   public AssignStmt(ASTreeNode left, ASTreeNode right) {
      this.left = left;
      this.right = right;
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      // Gera o código para o nó esquerdo e direito.
      Quadruple leftQ = this.left.codeGen(ctxt);
      Quadruple rightQ = this.right.codeGen(ctxt);

      // Se left ou right é nulo, então algum erro aconteceu.
      if(leftQ == null || rightQ == null) {
         throw new RuntimeException("AssignStmt : left e right não podem ser nulos.");
      }

      // Se precisa fazer CAST, então devemos adicionar uma operação de casting
      // neste ponto.
      // ************** TRATAR CASTING

      // Cria uma nova quádrupla que representa esta atribuição.
      Quadruple q = new Quadruple(Quadruple.Tag.COPY, leftQ.result(), rightQ.result());

      // Adiciona a nova quádrupla à lista de quádruplas do contexto.
      ctxt.addQuadruple(q);

      // Statments não precisam retornar quádruplas.
      return null;
   }
}
