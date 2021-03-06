package instructions;

import llvm.LLVMValue;
import llvm.Register;

public class PrintInstruction implements Instruction {
   LLVMValue value;
   Boolean println;

   public PrintInstruction(LLVMValue value, Boolean println) {
      this.value = value;
      this.println = println;
      value.add_use(this);
   }

   public String toString() {
      if (this.println) {
         return "call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.println, i32 0, i32 0), i32 " + this.value.get_name() + ")";
      } else {
         return "call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 " + this.value.get_name() + ")";
      }
   }

   public Register getReg() { return null; }
}
