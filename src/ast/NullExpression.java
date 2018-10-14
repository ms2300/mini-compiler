package ast;

import java.util.Map;

public class NullExpression extends AbstractExpression {
   public NullExpression(int lineNum)
   {
      super(lineNum);
   }

   public Type static_type_check(Map<String, TypeScope> local_map) { return new VoidType(); }
}
