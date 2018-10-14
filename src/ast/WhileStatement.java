package ast;

import java.util.Map;

public class WhileStatement extends AbstractStatement {
   private final Expression guard;
   private final Statement body;

   public WhileStatement(int lineNum, Expression guard, Statement body) {
      super(lineNum);
      this.guard = guard;
      this.body = body;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (!(guard.static_type_check(local_map) instanceof BoolType)) {
         Program.error("Invalid guard to loop line : " + this.getLineNum());
      }
      return body.static_type_check(ret_type, local_map);
   }
}
