package instructions;

public class CallInstruction extends AbstractInstruction {
   private final String result;
   private final String ty;
   private final String fnptrval;

   public CallInstruction(String result, String ty, String fnptrval) {
      super("call");
      this.result = result;
      this.ty = ty;
      this.fnptrval = fnptrval;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + " " + ty + " " + fnptrval;
   }
}
