package ast;

import java.util.Map;

public class TrueExpression extends AbstractExpression {
   public TrueExpression(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new BoolType(); }
}
