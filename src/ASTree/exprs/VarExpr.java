package MASB.ASTree.Exprs;

import java.util.ArrayList;

import MASB.SymbolTable.SymbolTableEntry;
import MASB.SymbolTable.Type;
import MASB.CodeGen.*;
import MASB.ASTree.*;


public class VarExpr extends ASTreeNode {
   private SymbolTableEntry entry;
   private ArrayList<ASTreeNode> idxs;

   public VarExpr(SymbolTableEntry entry) {
      this.entry = entry;
      this.idxs = new ArrayList<ASTreeNode>();
      this.setTypeTag(entry.type().tag());
   }

   // Getters
   public ArrayList<ASTreeNode> idxs() { return this.idxs; }

   /**
    * Essa função gera o código a partir deste nó.
    */
   @Override public Quadruple codeGen(CodeGenContext ctxt) {

      // Se existem expressões como índices da variável (no caso de uma variável
      // do tipo array), então precisamos calcular primeiro esses índices.
      if(this.idxs.size() > 0) {
         // Suponha um array de inteiros a[N1]..[Nm] e suponha que queremos
         // acessar o elemento a[i1]..[im]. Para calcular o índice i "linear"
         // desse elemento, não é muito difícil mostrar que:
         //
         // i = i1 + i2*(N1) + i3*(N1*N2) ... + i{m-1}(N1*...*N{m-2}) + ik(N1*...*N{m-1})
         //
         // Dessa forma, vamos calcular o tamanho NN_k = N1*...*Nk de forma cumulativa
         // e chamaremos NN_k de cumulativeSize por legibilidade.
         int cumulativeSize = 1;

         // Criamos um temporário para armazenar o cálculo do i "linear".
         String linTmp = ctxt.createTemp(Type.Tag.INT);

         // Geramos o código que fornece o primeiro índice i1 do elemento que
         // queremos acessar.
         Quadruple kIdxQ = this.idxs.get(0).codeGen(ctxt);

         // Colocamos o primeiro índice no temporário.
         ctxt.addQuadruple(new Quadruple(Quadruple.Tag.COPY, linTmp, kIdxQ.result()));

         for(int k = 1; k < this.idxs.size(); k++) {

            // Geramos o termo NN_k = N1*...Nk.
            cumulativeSize *= this.entry.type().dims().get(k);

            // Geramos o código que fornece o k-ésimo índice ik do elemento que
            // queremos acessar.
            kIdxQ = this.idxs.get(k).codeGen(ctxt);

            // Criamos um temporário para armazenar o resultado da multiplicação
            // de ik*NN_k.
            String tmp = ctxt.createTemp(Type.Tag.INT);

            // Realizamos a multiplicação ik*NN_k e armazenamos no temporário.
            ctxt.addQuadruple(new Quadruple(Quadruple.Tag.MUL, tmp, kIdxQ.result()));

            // Adicionamos o valor da multiplicação ao valor acumulado em linTemp.
            ctxt.addQuadruple(new Quadruple(Quadruple.Tag.ADD, linTmp, linTmp, tmp));
         }

         // Nesse momento temos o índice "linear" do elemento que vamos acessar e, portanto,
         // basta agora multiplicar esse valor pelo tamanho do tipo do array.
         ctxt.addQuadruple(new Quadruple(Quadruple.Tag.MUL, linTmp, linTmp, String.valueOf(this.entry.type().tag().size())));

         // Por fim, vamos retornar uma quadrupla que representa a variável.
         return new Quadruple(Quadruple.Tag.VARIABLE, String.format("%s%d[%s]", this.entry.id(), this.entry.tableIdx(), linTmp));
      }
      // Caso contrário, trata-se de uma variável simples.
      else {
         return new Quadruple(Quadruple.Tag.VARIABLE, String.format("%s%d", this.entry.id(), this.entry.tableIdx()));
      }
   };
}
