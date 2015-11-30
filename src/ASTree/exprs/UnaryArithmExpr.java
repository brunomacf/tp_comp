package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class UnaryArithmExpr extends UnaryOpExpr {

   /**
    * Construtor padrão protegido.
    * @param   opTag    Tag da operação.
    * @param   left     Nó único.
    * @return  Uma expressão de operação unária aritmética.
    */
   protected UnaryArithmExpr(Quadruple.Tag opTag, ASTreeNode left) {
      super(opTag, left);
   }
}
