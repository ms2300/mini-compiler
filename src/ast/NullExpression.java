package ast;

import cfg.BasicBlock;
import llvm.LLVMNull;
import llvm.LLVMValue;

import java.util.Map;

public class NullExpression extends AbstractExpression {
   public NullExpression(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new VoidType(); }
   public LLVMValue get_llvm(BasicBlock cur) { return new LLVMNull(); }
}
