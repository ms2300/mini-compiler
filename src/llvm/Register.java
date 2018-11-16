package llvm;

import cfg.Label;

public class Register implements LLVMValue {
   private String type_name;
   private String reg_name;

   public Register(String type_name) {
      this.type_name = type_name;
      if (this.type_name.equals("i32")) {
         this.reg_name = "%u" + Label.nextRegister();
      } else {
         if (this.type_name.charAt(0) == '@') {
            this.reg_name = this.type_name;
         } else {
            this.reg_name = "%" + this.type_name;
         }
      }
   }

   public String get_type() { return type_name; }
   public String get_name() { return reg_name; }
}
