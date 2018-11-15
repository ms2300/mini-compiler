package ast;

import cfg.BasicBlock;
import llvm.Immediate;
import llvm.LLVMValue;

import java.util.Map;

public class IntegerExpression extends AbstractExpression {
   private final String value;

   public IntegerExpression(int lineNum, String value) {
      super(lineNum);
      this.value = value;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new IntType(); }
   public LLVMValue get_llvm(BasicBlock cur) { return new Immediate(value, "i32"); }
}
