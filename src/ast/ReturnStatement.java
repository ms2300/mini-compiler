package ast;

import cfg.BasicBlock;
import cfg.Label;
import instructions.*;
import llvm.LLVMValue;
import llvm.Register;

import java.util.List;
import java.util.Map;

public class ReturnStatement extends AbstractStatement {
   private final Expression expression;

   public ReturnStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public boolean static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      Type ret_val = expression.static_type_check(local_map);
      if (!ret_val.getClass().equals(ret_type.getClass())) {
         if (ret_val instanceof VoidType) {
            return true;
         }
         Program.error("Invalid return statement line : " + this.getLineNum());
      }
      return true;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks) {
      LLVMValue reg = expression.get_llvm(cur);
      BranchInstruction b = new BranchInstruction(end.getLabel());
      if (Label.isSSA()) {
         cur.add_instruction(b);
         if (end.getInstructions().size() == 0) {
            PhiInstruction phi = new PhiInstruction(ret_val.get_type(), end, "_ret_val_");
            ReturnInstruction r = new ReturnInstruction(phi.getReg().get_type(), phi.getReg().get_name());
            phi.add_label(cur.getLabel());
            phi.add_operand(reg);
            end.add_instruction(phi);
            end.add_instruction(r);
         } else {
            Instruction inst = end.getInstructions().get(0);
            if (inst instanceof PhiInstruction) {
               PhiInstruction phi = (PhiInstruction) inst;
               phi.add_operand(reg);
               phi.add_label(cur.getLabel());
            } else {
               Program.error("Error with ssa and return statements : " + this.getLineNum());
               return null;
            }
         }
      } else {
         StoreInstruction s = new StoreInstruction(ret_val.get_type(), reg, ret_val.get_name());
         cur.add_instruction(s);
         cur.add_instruction(b);
         if (end.getInstructions().size() == 0) {
            LoadInstruction l = new LoadInstruction(ret_val.get_name(), ret_val.get_type());
            ReturnInstruction r = new ReturnInstruction(l.getReg().get_type(), l.getReg().get_name());
            end.add_instruction(l);
            end.add_instruction(r);
         }
      }
      end.add_pred(cur);
      cur.add_desc(end);
      return cur;
   }
}
