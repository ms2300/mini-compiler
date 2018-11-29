package ast;

import cfg.BasicBlock;
import instructions.CallInstruction;
import llvm.LLVMValue;
import llvm.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvocationExpression extends AbstractExpression {
   private final String name;
   private final List<Expression> arguments;
   private List<Type> types;

   public InvocationExpression(int lineNum, String name, List<Expression> arguments) {
      super(lineNum);
      this.name = name;
      this.arguments = arguments;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      Type inv = Program.var_map.get(name).getTy();
      if (inv instanceof FuncType) {
         FuncType f = (FuncType) inv;
         this.types = f.getParams().stream().map(x -> x.getType()).collect(Collectors.toList());
         List<Type> ev_args = arguments.stream()
               .map(x -> x.static_type_check(local_map)).collect(Collectors.toList());
         if (ev_args.size() == f.getParams().size()) {
            int i = 0;
            for (Declaration d : f.getParams()) {
               if (!ev_args.get(i).getClass().equals(d.getType().getClass())) {
                  if (ev_args.get(i) instanceof VoidType) {
                     i++;
                     continue;
                  }
                  Program.error("Invalid argument line : " + this.getLineNum());
               }
               i++;
            }
            return f.getRetType();
         }
      }
      Program.error("Invalid function type line : " + this.getLineNum());
      return null;
   }

   public LLVMValue get_llvm(BasicBlock cur) {
      Type inv = Program.var_map.get(name).getTy();
      if (inv instanceof FuncType) {
         FuncType f = (FuncType) inv;
         CallInstruction c = new CallInstruction(f.getRetType().to_llvm(), "@" + name, this.getLLVMParams(cur));
         cur.add_instruction(c);
         return c.getReg();
      }
      Program.error("Error generating llvm invocation line : " + this.getLineNum());
      return null;
   }

   public String getLLVMParams(BasicBlock cur) {
      List<String> arg_list = new ArrayList<>();
      for (int i = 0; i < arguments.size(); i++) {
         LLVMValue r = arguments.get(i).get_llvm(cur);
         arg_list.add(types.get(i).to_llvm() + " " + r.get_name());
      }
      String arg_string = arg_list.stream().collect(Collectors.joining(", "));
      return "(" + arg_string + ")";
   }
}
