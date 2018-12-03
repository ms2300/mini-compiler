package ast;

import cfg.BasicBlock;

import java.util.Map;

public class LvalueId implements Lvalue {
   private final int lineNum;
   private final String id;
   private String reg_type;
   private Map<String, TypeScope> local_map;
   private boolean global;

   public LvalueId(int lineNum, String id) {
      this.lineNum = lineNum;
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      this.local_map = local_map;
      if (local_map.containsKey(id)) {
         this.global = false;
         return local_map.get(id).getTy();
      } else if (Program.var_map.containsKey(id)) {
         this.global = true;
         Type r = Program.var_map.get(id).getTy();
         if (r instanceof FuncType) {
            this.reg_type = ((FuncType) r).getRetType().to_llvm();
            return ((FuncType) r).getRetType();
         }
         this.reg_type = r.to_llvm();
         return r;
      }
      Program.error("Invalid Lvalue line : " + lineNum);
      return null;
   }

   public String ref_llvm(BasicBlock cur) {
      String l;
      if (this.local_map.containsKey(id)) {
         TypeScope ty_scope = this.local_map.get(id);
         if (ty_scope.getScope() == TypeScope.Scope.Param) {
            l = "%_P_" + id;
         } else {
            l = "%" + id;
         }
      } else {
         l = "@" + id;
      }
      return l;
   }

   public boolean is_global() { return this.global; }
   public String getId() { return this.id; }
   public String getReg_type() { return this.reg_type; }
}
