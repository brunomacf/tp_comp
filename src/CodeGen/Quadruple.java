package MASB.CodeGen;

public class Quadruple {
   public enum Tag {
      CONST("const"),
      LITERAL("literal"),
      VARIABLE("variable"),
      COPY("copy"),
      CAST("cast"),
      ADD("+"),
      SUB("-"),
      MUL("*"),
      DIV("/"),
      GT(">"),
      GTE(">="),
      LT("<"),
      LTE("<="),
      EQ("=="),
      NEQ("!="),
      MINUS("-"),
      NOT("!"),
      AND("&&"),
      OR("||"),
      IF("if"),
      IFFALSE("iffalse");

      private String tagStr;

      Tag(String tagStr) {
         this.tagStr = tagStr;
      }

      public String toString() { return this.tagStr; }
   }

   /* Os quatro constiuintes de uma quádruplas necessários para criar o código
   de três endereços. */
   private Tag tag;
   private String label;
   private String result;
   private String arg1;
   private String arg2;

   /**
    * Construtor padrão da quadrupla recebendo todos os quatro constituintes.
    */
   public Quadruple(Tag tag, String result, String arg1, String arg2) {
      this.tag = tag;
      this.result = result;
      this.arg1 = arg1;
      this.arg2 = arg2;
      this.label = null;
   }

   /**
    * Construtor de quadrupla recebendo três constituintes.
    */
   public Quadruple(Tag tag, String result, String arg1) {
      this.tag = tag;
      this.result = result;
      this.arg1 = arg1;
      this.arg2 = null;
      this.label = null;
   }

   /**
    * Construtor de quadrupla recebendo apenas dois constituintes.
    */
    public Quadruple(Tag tag, String result) {
      this.tag = tag;
      this.result = result;
      this.arg1 = null;
      this.arg2 = null;
      this.label = null;
   }

   // Getters
   public Tag op() { return this.tag; }
   public String result() { return this.result; }
   public String arg1() { return this.arg1; }
   public String arg2() { return this.arg2; }
   public String label() { return this.label; }

   // Setters
   public void setLabel(String label) { this.label = label; }

   /**
    * Essa função imprime esta quádrupla.
    * @return Texto que representa a quádrupla.
    */
   public String toString() {
      switch(this.tag) {
         case IF : {
            return String.format("if %s goto %s", this.arg1, this.arg2);
         }
         case IFFALSE : {
            return String.format("iffalse %s goto %s", this.arg1, this.arg2);
         }
         case CAST : {
            return String.format("%s = (%s) %s", this.result, this.arg2, this.arg1);
         }
         case COPY : {
            return String.format("%s = %s", this.result, this.arg1);
         }
         default : {
            if (this.arg2 != null) {
               return String.format("%s = %s %s %s", this.result, this.arg1, this.tag.toString(), this.arg2);
            }
            else if(this.arg1 != null) {
               return String.format("%s = %s%s", this.result, this.tag.toString(), this.arg1);
            }
         }
      }

      return null;
   }
}
