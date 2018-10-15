package instructions;

public class ReturnVoidInstruction extends AbstractInstruction {
   public ReturnVoidInstruction() { super("ret"); }

   public String toString() {
      return this.getOp_code() + " void";
   }
}
