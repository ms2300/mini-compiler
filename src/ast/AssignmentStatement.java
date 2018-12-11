package ast;

import cfg.BasicBlock;
import cfg.Label;
import instructions.StoreInstruction;
import llvm.LLVMNull;
import llvm.LLVMValue;
import llvm.Register;

import java.util.List;
import java.util.Map;

public class AssignmentStatement extends AbstractStatement {
   private final Lvalue target;
   private final Expression source;
   private Map<String, TypeScope> local_map;

   public AssignmentStatement(int lineNum, Lvalue target, Expression source) {
      super(lineNum);
      this.target = target;
      this.source = source;
   }

   public boolean static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      Type s = source.static_type_check(local_map);
      if (!target.static_type_check(local_map).getClass().equals(s.getClass())) {
         if (s instanceof VoidType) {
            return false;
         }
         Program.error("Invalid assignment line : " + this.getLineNum());
      }
      this.local_map = local_map;
      return false;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      LLVMValue right = source.get_llvm(cur);
      String left = target.ref_llvm(cur);
      if (target instanceof LvalueId && !((LvalueId) target).is_global() && Label.isSSA()) {
         LvalueId t = (LvalueId) target;
         cur.write_value(t.getId(), right);
      } else {
         String type = target.getReg_type();
         StoreInstruction st = new StoreInstruction(type, right, left);
         if (target instanceof LvalueDot) {
            ((LvalueDot) target).getReg().add_use(st);
         }
         cur.add_instruction(st);
      }
      return cur;
   }
}
