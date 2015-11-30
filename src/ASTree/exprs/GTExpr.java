package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class GTExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public GTExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.GT, left, right);
   }
}
