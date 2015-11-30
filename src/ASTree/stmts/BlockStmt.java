package MASB.ASTree.Stmts;

import java.util.LinkedList;
import MASB.ASTree.ASTreeNode;
import MASB.CodeGen.*;

public class BlockStmt extends ASTreeNode {
   private LinkedList<ASTreeNode> stmts;

   public BlockStmt(LinkedList<ASTreeNode> stmts) {
      this.stmts = stmts;
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      for (ASTreeNode stmt : stmts) {
         stmt.codeGen(ctxt);
      }

      return null;
   }

   public LinkedList<ASTreeNode> stmts() { return this.stmts; }
}
