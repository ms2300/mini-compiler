package ast;

public class VoidType implements Type {
   public String to_llvm() { return "void"; }
}
