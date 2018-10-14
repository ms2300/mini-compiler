package ast;

import java.util.Map;

public class IdentifierExpression extends AbstractExpression {
   private final String id;

   public IdentifierExpression(int lineNum, String id) {
      super(lineNum);
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      if (Program.var_map.containsKey(id)) {
         Type ty = Program.var_map.get(id).getTy();
         if (ty instanceof FuncType) {
            return ((FuncType) ty).getRetType();
         }
         return ty;
      } else if (local_map.containsKey(id)) {
         return local_map.get(id).getTy();
      }
      System.out.println(local_map);
      Program.error("Invalid id expression line : " + this.getLineNum());
      return null;
   }
}
