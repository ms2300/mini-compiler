package instructions;

import llvm.LLVMValue;
import llvm.Register;

import java.util.Optional;

public class BitcastInstruction extends AbstractInstruction {
   private final Register reg;
   private final LLVMValue value;
   private final String ty1;
   private final String ty2;

   public BitcastInstruction(LLVMValue value, String ty1, String ty2) {
      super("bitcast");
      this.reg = new Register("i1", Optional.empty());
      this.value = value;
      this.ty1 = ty1;
      this.ty2 = ty2;
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + this.ty1 + " " + value.get_name() + " to " + this.ty2;
   }

   public Register getReg() { return this.reg; }
}
