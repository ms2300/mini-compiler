package ast;

import cfg.BasicBlock;

import java.util.Map;

public interface Statement {
    Type static_type_check(Type ret_type, Map<String, TypeScope> local_map);
    BasicBlock make_cfg(BasicBlock cur, BasicBlock end);
}
