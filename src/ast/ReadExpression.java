package ast;

import cfg.BasicBlock;
import instructions.ReadInstruction;
import llvm.LLVMValue;

import java.util.Map;

public class ReadExpression extends AbstractExpression {
   public ReadExpression(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new IntType(); }

   public LLVMValue get_llvm(BasicBlock cur) {
      ReadInstruction r = new ReadInstruction();
      cur.add_instruction(r);
      return r.getReg();
   }
}
