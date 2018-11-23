package ast;

import cfg.BasicBlock;
import cfg.Label;
import llvm.Register;

import java.util.Map;

public class WhileStatement extends AbstractStatement {
   private final Expression guard;
   private final Statement body;

   public WhileStatement(int lineNum, Expression guard, Statement body) {
      super(lineNum);
      this.guard = guard;
      this.body = body;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (!(guard.static_type_check(local_map) instanceof BoolType)) {
         Program.error("Invalid guard to loop line : " + this.getLineNum());
      }
      return body.static_type_check(ret_type, local_map);
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val) {
      /* Add guard to cur */
      BasicBlock body_block = body.make_cfg(new BasicBlock(Label.nextBlockLabel()), end, ret_val);
      BasicBlock join = new BasicBlock(Label.nextBlockLabel());
      /* Add guard to guard block */
      cur.add_desc(body_block);
      body_block.add_pred(cur);
      cur.add_desc(join);
      join.add_pred(cur);
      if (!(body_block.getDesc().size() > 0)) {
         BasicBlock guard_block = new BasicBlock(Label.nextBlockLabel());
         body_block.add_desc(guard_block);
         body_block.add_pred(guard_block);
         guard_block.add_desc(body_block);
         guard_block.add_desc(join);
         join.add_pred(guard_block);
      }
      return join;
   }
}
