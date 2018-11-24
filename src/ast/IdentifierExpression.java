package ast;

import cfg.BasicBlock;
import instructions.LoadInstruction;
import llvm.LLVMValue;

import java.util.Map;

public class IdentifierExpression extends AbstractExpression {
   private final String id;
   private Map<String, TypeScope> local_map;

   public IdentifierExpression(int lineNum, String id) {
      super(lineNum);
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      this.local_map = local_map;
      if (Program.var_map.containsKey(id)) {
         Type ty = Program.var_map.get(id).getTy();
         if (ty instanceof FuncType) {
            return ((FuncType) ty).getRetType();
         }
         return ty;
      } else if (local_map.containsKey(id)) {
         return local_map.get(id).getTy();
      }
      Program.error("Invalid id expression line : " + this.getLineNum());
      return null;
   }

   public LLVMValue get_llvm(BasicBlock cur) {
      TypeScope ty_scope;
      LoadInstruction l;
      if (Program.var_map.containsKey(id)) {
         ty_scope = Program.var_map.get(id);
         l = new LoadInstruction("@" + id, ty_scope.getTy().to_llvm());
      } else {
         ty_scope = this.local_map.get(id);
         if (ty_scope.getScope() == TypeScope.Scope.Param) {
            l = new LoadInstruction("%_P_" + id, ty_scope.getTy().to_llvm());
         } else {
            l = new LoadInstruction("%" + id, ty_scope.getTy().to_llvm());
         }
      }
      cur.add_instruction(l);
      return l.getReg();
   }
}
