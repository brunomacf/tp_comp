package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class SubExpr extends BinaryArithmExpr {
   /**
    * Construtor público.
    */
   public SubExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryArithmExpr).
      super(Quadruple.Tag.SUB, left, right);
   }
}
