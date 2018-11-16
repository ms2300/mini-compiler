package instructions;

import llvm.Register;

public class AllocInstruction extends AbstractInstruction {
   private final String result;
   private final String ty;

   public AllocInstruction(String result, String ty) {
      super("alloca");
      this.result = result;
      this.ty = ty;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + " " + ty;
   }
   public Register getReg() { return null; }
}
