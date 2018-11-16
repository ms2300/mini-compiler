package ast;

import cfg.BasicBlock;
import instructions.BitcastInstruction;
import instructions.CallInstruction;
import llvm.LLVMValue;

import java.util.Map;

public class NewExpression extends AbstractExpression {
   private final String id;

   public NewExpression(int lineNum, String id) {
      super(lineNum);
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      if (Program.struct_map.containsKey(id)) {
         return Program.var_map.get(id).getTy();
      }
      Program.error("Invalid new expression line : " + this.getLineNum());
      return null;
   }

   public LLVMValue get_llvm(BasicBlock cur) {
      /*
         NEED TO GET SIZE >>> ASK KEEN
       */
      CallInstruction c = new CallInstruction("i8*", "@malloc", "(i32 24)");
      BitcastInstruction b = new BitcastInstruction(c.getReg(), "i8*", Program.var_map.get(id).getTy().to_llvm());
      cur.add_instruction(c);
      cur.add_instruction(b);
      return b.getReg();
   }
}
