package instructions;

import llvm.Register;

public class ReadInstruction implements Instruction {

   public ReadInstruction() {}

   public String toString() {
      return "call i32 (i8*, ...)* @scanf(i8* getelementptr inbounds ([4 x i8]* @.read, i32 0, i32 0), i32* @_read_val_)";
   }
   public Register getReg() { return null; }
}
