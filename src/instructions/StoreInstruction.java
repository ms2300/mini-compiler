package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class StoreInstruction extends AbstractInstruction {
   private final String ty;
   private final LLVMValue val_a;
   private final Register val_b;

   public StoreInstruction(String ty, LLVMValue val_a, Register val_b) {
      super("store");
      this.ty = ty;
      this.val_a = val_a;
      this.val_b = val_b;
   }

   public String toString() {
      return this.getOp_code() + " " + ty + " " + val_a.get_name() + ", " + ty + "* " + val_b.get_name();
   }
   public Register getReg() { return null; }
}
