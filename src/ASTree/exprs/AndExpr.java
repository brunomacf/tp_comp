package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class AndExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public AndExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.AND, left, right);
   }
}
