package ast;

import cfg.BasicBlock;
import instructions.GetPtrInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.Map;

public class LvalueDot implements Lvalue {
   private final int lineNum;
   private final Expression left;
   private final String id;
   private String struct_name;

   public LvalueDot(int lineNum, Expression left, String id) {
      this.lineNum = lineNum;
      this.left = left;
      this.id = id;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      Type s = left.static_type_check(local_map);
      if (s instanceof StructType) {
         this.struct_name = ((StructType) s).getName();
         Map<String, Type> st = Program.struct_map.get(this.struct_name);
         Type ret = st.get(id);
         if (ret != null) {
            return ret;
         }
      }
      Program.error("Invalid Lvalue line : " + lineNum);
      return null;
   }

   public String ref_llvm(BasicBlock cur) {
      LLVMValue l = left.get_llvm(cur);
      int index = Program.naive_struct_map.get(this.struct_name).indexOf(id);
      GetPtrInstruction g = new GetPtrInstruction(l.get_type(), l, Integer.toString(index));
      cur.add_instruction(g);
      return g.getReg().get_name();
   }
}
