package instructions;

public class MiscInstruction extends AbstractInstruction {
   private final String result;
   private final String ty;
   private final String value;
   private final String ty2;

   public MiscInstruction(String result, String op, String ty, String value, String ty2) {
      super(op);
      this.result = result;
      this.ty = ty;
      this.value = value;
      this.ty2 = ty2;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + " " + ty + " " + value + " to " + ty2;
   }
}
