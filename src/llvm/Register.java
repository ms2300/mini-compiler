package llvm;

import cfg.Label;

import java.util.Optional;

public class Register implements LLVMValue {
   private String type_name;
   private String reg_name;

   public Register(String type_name, Optional<String> reg_name) {
      /*
      this.type_name = type_name;
      if (this.type_name.equals("i32") || this.type_name.equals("i1")) {
         this.reg_name = "%u" + Label.nextRegister();
      } else {
         this.reg_name = "%" + this.type_name;
      }
      */
      if (reg_name.isPresent()) {
         this.reg_name = reg_name.get();
      } else {
         this.reg_name = "%u" + Label.nextRegister();
      }
      this.type_name = type_name;
   }

   public String get_type() { return type_name; }
   public String get_name() { return reg_name; }
}
