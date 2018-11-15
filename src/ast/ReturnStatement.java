package ast;

import cfg.BasicBlock;

import java.util.Map;

public class ReturnStatement extends AbstractStatement {
   private final Expression expression;

   public ReturnStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (!expression.static_type_check(local_map).getClass().equals(ret_type.getClass())) {
         Program.error("Invalid return statement line : " + this.getLineNum());
      }
      return ret_type;
   }

   public BasicBlock make_cfg(BasicBlock cur, BasicBlock end) {
      /*
         Add instructions
       */
      end.add_pred(cur);
      cur.add_desc(end);
      return end;
   }
}
