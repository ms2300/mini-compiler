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
      BasicBlock body_block = body.make_cfg(new BasicBlock(Label.nextBlockLabel()), end, ret_val, blocks);
      BasicBlock join = new BasicBlock(Label.nextBlockLabel());
      LLVMValue gx = guard.get_llvm(cur);
      BranchConditional br_c = new BranchConditional(gx, body_block.getLabel(), join.getLabel());
      cur.add_instruction(br_c);
      cur.add_desc(body_block);
      body_block.add_pred(cur);
      cur.add_desc(join);
      join.add_pred(cur);
      if (!(body_block.getDesc().size() > 0)) {
         BasicBlock guard_block = new BasicBlock(Label.nextBlockLabel());
         LLVMValue gx_b = guard.get_llvm(guard_block);
         BranchConditional br_g = new BranchConditional(gx_b, body_block.getLabel(), join.getLabel());
         guard_block.add_instruction(br_g);
         BranchInstruction bb_g = new BranchInstruction(guard_block.getLabel());
         body_block.add_instruction(bb_g);
         body_block.add_desc(guard_block);
         body_block.add_pred(guard_block);
         guard_block.add_desc(body_block);
         guard_block.add_desc(join);
         join.add_pred(guard_block);
         blocks.add(guard_block);
      }
      blocks.add(body_block);
      blocks.add(join);
      return join;
   }
}
