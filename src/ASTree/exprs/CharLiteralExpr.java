package MASB.ASTree.Exprs;

import MASB.ASTree.*;
import MASB.CodeGen.*;
import MASB.SymbolTable.Type;

public class CharLiteralExpr extends ASTreeNode {
   private char value;

   public CharLiteralExpr(String value) {
      this.value = value.charAt(0);
      this.setTypeTag(Type.Tag.CHAR);
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      return new Quadruple(Quadruple.Tag.LITERAL, String.valueOf(this.value));
   };
}
