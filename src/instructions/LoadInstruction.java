package instructions;

public class LoadInstruction extends AbstractInstruction {
   private final String result;
   private final String ty;
   private final String pointer;

   public LoadInstruction(String result, String ty, String pointer) {
      super("load");
      this.result = result;
      this.ty = ty;
      this.pointer = pointer;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + " " + ty + "* " + pointer;
   }
}
