/**
 * Esse arquivo implementa uma tabela de símbolos como uma floresta de árvores
 * binárias.
 */

package MASB.SymbolTable;

public class SymbolTable {

   public static int MAX_SIZE = 100;
   public static int MAX_SCOPES_SIZE = 100;

   private int[] scopes;
   private int activeScope;
   private int nextAvailableAddress;
   private SymbolTableEntry[] table;

   /**
    * Construtor da tabela de símbolos.
    * @return Referência para a tabela de símbolos criada.
    */
   public SymbolTable() {
      this.scopes = new int[SymbolTable.MAX_SCOPES_SIZE];
      this.activeScope = -1;
      this.table = new SymbolTableEntry[SymbolTable.MAX_SIZE];

      // Inicializa scopes
      for(int i = 0; i < SymbolTable.MAX_SCOPES_SIZE; i++) {
         this.scopes[i] = -1;
      }
   }

   /**
    * Essa função cria um novo escopo de variáveis na tabela de símbolos, ou
    * seja, cria uma nova árvore na floresta que representa a tabela.
    */
   public void createScope() {
      if(this.activeScope == SymbolTable.MAX_SCOPES_SIZE-1) {
         throw new RuntimeException("max number of scopes reached");
      }

      this.activeScope++;
      this.scopes[this.activeScope] = -1;
   }

   /**
    * Essa função destrói o escopo atual da tabela de símbolos.
    */
   public void destroyScope() {
      if(this.scopes[this.activeScope] >= 0) {
         this.nextAvailableAddress = this.scopes[this.activeScope];
      }

      this.activeScope--;
   }

   /**
    * Essa função retorna uma entrada da tabela de símbolo identificada por um
    * id específico.
    * @param  id Identificador da entrada no escopo atual da tabela.
    * @return    Objeto que representa a entrada em questão ou nulo caso não
    *            seja encontrado.
    */
   public SymbolTableEntry getEntry(String id) {
      int currentScope = this.activeScope;
      int entryAddress = -1;

      while(currentScope >= 0) {
         entryAddress = this.scopes[currentScope];

         while(entryAddress >= 0) {
            if(id.compareTo(this.table[entryAddress].id()) == 0) {
               return this.table[entryAddress];
            }
            else if(id.compareTo(this.table[entryAddress].id()) < 0) {
               entryAddress = this.table[entryAddress].left();
            }
            else {
               entryAddress = this.table[entryAddress].right();
            }
         }

         currentScope--;
      }

      return null;
   }

   /**
    * Essa função instala uma nova entrada na tabela de símbolos.
    * @param  id     Identificador da entrada.
    * @param  value  Atributo associado à entrada.
    */
   public void installEntry(String id, Type type) {
      int currentScopeAddress = this.scopes[this.activeScope];
      int leafAddress = -1;

      while(currentScopeAddress >= 0) {
         leafAddress = currentScopeAddress;

         if(id.compareTo(this.table[currentScopeAddress].id()) == 0) {
            throw new RuntimeException("symbol already installed in current scope");
         }
         else if(id.compareTo(this.table[currentScopeAddress].id()) < 0) {
            currentScopeAddress = this.table[currentScopeAddress].left();
         }
         else {
            currentScopeAddress = this.table[currentScopeAddress].right();
         }
      }

      if(nextAvailableAddress == SymbolTable.MAX_SIZE-1) {
         throw new RuntimeException("can't install : symbol table is full");
      }

      // Cria a nova entrada.
      SymbolTableEntry entry = new SymbolTableEntry(id, type, nextAvailableAddress);

      // Verifica se a entrada será inserida à esquerda ou à direita do
      // nó folha.
      if(this.scopes[this.activeScope] == -1) {
         this.scopes[this.activeScope] = nextAvailableAddress;
      }
      else if(id.compareTo(this.table[leafAddress].id()) < 0) {
         this.table[leafAddress].setLeft(nextAvailableAddress);
      }
      else {
         this.table[leafAddress].setRight(nextAvailableAddress);
      }

      // Instala entrada no próximo endereço disponível da tabela.
      this.table[nextAvailableAddress] = entry;
      nextAvailableAddress++;
   }

   /**
    * Essa função imprime a tabela de símbolos atual na saída padrão.
    */
   public void print() {
      System.out.println("###################################");
      System.out.println("SymbolTable : print");
      System.out.println("-----------------------------------");
      System.out.println("Active Scope = " + this.activeScope);
      System.out.println("Scopes:");
      for(int i = 0; i < this.activeScope; i++) {
         System.out.println("   scope["+i+"] = " + this.scopes[i]);
      }

      System.out.println("");
      System.out.println("|\tid\t|\tvalue\t|\ttype\t|\tleft\t|\tright\t|");
      System.out.println("|\t----\t:\t----\t:\t----\t:\t----\t:\t----\t|");

      for(int j = 0; j < this.nextAvailableAddress; j++) {
         SymbolTableEntry entry = this.table[j];
         System.out.println("|\t"+entry.id()+"\t|\t"+entry.value()+"\t|\t"+entry.type().tag().toString()+"\t|\t"+entry.left()+"\t|\t"+entry.right()+"\t|");
      }

      System.out.println("###################################End\n");
   }
}
