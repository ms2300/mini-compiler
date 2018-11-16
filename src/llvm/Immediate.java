package llvm;

public class Immediate implements LLVMValue {
   private final String imm_value;
   private final String ty;

   public Immediate(String imm_value, String ty) {
      this.imm_value = imm_value;
      this.ty = ty;
   }

   public String get_type() {
      return this.ty;
   }

   public String get_name() { return imm_value; }
}
