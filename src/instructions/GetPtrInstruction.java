package instructions;

import llvm.LLVMValue;
import llvm.Register;

import java.util.Optional;

public class GetPtrInstruction extends AbstractInstruction {
   private final Register reg;
   private final LLVMValue value;
   private final String ty;
   private final String result_ty;
   private final String index;

   public GetPtrInstruction(String ty, LLVMValue value, String index, String result_ty) {
      super("getelementptr");
      this.ty = ty;
      this.result_ty = result_ty;
      this.reg = new Register(this.result_ty, Optional.empty());
      this.value = value;
      this.index = index;
      reg.set_def(this);
      value.add_use(this);
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + ty + " " + value.get_name() + ", i1 0, i32 " + index;
   }
   public Register getReg() { return this.reg; }
}
