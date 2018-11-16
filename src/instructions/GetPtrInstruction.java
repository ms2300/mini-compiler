package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class GetPtrInstruction extends AbstractInstruction {
   private final Register reg;
   private final LLVMValue value;
   private final String ty;
   private final String index;

   public GetPtrInstruction(String ty, LLVMValue value, String index) {
      super("getelementptr");
      this.reg = new Register("i32");
      this.ty = ty;
      this.value = value;
      this.index = index;
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + ty + " " + value.get_name() + ", i1 0, i32 " + index;
   }
   public Register getReg() { return this.reg; }
}
