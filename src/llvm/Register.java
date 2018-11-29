package llvm;

import cfg.Label;
import instructions.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Register implements LLVMValue {
   private String type_name;
   private String reg_name;
   private List<Instruction> uses;
   private Instruction def;

   public Register(String type_name, Optional<String> reg_name) {
      if (reg_name.isPresent()) {
         this.reg_name = reg_name.get();
      } else {
         this.reg_name = "%u" + Label.nextRegister();
      }
      this.type_name = type_name;
      this.uses = new ArrayList<>();
   }

   public String get_type() { return type_name; }
   public String get_name() { return reg_name; }
   public void add_use(Instruction x) { this.uses.add(x); }
   public void set_def(Instruction x) { this.def = x; }
   public List<Instruction> get_uses() { return this.uses; }
   public Instruction get_def() { return this.def; }
}
