package MASB.CodeGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import MASB.SymbolTable.*;

public class CodeGenContext {
   private SymbolTable table;
   private ArrayList<Quadruple> quadruples;
   private Stack<String> loopJumpOutStack;
   private HashMap<Integer, String> labelsMap;
   private int tmpCount;
   private int labelCount;

   /**
    * Essa função gera um novo contexto de geração de código intermediário
    * utilizando uma tabela de símbolos específica.
    * @param  table Tabela de símbolos a ser utilizada para os temporários.
    * @return       Contexto do gerador de código intermediário.
    */
   public CodeGenContext(SymbolTable table) {
      this.table = table;
      this.quadruples = new ArrayList<Quadruple>();
      this.loopJumpOutStack = new Stack<String>();
      this.labelsMap = new HashMap<Integer, String>();
      this.tmpCount = 0;
      this.labelCount = 0;
   }

   // Getters
   public Stack<String> loopJumpOutStack() { return this.loopJumpOutStack; }

   /**
    * Essa função cria um novo label para uma instrução do código intermediário.
    * @return Identificador do label.
    */
   public String createJumpLabel() {
      String nextLabel = ("L" + this.labelCount);
      this.labelCount++;
      return nextLabel;
   }

   /**
    * Essa função cria e instala uma nova variável temporária na tabela de
    * símbolos.
    * @return Identificador da variável temporária inserida.
    */
   public String createTemp(Type.Tag typeTag) {
      String tmpId = "t" + this.tmpCount;
      tmpCount++;

      // Adiciona o temporário na tabela de símbolos.
      this.table.installEntry(tmpId, new Type(typeTag));

      return tmpId;
   }

   /**
    * Essa função adiciona uma nova quádrupla na lista de quádruplas deste
    * contexto de geração de código.
    * @param quadruple Quádrupla a ser adicionada na lista.
    */
   public void addQuadruple(Quadruple quadruple) {
      this.quadruples.add(quadruple);
   }

   /**
    * Essa função adiciona um jump label à tabela de jump labels.
    * @param jumpLabel O jump label que será adicionado à lista.
    */
   public void addJumpLabel(String jumpLabel) {
      this.labelsMap.put(new Integer(this.quadruples.size()), jumpLabel);
   };


   /**
    * Essa função gera o código de três endereços com base nas quádruplas deste
    * contexto.
    */
   public void codePrint() {
      System.out.println("###################################");
      System.out.println("CodeGenContext : codePrint");
      System.out.println("-----------------------------------");
      for(int i = 0; i < this.quadruples.size(); i++) {
         String q = this.quadruples.get(i).toString();
         String label = this.labelsMap.get(i);

         if(label != null) { System.out.println(label+" : \t"+q);}
         else { System.out.println("\t"+q); }
      }
      System.out.println("###################################End");
   };
}
