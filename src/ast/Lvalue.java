package ast;

import cfg.BasicBlock;
import llvm.Register;

import java.util.Map;

public interface Lvalue {
   Type static_type_check(Map<String, TypeScope> local_map);
   String ref_llvm(BasicBlock cur);
}
