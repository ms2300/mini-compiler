package instructions;

import llvm.Register;

public class BranchInstruction extends AbstractInstruction {
   private final String dest;

   public BranchInstruction(String dest) {
      super("br");
      this.dest = dest;
   }

   public String toString() {
      return this.getOp_code() + " label " + dest;
   }
   public Register getReg() { return null; }
}
