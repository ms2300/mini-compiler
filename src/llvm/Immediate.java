package llvm;

import ast.Program;
import instructions.Instruction;

import java.util.List;

public class Immediate implements LLVMValue {
   private final String imm_value;
   private final String ty;

   public Immediate(String imm_value, String ty) {
      this.imm_value = imm_value;
      this.ty = ty;
   }

   public String get_type() {
      return this.ty;
   }
   public void add_use(Instruction x) {}
   public void set_def(Instruction x) {}
   public String get_name() { return imm_value; }
   public List<Instruction> get_uses() {
      Program.error("SSA trying to get uses from immediate value");
      return null;
   }
   public Instruction get_def() {
      Program.error("SSA trying to get def from immediate value");
      return null;
   }
}
