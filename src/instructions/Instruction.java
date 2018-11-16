package instructions;

import llvm.Register;

public interface Instruction {
   String toString();
   Register getReg();
}
