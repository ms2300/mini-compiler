package ast;

import cfg.BasicBlock;
import cfg.Label;
import instructions.BranchConditional;
import instructions.BranchInstruction;
import llvm.LLVMValue;
import llvm.Register;

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

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val) {
      /*
         There might be problems with returns from conditions here
       */
      BasicBlock then_flow = thenBlock.make_cfg(new BasicBlock(Label.nextBlockLabel()), end, ret_val);
      BasicBlock else_flow = elseBlock.make_cfg(new BasicBlock(Label.nextBlockLabel()), end, ret_val);
      LLVMValue gx = guard.get_llvm(cur);
      BranchConditional br_c = new BranchConditional(gx, then_flow.getLabel(), else_flow.getLabel());
      cur.add_instruction(br_c);
      BasicBlock join = new BasicBlock(Label.nextBlockLabel());
      BranchInstruction br_a = new BranchInstruction(join.getLabel());
      then_flow.add_instruction(br_a);
      else_flow.add_instruction(br_a);
      if (!(then_flow.getDesc().size() > 0)) {
         join.add_pred(then_flow);
         then_flow.add_desc(join);
      }
      if (!(else_flow.getDesc().size() > 0)) {
         join.add_pred(else_flow);
         else_flow.add_desc(join);
      }
      then_flow.add_pred(cur);
      else_flow.add_pred(cur);
      cur.add_desc(then_flow);
      cur.add_desc(else_flow);
      return join;
   }
}
