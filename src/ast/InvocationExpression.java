package ast;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvocationExpression extends AbstractExpression {
   private final String name;
   private final List<Expression> arguments;

   public InvocationExpression(int lineNum, String name, List<Expression> arguments) {
      super(lineNum);
      this.name = name;
      this.arguments = arguments;
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      Type inv = Program.var_map.get(name).getTy();
      if (inv instanceof FuncType) {
         FuncType f = (FuncType) inv;
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
}
