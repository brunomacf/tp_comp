package MASB.ASTree;

import MASB.CodeGen.*;
import MASB.SymbolTable.*;

public abstract class ASTreeNode {
   private Type.Tag typeTag;

   /**
    * Essa função gera a quádrupla que representa este nó utilizando o contexto
    * de geração fornecido.
    * @param  ctxt Contexto de geração utilizado para gerar a quádrupla.
    * @return      Quádrupla gerada (apenas no caso de expressões).
    */
   public abstract Quadruple codeGen(CodeGenContext ctxt);

   // Getters
   public Type.Tag typeTag() { return this.typeTag; }

   // Setters
   protected void setTypeTag(Type.Tag tag) { this.typeTag = tag; }
}
