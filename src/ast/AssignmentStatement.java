package ast;

import cfg.BasicBlock;
import llvm.Register;

import java.util.Map;

public class AssignmentStatement extends AbstractStatement {
   private final Lvalue target;
   private final Expression source;

   public AssignmentStatement(int lineNum, Lvalue target, Expression source) {
      super(lineNum);
      this.target = target;
      this.source = source;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      Type s = source.static_type_check(local_map);
      if (!target.static_type_check(local_map).getClass()
            .equals(s.getClass())) {
         if (s instanceof VoidType) {
            return new VoidType();
         }
         Program.error("Invalid assignment line : " + this.getLineNum());
      }
      return new VoidType();
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val) {
      /*
         Add instructions
       */
      return cur;
   }
}
