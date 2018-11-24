package instructions;

import llvm.LLVMValue;
import llvm.Register;

import java.util.Optional;

public class MathematicalInstruction extends AbstractInstruction {
   private final Register reg;
   private final String ty;
   private final LLVMValue op1;
   private final LLVMValue op2;

   public MathematicalInstruction(String opc, String ty, LLVMValue op1, LLVMValue op2) {
      super(opc);
      this.ty = ty;
      this.reg = new Register(this.ty, Optional.empty());
      this.op1 = op1;
      this.op2 = op2;
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + this.ty + " " + op1.get_name() + ", " + op2.get_name();
   }

   public Register getReg() { return this.reg; }
}
