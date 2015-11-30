package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class EqExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public EqExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.EQ, left, right);
   }
}
