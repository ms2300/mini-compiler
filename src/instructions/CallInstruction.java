package instructions;

import llvm.Register;

public class CallInstruction extends AbstractInstruction {
   private final Register reg;
   private final String ret_ty;
   private final String func_name;
   private final String param_string;

   public CallInstruction(String ret_ty, String func_name, String param_string) {
      super("call");
      this.reg = new Register(ret_ty);
      this.ret_ty = ret_ty;
      this.func_name = func_name;
      this.param_string = param_string;
   }

   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + this.ret_ty + " " + this.func_name + this.param_string;
   }
   public Register getReg() { return this.reg; }
}
