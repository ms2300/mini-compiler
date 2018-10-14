package ast;

import java.util.Map;

public class LvalueDot implements Lvalue {
   private final int lineNum;
   private final Expression left;
   private final String id;

   public LvalueDot(int lineNum, Expression left, String id) {
      this.lineNum = lineNum;
      this.left = left;
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      Type s = left.static_type_check(local_map);
      if (s instanceof StructType) {
         Map<String, Type> st = Program.struct_map.get(((StructType) s).getName());
         Type ret = st.get(id);
         if (ret != null) {
            return ret;
         }
      }
      Program.error("Invalid Lvalue line : " + lineNum);
      return null;
   }
}
