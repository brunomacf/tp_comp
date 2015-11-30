package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class MinusExpr extends UnaryArithmExpr {
   /**
    * Construtor público.
    */
   public MinusExpr(ASTreeNode left) {
      // Chama o construtor protegido da classe pai (UnaryArithmExpr).
      super(Quadruple.Tag.MINUS, left);
   }
}
