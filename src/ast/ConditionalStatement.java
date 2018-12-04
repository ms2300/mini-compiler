package ast;

import cfg.BasicBlock;
import cfg.Label;
import instructions.BranchConditional;
import instructions.BranchInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.List;
import java.util.Map;

public class ConditionalStatement extends AbstractStatement {
   private final Expression guard;
   private final Statement thenBlock;
   private final Statement elseBlock;

   public ConditionalStatement(int lineNum, Expression guard, Statement thenBlock, Statement elseBlock) {
      super(lineNum);
      this.guard = guard;
      this.thenBlock = thenBlock;
      this.elseBlock = elseBlock;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (guard.static_type_check(local_map) instanceof BoolType) {
         thenBlock.static_type_check(ret_type, local_map);
         elseBlock.static_type_check(ret_type, local_map);
         return new VoidType();
      }
      Program.error("Invalid conditional line : " + this.getLineNum());
      return null;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      int flag = 0;
      BasicBlock then_block = new BasicBlock(Label.nextBlockLabel());
      BasicBlock else_block = new BasicBlock(Label.nextBlockLabel());
      BasicBlock then_flow = thenBlock.make_cfg(then_block, end, ret_val, blocks);
      BasicBlock else_flow = elseBlock.make_cfg(else_block, end, ret_val, blocks);
      if (!blocks.contains(then_block)) { blocks.add(then_block); }
      if (!blocks.contains(else_block)) { blocks.add(else_block); }
      LLVMValue gx = guard.get_llvm(cur);
      BranchConditional br_c = new BranchConditional(gx, then_block.getLabel(), else_block.getLabel());
      cur.add_instruction(br_c);
      BasicBlock join = new BasicBlock(Label.nextBlockLabel());
      BranchInstruction br_a = new BranchInstruction(join.getLabel());
      if (!(then_flow.getDesc().size() > 0)) {
         flag++;
         then_flow.add_instruction(br_a);
         join.add_pred(then_flow);
         then_flow.add_desc(join);
      }
      if (!(else_flow.getDesc().size() > 0)) {
         flag++;
         else_flow.add_instruction(br_a);
         join.add_pred(else_flow);
         else_flow.add_desc(join);
      }
      then_block.add_pred(cur);
      else_block.add_pred(cur);
      cur.add_desc(then_block);
      cur.add_desc(else_block);
      then_block.seal_block();
      then_flow.seal_block();
      else_block.seal_block();
      else_flow.seal_block();
      join.seal_block();
      if (flag != 0) {
         blocks.add(join);
         return join;
      }
      return cur;
   }
}
