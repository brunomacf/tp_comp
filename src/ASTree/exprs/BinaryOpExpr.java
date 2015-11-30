package MASB.ASTree.Exprs;

import MASB.ASTree.*;
import MASB.CodeGen.*;

public class BinaryOpExpr extends ASTreeNode {
   private Quadruple.Tag opTag;
   private ASTreeNode left;
   private ASTreeNode right;

   /**
    * Construtor padrão protegido.
    * @param   op       Operação desta expressão de operação aritmética.
    * @param   left     Nó esquerdo.
    * @param   right    Nó direito.
    * @return  Uma expressão de operação binária.
    */
   protected BinaryOpExpr(Quadruple.Tag opTag, ASTreeNode left, ASTreeNode right) {
      this.opTag = opTag;
      this.left = left;
      this.right = right;
   }

   /**
    * Sobrescreve a função de geração de código da classe ASTreeNode.
    */
   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      // Gera o código para o nó esquerdo e direito.
      Quadruple leftQ = this.left.codeGen(ctxt);
      Quadruple rightQ = this.right.codeGen(ctxt);

      // Se left ou right é nulo, então algum erro aconteceu.
      if(leftQ == null || rightQ == null) {
         throw new RuntimeException("BinaryOpExpr : left e right não podem ser nulos.");
      }

      // Gera um novo temporário no contexto para armazenar o resultado da
      // operação.
      String tmp = ctxt.createTemp(this.typeTag());

      // Cria uma nova quádrupla que representa esta operação aritmética.
      Quadruple q = new Quadruple(this.opTag, tmp, leftQ.result(), rightQ.result());

      // Adiciona a nova quádrupla à lista de quádruplas do contexto.
      ctxt.addQuadruple(q);

      // Retorna a quádrupla.
      return q;
   };
}
