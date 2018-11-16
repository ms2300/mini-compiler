package ast;

import cfg.BasicBlock;
import instructions.GetPtrInstruction;
import llvm.LLVMValue;

import java.util.Map;

public class DotExpression extends AbstractExpression {
   private final Expression left;
   private final String id;

   public DotExpression(int lineNum, Expression left, String id) {
      super(lineNum);
      this.left = left;
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      Type s = left.static_type_check(local_map);
      if (s instanceof StructType) {
         Map<String, Type> ty_map = Program.struct_map.get(((StructType) s).getName());
         Type ret = ty_map.get(id);
         if (ret == null) {
            Program.error("Invalid dot expression line : " + this.getLineNum());
         }
         return ret;
      }
      Program.error("Invalid dot expression line : " + this.getLineNum());
      return null;
   }

   public LLVMValue get_llvm(BasicBlock cur) {
      LLVMValue l = left.get_llvm(cur);
      /*
         GET CORRECT INDEX
       */
      GetPtrInstruction g = new GetPtrInstruction(l.get_type(), l, "2");
      cur.add_instruction(g);
      return g.getReg();
   }
}
