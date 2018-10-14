package ast;

import java.util.Map;

public class UnaryExpression extends AbstractExpression {
   private final Operator operator;
   private final Expression operand;

   private UnaryExpression(int lineNum, Operator operator, Expression operand) {
      super(lineNum);
      this.operator = operator;
      this.operand = operand;
   }

   public static UnaryExpression create(int lineNum, String opStr, Expression operand) {
      if (opStr.equals(NOT_OPERATOR)) {
         return new UnaryExpression(lineNum, Operator.NOT, operand);
      } else if (opStr.equals(MINUS_OPERATOR)) {
         return new UnaryExpression(lineNum, Operator.MINUS, operand);
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static final String NOT_OPERATOR = "!";
   private static final String MINUS_OPERATOR = "-";

   public static enum Operator {
      NOT, MINUS
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      switch (operator) {
         case NOT:
            if (operand.static_type_check(local_map) instanceof BoolType) {
               return new BoolType();
            }
         case MINUS:
            if (operand.static_type_check(local_map) instanceof IntType) {
               return new IntType();
            }
      }
      Program.error("Invalid unary expression line : " + this.getLineNum());
      return null;
   }
}
