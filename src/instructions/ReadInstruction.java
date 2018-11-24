package instructions;

import llvm.Register;

import java.util.Optional;

public class ReadInstruction implements Instruction {
   private final Register reg;

   public ReadInstruction() {
      this.reg = new Register("i32", Optional.empty());
   }

   public String toString() {
      return "call i32 (i8*, ...)* @scanf(i8* getelementptr inbounds ([4 x i8]* @.read, i32 0, i32 0), i32* " + reg.get_name() + ")";
   }
   public Register getReg() { return this.reg; }
}
