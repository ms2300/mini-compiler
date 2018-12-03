package cfg;

import ast.Program;
import instructions.Instruction;
import instructions.PhiInstruction;
import llvm.LLVMValue;

import java.util.*;

public class BasicBlock {
   private final String label;
   private List<Instruction> instructions;
   private List<BasicBlock> pred;
   private List<BasicBlock> desc;
   private List<PhiInstruction> incomplete_phis;
   private Map<String, LLVMValue> ssa_map;
   private boolean sealed;

   public BasicBlock(String label) {
      this.label = label;
      instructions = new ArrayList<>();
      desc = new ArrayList<>();
      pred = new ArrayList<>();
      incomplete_phis = new ArrayList<>();
      this.ssa_map = new HashMap<>();
      this.sealed = false;
   }

   public boolean equals(Object other) {
      if (other instanceof BasicBlock) {
         if (((BasicBlock) other).getLabel().equals(label)) {
            return true;
         }
      }
      return false;
   }

   public void add_desc(BasicBlock child) { desc.add(child); }

   public void add_pred(BasicBlock above) { pred.add(above); }

   public void add_instruction(Instruction next) { instructions.add(next); }

   public List<BasicBlock> getDesc() { return desc; }
   public List<BasicBlock> getPred() { return pred; }

   public String getLabel() { return this.label; }

   public String to_llvm() {
      String body = "";
      instructions.stream().forEach(x -> body.concat("\t" + x.toString() + "\n"));
      return label + ": \n" + body;
   }

   public List<Instruction> getInstructions() { return this.instructions; }

   public static int compare(BasicBlock lhs, BasicBlock rhs) {
      if (lhs.getLabel().equals(rhs.getLabel())) {
         Program.error("This is wild I'm telling you");
         return -99999;
      } else {
         return lhs.getLabel().compareTo(rhs.getLabel());
      }
   }

   public void write_value(String left, LLVMValue right) { ssa_map.put(left, right); }

   public void seal_block() {
      if (this.sealed) {
         return;
      }
      this.sealed = true;
      int size = incomplete_phis.size();
      for (int i = 0; i < size; i++) {
         PhiInstruction phi = incomplete_phis.get(0);
         for (BasicBlock pred : phi.getBlock().getPred()) {
            phi.add_operand(pred.read_value(phi.getId(), phi.getReg().get_type()));
            phi.add_label(pred.getLabel());
         }
         incomplete_phis.remove(phi);
         phi.set_complete();
         instructions.add(0, phi);
      }
   }

   public LLVMValue read_value(String id, String ty_string) {
      if (ssa_map.containsKey(id)) {
         return ssa_map.get(id);
      } else {
         return read_value_from_pred(id, ty_string);
      }
   }

   private LLVMValue read_value_from_pred(String id, String ty_string) {
      if (!this.sealed) {
         PhiInstruction phi = new PhiInstruction(ty_string, this, id);
         write_value(id, phi.getReg());
         incomplete_phis.add(phi);
         return phi.getReg();
      } else if (pred.size() == 0) {
         Program.error("Variable " + id + " used before instantiation");
         return null;
      } else if (pred.size() == 1) {
         LLVMValue val = pred.get(0).read_value(id, ty_string);
         write_value(id, val);
         return val;
      } else {
         PhiInstruction phi = new PhiInstruction(ty_string, this, id);
         write_value(id, phi.getReg());
         pred.stream().forEach(x -> {
            phi.add_operand(x.read_value(id, ty_string));
            phi.add_label(x.getLabel());
         });
         phi.set_complete();
         instructions.add(0, phi);
         return phi.getReg();
      }
   }
}
