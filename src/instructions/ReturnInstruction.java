package instructions;

import llvm.Register;

public class ReturnInstruction extends AbstractInstruction {
   private final String ty;
   private final String value;

   public ReturnInstruction(String ty, String value) {
      super("ret");
      this.ty = ty;
      this.value = value;
   }

   public String toString() {
      return this.getOp_code() + " " + this.ty + " " + this.value;
   }
   public Register getReg() { return null; }
}
