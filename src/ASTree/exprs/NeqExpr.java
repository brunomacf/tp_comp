package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class NeqExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public NeqExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.NEQ, left, right);
   }
}
