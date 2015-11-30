package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class NotExpr extends UnaryArithmExpr {
   /**
    * Construtor público.
    */
   public NotExpr(ASTreeNode left) {
      // Chama o construtor protegido da classe pai (UnaryArithmExpr).
      super(Quadruple.Tag.NOT, left);
   }
}
