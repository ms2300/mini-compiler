package ast;

import java.util.Map;

public class DeleteStatement extends AbstractStatement {
   private final Expression expression;

   public DeleteStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      Type del = expression.static_type_check(local_map);
      if (!(del instanceof StructType)) {
         Program.error("Invalid delete statement line : " + this.getLineNum());
      }
      return del;
   }
}
