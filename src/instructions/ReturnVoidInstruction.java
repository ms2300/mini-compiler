package instructions;

import llvm.Register;

public class ReturnVoidInstruction extends AbstractInstruction {
   public ReturnVoidInstruction() { super("ret"); }

   public String toString() {
      return this.getOp_code() + " void";
   }
   public Register getReg() { return null; }
}
