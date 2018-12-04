package ast;

import cfg.BasicBlock;
import llvm.Register;

import java.util.List;
import java.util.Map;

public interface Statement {
    boolean static_type_check(Type ret_type, Map<String, TypeScope> local_map);
    BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val, List<BasicBlock> blocks);
}
