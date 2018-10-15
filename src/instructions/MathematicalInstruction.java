package instructions;

public class MathematicalInstruction extends AbstractInstruction {
   private final String result;
   private final String ty;
   private final String op1;
   private final String op2;

   public MathematicalInstruction(String result, String opc, String ty, String op1, String op2) {
      super(opc);
      this.result = result;
      this.ty = ty;
      this.op1 = op1;
      this.op2 = op2;
   }

   public String toString() {
      return result + " = " + this.getOp_code() + " " + ty + " " + op1 + ", " + op2;
   }
}
