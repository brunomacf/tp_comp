package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class MulExpr extends BinaryArithmExpr {
   /**
    * Construtor público.
    */
   public MulExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryArithmExpr).
      super(Quadruple.Tag.MUL, left, right);
   }
}
