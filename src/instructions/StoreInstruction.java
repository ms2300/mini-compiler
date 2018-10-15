package instructions;

public class StoreInstruction extends AbstractInstruction {
   private final String ty;
   private final String value;
   private final String pointer;

   public StoreInstruction(String ty, String value, String pointer) {
      super("store");
      this.ty = ty;
      this.value = value;
      this.pointer = pointer;
   }

   public String toString() {
      return this.getOp_code() + " " + ty + " " + value + ", " + ty + "* " + pointer;
   }
}
