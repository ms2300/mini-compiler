package instructions;

import llvm.LLVMValue;
import llvm.Register;

import java.util.Optional;

public class CmpInstruction extends AbstractInstruction {
   private final Register reg;
   private final String ty;
   private final String cond;
   private final LLVMValue op1;
   private final LLVMValue op2;

   public CmpInstruction(String cond, String ty, LLVMValue op1, LLVMValue op2) {
      super("icmp");
      this.ty = ty;
      this.reg = new Register("i1", Optional.empty());
      this.cond = cond;
      this.op1 = op1;
      this.op2 = op2;
      op1.add_use(this);
      op2.add_use(this);
      reg.set_def(this);
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + cond + " " + this.ty + " " + op1.get_name() + ", " + op2.get_name();
   }

   public Register getReg() { return this.reg; }
}
