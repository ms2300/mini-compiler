package ast;

import java.util.Map;

public class LvalueId implements Lvalue {
   private final int lineNum;
   private final String id;

   public LvalueId(int lineNum, String id) {
      this.lineNum = lineNum;
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      if (Program.var_map.containsKey(id)) {
         Type r = Program.var_map.get(id).getTy();
         if (r instanceof FuncType) {
            return ((FuncType) r).getRetType();
         }
         return r;
      } else if (local_map.containsKey(id)) {
         return local_map.get(id).getTy();
      }
      Program.error("Invalid Lvalue line : " + lineNum);
      return null;
   }
}
