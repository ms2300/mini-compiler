package llvm;

import ast.Program;
import instructions.Instruction;

import java.util.List;

public class LLVMNull implements LLVMValue {
   public LLVMNull() {}
   public String get_name() { return "null"; }
   public String get_type() { return "i32*"; }
   public void add_use(Instruction x) {}
   public void set_def(Instruction x) {}
   public List<Instruction> get_uses() {
      Program.error("SSA trying to get uses from null value");
      return null;
   }
   public Instruction get_def() {
      Program.error("SSA trying to get def from null value");
      return null;
   }
}
