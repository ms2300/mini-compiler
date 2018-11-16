package ast;

public class BoolType implements Type {
   public String to_llvm() { return "i1"; }
}
