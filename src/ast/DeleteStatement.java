package ast;

import cfg.BasicBlock;
import instructions.BitcastInstruction;
import instructions.CallInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.List;
import java.util.Map;

public class DeleteStatement extends AbstractStatement {
   private final Expression expression;

   public DeleteStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public boolean static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      Type del = expression.static_type_check(local_map);
      if (!(del instanceof StructType)) {
         Program.error("Invalid delete statement line : " + this.getLineNum());
      }
      return false;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      LLVMValue r = expression.get_llvm(cur);
      BitcastInstruction b = new BitcastInstruction(r, r.get_type(), "i8*");
      CallInstruction c = new CallInstruction("void", "@free", "(i8* " + b.getReg().get_name() + ")");
      cur.add_instruction(b);
      cur.add_instruction(c);
      return cur;
   }
}
