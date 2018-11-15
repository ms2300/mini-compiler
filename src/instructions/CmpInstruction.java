package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class CmpInstruction extends AbstractInstruction {
   private Register reg;
   private final String ty;
   private final String cond;
   private final LLVMValue op1;
   private final LLVMValue op2;

   public CmpInstruction(String cond, String ty, LLVMValue op1, LLVMValue op2) {
      super("icmp");
      this.ty = ty;
      this.reg = new Register("i32");
      this.cond = cond;
      this.op1 = op1;
      this.op2 = op2;
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + cond + " " + this.ty + " " + op1.get_name() + ", " + op2.get_name();
   }

   public Register getReg() { return this.reg; }
}
