package ast;

public class BoolType implements Type {
   public String to_llvm() { return "bool"; }
}
