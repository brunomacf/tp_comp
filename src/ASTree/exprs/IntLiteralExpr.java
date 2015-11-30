package MASB.ASTree.Exprs;

import MASB.ASTree.*;
import MASB.CodeGen.*;
import MASB.SymbolTable.Type;

public class IntLiteralExpr extends ASTreeNode {
   private int value;

   public IntLiteralExpr(String value) {
      this.value = Integer.parseInt(value);
      this.setTypeTag(Type.Tag.INT);
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      return new Quadruple(Quadruple.Tag.LITERAL, String.valueOf(this.value));
   };
}
