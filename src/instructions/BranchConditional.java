package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class BranchConditional extends AbstractInstruction {
   private final LLVMValue cond;
   private final String iftrue;
   private final String iffalse;

   public BranchConditional(LLVMValue cond, String iftrue, String iffalse) {
      super("br");
      this.cond = cond;
      this.iftrue = iftrue;
      this.iffalse = iffalse;
   }

   public String toString() {
      return this.getOp_code() + " i1 " + cond.get_name() + ", label " + iftrue + ", label " + iffalse;
   }
   public Register getReg() { return null; }
}
