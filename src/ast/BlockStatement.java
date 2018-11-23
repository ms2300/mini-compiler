package ast;

import cfg.BasicBlock;
import llvm.Register;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class BlockStatement extends AbstractStatement {
   private final List<Statement> statements;

   public BlockStatement(int lineNum, List<Statement> statements) {
      super(lineNum);
      this.statements = statements;
   }

   public static BlockStatement emptyBlock()
   {
      return new BlockStatement(-1, new ArrayList<>());
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      /* Cannot .forEach() because need to break */
      for (Statement stmnt : statements) {
         if (stmnt instanceof ReturnStatement || stmnt instanceof ReturnEmptyStatement) {
            return stmnt.static_type_check(ret_type, local_map);
         }
         stmnt.static_type_check(ret_type, local_map);
      }
      return new VoidType();
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end, Register ret_val) {
      for (Statement s : statements) {
         if (s instanceof ReturnEmptyStatement || s instanceof ReturnStatement) {
            return s.make_cfg(cur, end, ret_val);
         }
         cur = s.make_cfg(cur, end, ret_val);
      }
      return cur;
   }
}
