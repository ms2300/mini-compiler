package ast;

import java.util.Map;

public class PrintStatement extends AbstractStatement {
   private final Expression expression;

   public PrintStatement(int lineNum, Expression expression) {
      super(lineNum);
      this.expression = expression;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      expression.static_type_check(local_map);
      return new VoidType();
   }
}
