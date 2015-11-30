package MASB.ASTree.Exprs;

import MASB.ASTree.*;
import MASB.CodeGen.*;

public class UnaryOpExpr extends ASTreeNode {
   private Quadruple.Tag opTag;
   private ASTreeNode left;

   /**
    * Construtor padrão protegido.
    * @param   op       Operação desta expressão de operação unária.
    * @param   left     Único nó.
    * @return  Uma expressão de operação unária.
    */
   protected UnaryOpExpr(Quadruple.Tag opTag, ASTreeNode left) {
      this.opTag = opTag;
      this.left = left;
   }

   /**
    * Sobrescreve a função de geração de código da classe ASTreeNode.
    */
   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      // Gera o código para o nó único.
      Quadruple leftQ = this.left.codeGen(ctxt);

      // Se left ou right é nulo, então algum erro aconteceu.
      if(leftQ == null) {
         throw new RuntimeException("UnaryOpExpr : left não pode ser nulo.");
      }

      // Gera um novo temporário no contexto para armazenar o resultado da
      // operação.
      String tmp = ctxt.createTemp(this.typeTag());

      // Cria uma nova quádrupla que representa esta operação unária.
      Quadruple q = new Quadruple(this.opTag, tmp, leftQ.result());

      // Adiciona a nova quádrupla à lista de quádruplas do contexto.
      ctxt.addQuadruple(q);

      // Retorna a quádrupla.
      return q;
   };
}
