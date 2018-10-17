package ast;

import java.util.Map;

public class ReadExpression extends AbstractExpression {
   public ReadExpression(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new IntType(); }
}
