package ast;

import cfg.BasicBlock;
import instructions.BranchInstruction;
import instructions.ReturnVoidInstruction;
import llvm.Register;

import java.util.Map;

public class ReturnEmptyStatement extends AbstractStatement {
   public ReturnEmptyStatement(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (!(ret_type instanceof VoidType)) {
         Program.error("Invalid return statement line : " + this.getLineNum());
      }
      return ret_type;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val) {
      BranchInstruction b = new BranchInstruction(end.getLabel());
      cur.add_instruction(b);
      ReturnVoidInstruction r = new ReturnVoidInstruction();
      end.add_instruction(r);
      end.add_pred(cur);
      cur.add_desc(end);
      return end;
   }
}
