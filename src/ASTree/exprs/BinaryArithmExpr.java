package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;

public class BinaryArithmExpr extends BinaryOpExpr {

   /**
    * Construtor padrão protegido.
    * @param   opTag    Tag da operação.
    * @param   left     Nó esquerdo.
    * @param   right    Nó direito.
    * @return  Uma expressão de operação binária aritmética.
    */
   protected BinaryArithmExpr(Quadruple.Tag opTag, ASTreeNode left, ASTreeNode right) {
      super(opTag, left, right);
   }
}
