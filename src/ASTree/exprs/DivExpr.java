package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class DivExpr extends BinaryArithmExpr {
   /**
    * Construtor público.
    */
   public DivExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryArithmExpr).
      super(Quadruple.Tag.DIV, left, right);
   }
}
