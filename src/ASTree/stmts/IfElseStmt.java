package MASB.ASTree.Stmts;

import MASB.ASTree.ASTreeNode;
import MASB.CodeGen.*;

public class IfElseStmt extends ASTreeNode {
   private ASTreeNode condition;
   private ASTreeNode ifBlock;
   private ASTreeNode elseBlock;

   public IfElseStmt(ASTreeNode condition, ASTreeNode ifBlock, ASTreeNode elseBlock) {
      this.condition = condition;
      this.ifBlock = ifBlock;
      this.elseBlock = elseBlock;
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      String elseJumpInLabel = ctxt.createJumpLabel();

      // Gera e trata o código para o nó filho de condição do IF. Observe que a
      // quádrupla significa que se a condição do if for falsa, então pule para
      // o endereço identificado por elseJumpInLabel (i.e., sai do if indo para
      // um else ou continuando para as outras quádruplas).
      Quadruple conditionQ = this.condition.codeGen(ctxt);
      ctxt.addQuadruple(new Quadruple(Quadruple.Tag.IFFALSE, null, conditionQ.result(), elseJumpInLabel));

      // Gera o código para o nó filho do bloco do IF (que será executado se
      // a condição do if for verdadeira).
      Quadruple ifBlockQ = this.ifBlock.codeGen(ctxt);

      // Adiciona o jump label na posição corrente do contexto. Pularemos para
      // esta posição caso a condição do if não for satisfeita.
      ctxt.addJumpLabel(elseJumpInLabel);

      // Se existe um bloco de else, então o tratamos.
      if(this.elseBlock != null) {
         String elseJumpOutLabel = ctxt.createJumpLabel();

         // Se a condição do if foi satisfeita, então pulamos pra fora do else
         // (i.e., não o executamos).
         ctxt.addQuadruple(new Quadruple(Quadruple.Tag.IF, null, conditionQ.result(), elseJumpOutLabel));

         // Geramos o código do else.
         this.elseBlock.codeGen(ctxt);

         // Adiciona o jump label na posição corrente do contexto.
         ctxt.addJumpLabel(elseJumpOutLabel);
      }

      // Statments não precisam retornar quádruplas.
      return null;
   }
}
