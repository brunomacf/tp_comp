/**
 * Esse arquivo implementa uma entrada da tabela de símbolos.
 */

package MASB.SymbolTable;

public class SymbolTableEntry {

   private String id;
   private String value;
   private Type type;
   private int left;
   private int right;
   private int tableIdx;

   public SymbolTableEntry(String id, Type type, int tableIdx) {
      this.id = id;
      this.value = null;
      this.type = type;
      this.left = -1;
      this.right = -1;
      this.tableIdx = tableIdx;
   }

   // Getters
   public String id() { return this.id; }
   public String value() { return this.value; }
   public Type type() { return this.type; }
   public int left() { return this.left; }
   public int right() { return this.right; }
   public int tableIdx() { return this.tableIdx; }

   // Setters
   public void setValue(String value) { this.value = value; }
   public void setLeft(int left) { this.left = left; }
   public void setRight(int right) { this.right = right; }
}
