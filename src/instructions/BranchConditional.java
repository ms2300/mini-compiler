package instructions;

public class BranchConditional extends AbstractInstruction {
   private final String cond;
   private final String iftrue;
   private final String iffalse;

   public BranchConditional(String cond, String iftrue, String iffalse) {
      super("br");
      this.cond = cond;
      this.iftrue = iftrue;
      this.iffalse = iffalse;
   }

   public String toString() {
      return this.getOp_code() + " i1 " + cond + ", label " + iftrue + ", label " + iffalse;
   }
}
