package MASB.ASTree.Exprs;

import MASB.ASTree.*;
import MASB.CodeGen.*;
import MASB.SymbolTable.Type;

public class FloatLiteralExpr extends ASTreeNode {
   private double value;

   public FloatLiteralExpr(String value) {
      this.value = Double.parseDouble(value);
      this.setTypeTag(Type.Tag.FLOAT);
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      return new Quadruple(Quadruple.Tag.LITERAL, String.valueOf(this.value));
   };
}
