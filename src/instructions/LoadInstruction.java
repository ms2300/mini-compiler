package instructions;

import llvm.Register;

import java.util.Optional;

public class LoadInstruction extends AbstractInstruction {
   private final Register reg;
   private final String ty;
   private final String label;

   public LoadInstruction(String label, String ty) {
      super("load");
      this.ty = ty;
      this.reg = new Register(this.ty, Optional.empty());
      this.label = label;
      reg.set_def(this);
   }

   public String toString() { return reg.get_name() + " = " + this.getOp_code() + " " + this.ty + "* " + label; }
   public Register getReg() { return this.reg; }
}
