package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class LTEqExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public LTEqExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.LTE, left, right);
   }
}
