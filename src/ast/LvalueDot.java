package ast;

import cfg.BasicBlock;
import instructions.GetPtrInstruction;
import instructions.Instruction;
import instructions.LoadInstruction;
import llvm.LLVMValue;

import java.util.List;
import java.util.Map;

public class LvalueDot implements Lvalue {
   private final int lineNum;
   private final Expression left;
   private final String id;
   private String struct_name;
   private String reg_type;
   private Type result_type;

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
         this.result_type = ret;
         if (ret != null) {
            return ret;
         }
      }
      Program.error("Invalid Lvalue line : " + lineNum);
      return null;
   }

   public String ref_llvm(BasicBlock cur) {
      LLVMValue l = left.get_llvm(cur);
      List<String> indices = Program.naive_struct_map.get(this.struct_name);
      GetPtrInstruction g = new GetPtrInstruction(l.get_type(), l, Integer.toString(indices.indexOf(id)), result_type.to_llvm());
      cur.add_instruction(g);
      this.reg_type = g.getReg().get_type();
      return g.getReg().get_name();
   }

   public String getReg_type() { return this.reg_type; }
}
