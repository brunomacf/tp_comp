package MASB.ASTree.Exprs;

import MASB.ASTree.*;
import MASB.CodeGen.*;
import MASB.SymbolTable.Type;

public class BoolLiteralExpr extends ASTreeNode {
   private boolean value;

   public BoolLiteralExpr(String value) {
      this.value = Boolean.parseBoolean(value);
      this.setTypeTag(Type.Tag.BOOL);
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      return new Quadruple(Quadruple.Tag.LITERAL, String.valueOf(this.value));
   };
}
