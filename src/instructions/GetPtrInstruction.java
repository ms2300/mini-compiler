package instructions;

public class GetPtrInstruction extends AbstractInstruction {
   private final String result;
   private final String ty;
   private final String ptrval;
   private final String index;

   public GetPtrInstruction(String result, String ty, String ptrval, String index) {
      super("getelementptr");
      this.result = result;
      this.ty = ty;
      this.ptrval = ptrval;
      this.index = index;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + ty + "* " + ptrval + ", i1 0, i32 " + index;
   }
}
