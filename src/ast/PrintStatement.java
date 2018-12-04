package ast;

import cfg.BasicBlock;
import instructions.PrintInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.List;
import java.util.Map;

public class PrintStatement extends AbstractStatement {
   private final Expression expression;

   public PrintStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public boolean static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      expression.static_type_check(local_map);
      return false;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      LLVMValue pr = expression.get_llvm(cur);
      PrintInstruction p = new PrintInstruction(pr, false);
      cur.add_instruction(p);
      return cur;
   }
}
