package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class StoreInstruction extends AbstractInstruction {
   private final String ty;
   private final LLVMValue val_a;
   private final String val_b;

   public StoreInstruction(String ty, LLVMValue val_a, String val_b) {
      super("store");
      this.ty = ty;
      this.val_a = val_a;
      this.val_b = val_b;
      val_a.add_use(this);
   }

   public String toString() {
      return this.getOp_code() + " " + ty + " " + val_a.get_name() + ", " + ty + "* " + val_b;
   }
   public Register getReg() { return null; }
}
