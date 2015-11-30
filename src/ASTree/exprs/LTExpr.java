package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class LTExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public LTExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.LT, left, right);
   }
}
