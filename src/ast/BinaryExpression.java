package ast;

import cfg.BasicBlock;
import instructions.CmpInstruction;
import instructions.MathematicalInstruction;
import instructions.ZextInstruction;
import llvm.LLVMValue;

import java.util.Map;

public class BinaryExpression extends AbstractExpression {
   private final Operator operator;
   private final Expression left;
   private final Expression right;

   private BinaryExpression(int lineNum, Operator operator, Expression left, Expression right) {
      super(lineNum);
      this.operator = operator;
      this.left = left;
      this.right = right;
   }

   public static BinaryExpression create(int lineNum, String opStr, Expression left, Expression right) {
      switch (opStr) {
         case TIMES_OPERATOR:
            return new BinaryExpression(lineNum, Operator.TIMES, left, right);
         case DIVIDE_OPERATOR:
            return new BinaryExpression(lineNum, Operator.DIVIDE, left, right);
         case PLUS_OPERATOR:
            return new BinaryExpression(lineNum, Operator.PLUS, left, right);
         case MINUS_OPERATOR:
            return new BinaryExpression(lineNum, Operator.MINUS, left, right);
         case LT_OPERATOR:
            return new BinaryExpression(lineNum, Operator.LT, left, right);
         case LE_OPERATOR:
            return new BinaryExpression(lineNum, Operator.LE, left, right);
         case GT_OPERATOR:
            return new BinaryExpression(lineNum, Operator.GT, left, right);
         case GE_OPERATOR:
            return new BinaryExpression(lineNum, Operator.GE, left, right);
         case EQ_OPERATOR:
            return new BinaryExpression(lineNum, Operator.EQ, left, right);
         case NE_OPERATOR:
            return new BinaryExpression(lineNum, Operator.NE, left, right);
         case AND_OPERATOR:
            return new BinaryExpression(lineNum, Operator.AND, left, right);
         case OR_OPERATOR:
            return new BinaryExpression(lineNum, Operator.OR, left, right);
         default:
            throw new IllegalArgumentException();
      }
   }

   private static final String TIMES_OPERATOR = "*";
   private static final String DIVIDE_OPERATOR = "/";
   private static final String PLUS_OPERATOR = "+";
   private static final String MINUS_OPERATOR = "-";
   private static final String LT_OPERATOR = "<";
   private static final String LE_OPERATOR = "<=";
   private static final String GT_OPERATOR = ">";
   private static final String GE_OPERATOR = ">=";
   private static final String EQ_OPERATOR = "==";
   private static final String NE_OPERATOR = "!=";
   private static final String AND_OPERATOR = "&&";
   private static final String OR_OPERATOR = "||";

   public static enum Operator {
      TIMES, DIVIDE, PLUS, MINUS, LT, GT, LE, GE, EQ, NE, AND, OR
   }

   public Type static_type_check(Map<String, TypeScope> local_map) {
      switch (operator) {
         case TIMES: case DIVIDE: case PLUS: case MINUS:
            if (left.static_type_check(local_map) instanceof IntType &&
                  right.static_type_check(local_map) instanceof IntType) {
               return new IntType();
            }
            break;
         case LT: case GT: case LE: case GE: case EQ: case NE:
            Type rt = right.static_type_check(local_map);
            if ((left.static_type_check(local_map) instanceof IntType &&
                  (rt instanceof IntType || rt instanceof VoidType)) ||
                (left.static_type_check(local_map) instanceof StructType &&
                      (rt instanceof StructType || rt instanceof VoidType))) {
               return new BoolType();
            }
            break;
         case AND: case OR:
            if (left.static_type_check(local_map) instanceof BoolType &&
                  right.static_type_check(local_map) instanceof BoolType) {
               return new BoolType();
            }
            break;
      }
      Program.error("Invalid binary expression line : " + this.getLineNum());
      return null;
   }

   public LLVMValue get_llvm(BasicBlock cur) {
      LLVMValue op1 = left.get_llvm(cur);
      LLVMValue op2 = right.get_llvm(cur);
      switch (operator) {
         case TIMES: {
            MathematicalInstruction m = new MathematicalInstruction("mul", "i32", op1, op2);
            cur.add_instruction(m);
            return m.getReg();
         } case DIVIDE: {
            MathematicalInstruction m = new MathematicalInstruction("sdiv","i32", op1, op2);
            cur.add_instruction(m);
            return m.getReg();
         } case PLUS: {
            MathematicalInstruction m = new MathematicalInstruction("add","i32", op1, op2);
            cur.add_instruction(m);
            return m.getReg();
         } case MINUS: {
            MathematicalInstruction m = new MathematicalInstruction("sub","i32", op1, op2);
            cur.add_instruction(m);
            return m.getReg();
         } case LE: {
            CmpInstruction c = new CmpInstruction("sle", "i32", op1, op2);
            //ZextInstruction z = new ZextInstruction(c.getReg());
            cur.add_instruction(c);
            //cur.add_instruction(z);
            return c.getReg();
         } case LT: {
            CmpInstruction c = new CmpInstruction("slt", "i32", op1, op2);
            //ZextInstruction z = new ZextInstruction(c.getReg());
            cur.add_instruction(c);
            //cur.add_instruction(z);
            return c.getReg();
         } case GE: {
            CmpInstruction c = new CmpInstruction("sge", "i32", op1, op2);
            //ZextInstruction z = new ZextInstruction(c.getReg());
            cur.add_instruction(c);
            //cur.add_instruction(z);
            return c.getReg();
         } case GT: {
            CmpInstruction c = new CmpInstruction("sgt", "i32", op1, op2);
            //ZextInstruction z = new ZextInstruction(c.getReg());
            cur.add_instruction(c);
            //cur.add_instruction(z);
            return c.getReg();
         } case EQ: {
            CmpInstruction c = new CmpInstruction("eq", op1.get_type(), op1, op2);
            //ZextInstruction z = new ZextInstruction(c.getReg());
            cur.add_instruction(c);
            //cur.add_instruction(z);
            return c.getReg();
         } case NE: {
            CmpInstruction c = new CmpInstruction("ne", op1.get_type(), op1, op2);
            //ZextInstruction z = new ZextInstruction(c.getReg());
            cur.add_instruction(c);
            //cur.add_instruction(z);
            return c.getReg();
         } case OR: {
            MathematicalInstruction m = new MathematicalInstruction("or", "i1", op1, op2);
            cur.add_instruction(m);
            return m.getReg();
         } case AND: {
            MathematicalInstruction m = new MathematicalInstruction("and", "i1", op1, op2);
            cur.add_instruction(m);
            return m.getReg();
         }
      }
      Program.error("Error in llvm generation binary expression line : " + this.getLineNum());
      return null;
   }
}
