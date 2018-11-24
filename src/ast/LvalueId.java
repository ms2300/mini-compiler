package ast;

import cfg.BasicBlock;
import instructions.LoadInstruction;
import llvm.Register;

import java.util.Map;

public class LvalueId implements Lvalue {
   private final int lineNum;
   private final String id;
   private Map<String, TypeScope> local_map;

   public LvalueId(int lineNum, String id) {
      this.lineNum = lineNum;
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      this.local_map = local_map;
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

   public String ref_llvm(BasicBlock cur) {
      TypeScope ty_scope;
      String l;
      if (Program.var_map.containsKey(id)) {
         ty_scope = Program.var_map.get(id);
         l = "@" + id;
      } else {
         ty_scope = this.local_map.get(id);
         if (ty_scope.getScope() == TypeScope.Scope.Param) {
            l = "%_P_" + id;
         } else {
            l = "%" + id;
         }
      }
      return l;
   }
}
