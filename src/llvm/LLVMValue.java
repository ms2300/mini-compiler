package llvm;

import instructions.Instruction;

import java.util.List;

public interface LLVMValue {
   String get_name();
   String get_type();
   void add_use(Instruction x);
   void set_def(Instruction x);
   List<Instruction> get_uses();
   Instruction get_def();

}
