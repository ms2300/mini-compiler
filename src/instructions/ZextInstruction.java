package instructions;

import llvm.LLVMValue;
import llvm.Register;

import java.util.Optional;

public class ZextInstruction extends AbstractInstruction {
   private final Register reg;
   private final LLVMValue value;

   public ZextInstruction(LLVMValue value) {
      super("zext");
      this.reg = new Register("i1", Optional.empty());
      this.value = value;
      reg.set_def(this);
      value.add_use(this);
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " i32 " + value.get_name() + " to i1";
   }

   public Register getReg() { return this.reg; }
}
