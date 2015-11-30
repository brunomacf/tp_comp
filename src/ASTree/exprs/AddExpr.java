package MASB.ASTree.Exprs;

import MASB.CodeGen.*;
import MASB.ASTree.*;
import MASB.SymbolTable.*;

public class AddExpr extends BinaryArithmExpr {
   /**
    * Construtor público.
    */
   public AddExpr(ASTreeNode left, ASTreeNode right) {
      // Chama o construtor protegido da classe pai (BinaryArithmExpr).
      super(Quadruple.Tag.ADD, left, right);

      // Vamos determinar o tipo deste nó baseado no tipo dos operandos.
      if(left.typeTag() == Type.Tag.FLOAT || left.typeTag() == Type.Tag.FLOAT) {
         this.setTypeTag(Type.Tag.FLOAT);
      }
      else if(left.typeTag() == Type.Tag.INT || right.typeTag() == Type.Tag.INT) {
         this.setTypeTag(Type.Tag.INT);
      }
      else if(left.typeTag() == Type.Tag.CHAR || right.typeTag() == Type.Tag.CHAR) {
         this.setTypeTag(Type.Tag.CHAR);
      }
      else {
         this.setTypeTag(Type.Tag.BOOL);
      }
   }
}
