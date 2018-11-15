package ast;

import cfg.BasicBlock;
import llvm.LLVMValue;

import java.util.Map;

public interface Expression {
   Type static_type_check(Map<String, TypeScope> local_map);
   LLVMValue get_llvm(BasicBlock cur);
}
