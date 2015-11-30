package MASB.SymbolTable;

import java.util.ArrayList;

public class Type {
   public enum Tag {
      NAT(0),
      BOOL(1),
      CHAR(4),
      INT(4),
      FLOAT(8);

      public final int size;

      Tag(int size) {
         this.size = size;
      }

      public static Tag fromString(String str) {
         switch(str) {
            case "int": {return INT;}
            case "float": {return FLOAT;}
            case "bool": {return BOOL;}
            case "char": {return CHAR;}
            default: {return null;}
         }
      }

      public int size() { return this.size(); }

      public String toString() {
         return super.toString().toLowerCase();
      }
   }

   private Tag tag;
   private ArrayList<Integer> dims;      // Tamanho de cada dimensão do tipo (caso array).

   // Getters
   public Tag tag() { return this.tag; }
   public ArrayList<Integer> dims() { return this.dims; }

   /**
    * Construtor padrão.
    * @param  tag Tag que define este tipo.
    * @return     Novo tipo construído.
    */
   public Type(Tag tag) {
      this.tag = tag;
      this.dims = new ArrayList<Integer>();
   }

   /**
    * Essa função adiciona uma nova dimensão a este tipo num tamanho específico.
    * @param dimSize Tamanho da nova dimensão que será adicionada ao tipo.
    */
   public void addDim(String dimSize) {
      this.dims.add(Integer.valueOf(dimSize));
   }
}
