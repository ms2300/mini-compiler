package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class ZextInstruction extends AbstractInstruction {
   private Register reg;
   private final LLVMValue value;

   public ZextInstruction(LLVMValue value) {
      super("zext");
      this.reg = new Register("i1");
      this.value = value;
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " i32 " + value.get_name() + " to i1";
   }

   public Register getReg() { return this.reg; }
}
