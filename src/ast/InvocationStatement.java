package ast;

import cfg.BasicBlock;
import llvm.Register;

import java.util.List;
import java.util.Map;

public class InvocationStatement extends AbstractStatement {
   private final Expression expression;
   public InvocationStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      return expression.static_type_check(local_map);
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      expression.get_llvm(cur);
      return cur;
   }
}
