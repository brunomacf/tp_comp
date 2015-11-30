package MASB.ASTree.Stmts;

import MASB.ASTree.ASTreeNode;
import MASB.CodeGen.*;

public class DoWhileStmt extends ASTreeNode {
   private ASTreeNode condition;
   private ASTreeNode block;

   public DoWhileStmt(ASTreeNode block, ASTreeNode condition) {
      this.condition = condition;
      this.block = block;
   }

   @Override public Quadruple codeGen(CodeGenContext ctxt) {
      String jumpInLabel = ctxt.createJumpLabel();
      String jumpOutLabel = ctxt.createJumpLabel();

      // Adicionamos o jumpOutLabel ao stack de labels de jump outs de loops
      // armazenados no contexto. Isso é importante por causa da declaração de
      // BREAKS no interior do loop.
      ctxt.loopJumpOutStack().push(jumpOutLabel);

      // Adicionamos o jump in label na posição corrente do código gerado.
      // Observer que o jump in está localizado antes do código para a condição
      // do while. Dessa forma, sempre que entrarmos no while novamente iremos
      // primeiro avaliar a condição.
      ctxt.addJumpLabel(jumpInLabel);

      // Primeiro geramos o código para o bloco do while.
      this.block.codeGen(ctxt);

      // Adicionamos o código para verificar a condição do while. Fazemos a
      // verificação da condição
      Quadruple conditionQ = this.condition.codeGen(ctxt);

      // Se a condição for verdadeira, então precisamos pular novamente para
      // dentro do while e executá-lo novamente. Caso contrário simplesmente
      // ignoramos e condinuamos.
      ctxt.addQuadruple(new Quadruple(Quadruple.Tag.IF, null, conditionQ.result(), jumpInLabel));

      // Removemos o jump out após geramos todo o código responsável pelo while.
      ctxt.loopJumpOutStack().pop();

      // Statments não precisam retornar quádruplas.
      return null;
   }
}
