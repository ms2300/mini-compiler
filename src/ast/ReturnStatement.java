package ast;

import cfg.BasicBlock;
import instructions.BranchInstruction;
import instructions.LoadInstruction;
import instructions.ReturnInstruction;
import instructions.StoreInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.Map;

public class ReturnStatement extends AbstractStatement {
   private final Expression expression;

   public ReturnStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (!expression.static_type_check(local_map).getClass().equals(ret_type.getClass())) {
         Program.error("Invalid return statement line : " + this.getLineNum());
      }
      return ret_type;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val) {
      LLVMValue reg = expression.get_llvm(cur);
      StoreInstruction s = new StoreInstruction(reg.get_type(), reg, ret_val);
      BranchInstruction b = new BranchInstruction(end.getLabel());
      cur.add_instruction(s);
      cur.add_instruction(b);
      LoadInstruction l = new LoadInstruction(ret_val.get_name(), ret_val.get_type());
      ReturnInstruction r = new ReturnInstruction(l.getReg().get_type(), l.getReg().get_name());
      end.add_instruction(l);
      end.add_instruction(r);
      end.add_pred(cur);
      cur.add_desc(end);
      return end;
   }
}
