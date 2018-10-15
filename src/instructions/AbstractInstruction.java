package instructions;


public abstract class AbstractInstruction implements Instruction {
   private final String op_code;

   public AbstractInstruction(String op_code) {
      this.op_code = op_code;
   }

   public String getOp_code() { return this.op_code; }
}
