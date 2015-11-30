package MASB.ASTree.Stmts;

import MASB.ASTree.ASTreeNode;
import MASB.CodeGen.*;

public class WhileStmt extends ASTreeNode {
   private ASTreeNode condition;
   private ASTreeNode block;

   public WhileStmt(ASTreeNode condition, ASTreeNode block) {
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

      // Adicionamos o código para verificar a condição do while. Fazemos a
      // verificação da condição
      Quadruple conditionQ = this.condition.codeGen(ctxt);

      // Se a condição for falsa, então devemos pular para fora do bloco do
      // while.
      ctxt.addQuadruple(new Quadruple(Quadruple.Tag.IFFALSE, null, conditionQ.result(), jumpOutLabel));

      // Geramos o código para o bloco do while.
      this.block.codeGen(ctxt);

      // Se a condição for verdadeira, então devemos executar o bloco do while
      // e em seguida devemos pular para o ínício da avaliação da condição.
      // Observe que conditionQ.result() referencia a variável temporária que
      // armazenou a última avaliação da condição, então podemos utilizá-la
      // novamente sem risco (já que nunca reutilizamos temporários).
      ctxt.addQuadruple(new Quadruple(Quadruple.Tag.IF, null, conditionQ.result(), jumpInLabel));

      // Adicionamos o label para sair do bloco do while.
      ctxt.addJumpLabel(jumpOutLabel);

      // Removemos o jump out após geramos todo o código responsável pelo while.
      ctxt.loopJumpOutStack().pop();

      // Statments não precisam retornar quádruplas.
      return null;
   }
}
