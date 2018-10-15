package instructions;

public class CmpInstruction extends AbstractInstruction {
   private final String result;
   private final String cond;
   private final String ty;
   private final String op1;
   private final String op2;

   public CmpInstruction(String result, String cond, String ty, String op1, String op2) {
      super("icmp");
      this.result = result;
      this.cond = cond;
      this.ty = ty;
      this.op1 = op1;
      this.op2 = op2;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + " " + cond + " " + ty + " " + op1 + ", " + op2;
   }
}
