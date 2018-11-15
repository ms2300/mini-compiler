package cfg;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock {
   private final String label;
   public boolean visit;
   private List<Instruction> instructions;
   private List<BasicBlock> pred;
   private List<BasicBlock> desc;

   public BasicBlock(String label) {
      this.label = label;
      desc = new ArrayList<>();
      pred = new ArrayList<>();
      visit = true;
   }

   public void add_desc(BasicBlock child) { desc.add(child); }

   public void add_pred(BasicBlock above) { pred.add(above); }

   public void add_instruction(Instruction next) { instructions.add(next); }

   public List<BasicBlock> getDesc() { return desc; }

   public String to_llvm() {
      String body = "";
      instructions.stream().forEach(x -> body.concat("\t" + x.toString() + "\n"));
      return label + ": \n" + body;
   }

}
