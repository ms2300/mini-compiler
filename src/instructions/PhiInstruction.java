package instructions;

import cfg.BasicBlock;
import cfg.Label;
import llvm.LLVMValue;
import llvm.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PhiInstruction extends AbstractInstruction {
   private final String ty;
   private final String id;
   private final Register reg;
   private final BasicBlock block;
   private List<LLVMValue> operands;
   private List<String> labels;
   private boolean complete;

   public PhiInstruction(String ty, BasicBlock block, String id) {
      super("phi");
      this.id = id;
      this.block = block;
      this.ty = ty;
      this.reg = new Register(this.ty, Optional.of("%" + id + Label.nextPhi()));
      this.complete = false;
      operands = new ArrayList<>();
      labels = new ArrayList<>();
   }

   public Register getReg() { return this.reg; }
   public void add_operand(LLVMValue val) { operands.add(val); }
   public void add_label(String label) { labels.add(label); }
   public void set_complete() { this.complete = true; }
   public BasicBlock getBlock() { return this.block; }
   public String toString() {
      return reg.get_name() + " = " + this.getOp_code() + " " + ty + " " + get_phi_string();
   }

   private String combine_phi(String label, LLVMValue val) {
      return "[" + val.get_name() + ", %" + label + "]";
   }

   private String get_phi_string() {
      List<String> s = new ArrayList<>();
      for (int i = 0; i < labels.size(); i++) {
         s.add(combine_phi(labels.get(i), operands.get(i)));
      }
      return s.stream().collect(Collectors.joining(", "));
   }
   public String getId() { return this.id; }
}
