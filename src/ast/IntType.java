package ast;

public class IntType implements Type {
   public String to_llvm() { return "i32"; }
}
