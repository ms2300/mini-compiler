package ast;

import java.util.Map;

public class NewExpression extends AbstractExpression {
   private final String id;

   public NewExpression(int lineNum, String id) {
      super(lineNum);
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      if (Program.struct_map.containsKey(id)) {
         return Program.var_map.get(id).getTy();
      }
      Program.error("Invalid new expression line : " + this.getLineNum());
      return null;
   }
}
