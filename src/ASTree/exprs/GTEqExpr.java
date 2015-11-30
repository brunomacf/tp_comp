package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class GTEqExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public GTEqExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.GTE, left, right);
   }
}
