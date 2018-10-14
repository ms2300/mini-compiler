package ast;

import java.util.Map;

public class ConditionalStatement extends AbstractStatement {
   private final Expression guard;
   private final Statement thenBlock;
   private final Statement elseBlock;

   public ConditionalStatement(int lineNum, Expression guard, Statement thenBlock, Statement elseBlock) {
      super(lineNum);
      this.guard = guard;
      this.thenBlock = thenBlock;
      this.elseBlock = elseBlock;
   }

   public Type static_type_check(Type ret_type, Map<String, TypeScope> local_map) {
      if (guard.static_type_check(local_map) instanceof BoolType) {
         Type then_class = thenBlock.static_type_check(ret_type, local_map);
         if (then_class.getClass().equals(elseBlock.static_type_check(ret_type, local_map).getClass())) {
            return then_class;
         }
      }
      Program.error("Invalid conditional line : " + this.getLineNum());
      return null;
   }
}
