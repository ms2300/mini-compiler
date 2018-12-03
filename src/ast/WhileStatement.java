package ast;

import cfg.BasicBlock;
import cfg.Label;
import instructions.BranchConditional;
import instructions.BranchInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.List;
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

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      BasicBlock body_block = new BasicBlock(Label.nextBlockLabel());
      BasicBlock body_flow = body.make_cfg(body_block, end, ret_val, blocks);
      if (!body_block.equals(body_flow)) {
         blocks.add(body_block);
      } else {
         blocks.add(body_flow);
      }
      BasicBlock join = new BasicBlock(Label.nextBlockLabel());
      LLVMValue gx = guard.get_llvm(cur);
      BranchConditional br_c = new BranchConditional(gx, body_flow.getLabel(), join.getLabel());
      cur.add_instruction(br_c);
      cur.add_desc(body_block);
      body_block.add_pred(cur);
      cur.add_desc(join);
      join.add_pred(cur);
      if (!(body_flow.getDesc().size() > 0)) {
         body_block.add_pred(body_flow);
         body_flow.add_desc(body_block);
         join.add_pred(body_flow);
         body_flow.add_desc(join);
         body_block.seal_block();
         body_flow.seal_block();
         LLVMValue gx2 = guard.get_llvm(body_flow);
         BranchConditional br_c2 = new BranchConditional(gx2, body_block.getLabel(), join.getLabel());
         body_flow.add_instruction(br_c2);
      }
      body_block.seal_block();
      body_flow.seal_block();
      join.seal_block();
      blocks.add(join);
      return join;
   }
}
