package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class BinaryLogicExpr extends BinaryOpExpr {
   private Quadruple.Tag opTag;
   private ASTreeNode left;
   private ASTreeNode right;

   /**
    * Construtor padrão protegido.
    * @param   opTag    Tag da operação.
    * @param   left     Nó esquerdo.
    * @param   right    Nó direito.
    * @return  Uma expressão de operação binária.
    */
   protected BinaryLogicExpr(Quadruple.Tag opTag, ASTreeNode left, ASTreeNode right) {
      super(opTag, left, right);
   }
}
