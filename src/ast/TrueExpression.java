package ast;

import cfg.BasicBlock;
import llvm.Immediate;
import llvm.LLVMValue;

import java.util.Map;

public class TrueExpression extends AbstractExpression {
   public TrueExpression(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new BoolType(); }
   public LLVMValue get_llvm(BasicBlock cur) { return new Immediate("1", "i1"); }
}
