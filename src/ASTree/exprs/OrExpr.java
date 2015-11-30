package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class OrExpr extends BinaryLogicExpr {
   /**
    * Construtor público.
    */
   public OrExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryLogicExpr).
      super(Quadruple.Tag.OR, left, right);
   }
}
