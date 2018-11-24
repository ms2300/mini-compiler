package cfg;

import ast.Declaration;
import ast.Statement;
import ast.Type;
import ast.VoidType;
import instructions.AllocInstruction;
import instructions.BranchInstruction;
import instructions.ReturnVoidInstruction;
import instructions.StoreInstruction;
import llvm.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FunctionCFG {
   private BasicBlock enter;
   private BasicBlock exit;
   private List<BasicBlock> blocks;
   private String llvm_name;
   private final String name;
   private final List<Declaration> params;
   private final List<Declaration> locals;
   private final Type retType;
   private final Statement body;

   public FunctionCFG(String name, List<Declaration> params, List<Declaration> locals, Type retType, Statement body) {
      this.name = name;
      this.params = params;
      this.locals = locals;
      this.retType = retType;
      this.body = body;
      this.blocks = new ArrayList<>();
      createCFG();
   }

   private void createCFG() {
      Register ret_r = new Register(retType.to_llvm(), Optional.of("%_retval_"));
      this.enter = new BasicBlock("Entry");
      this.exit = new BasicBlock("Exit");
      this.blocks.add(enter);
      this.declare_func();
      BasicBlock fin = body.make_cfg(enter, exit, ret_r, blocks);
      this.blocks.add(exit);
      if (!(fin.getDesc().size() > 0)) {
         BranchInstruction b = new BranchInstruction(exit.getLabel());
         fin.add_instruction(b);
         exit.add_pred(fin);
         fin.add_desc(exit);
      }
      if (exit.getInstructions().size() == 0 && retType instanceof VoidType) {
         ReturnVoidInstruction r = new ReturnVoidInstruction();
         exit.add_instruction(r);
      }
   }

   private void declare_func() {
      String param_string = this.params.stream()
            .map(param -> param.getType().to_llvm() + " %" + param.getName())
            .collect(Collectors.joining(","));
      this.llvm_name = "define " + retType.to_llvm() + " @" + name + "(" + param_string + ")";
      if (!(retType instanceof VoidType)) {
         AllocInstruction a = new AllocInstruction("%_retval_", retType.to_llvm());
         this.enter.add_instruction(a);
      }
      this.params.stream().forEach(p -> {
         AllocInstruction a = new AllocInstruction("%_P_" + p.getName(), p.getType().to_llvm());
         this.enter.add_instruction(a);
      });
      this.locals.stream().forEach(l -> {
         AllocInstruction a = new AllocInstruction("%" + l.getName(), l.getType().to_llvm());
         this.enter.add_instruction(a);
      });
      this.params.stream().forEach(p -> {
         Register r1 = new Register(p.getType().to_llvm(), Optional.of("%" + p.getName()));
         Register r2 = new Register(p.getType().to_llvm(), Optional.of("%_P_" + p.getName()));
         StoreInstruction s = new StoreInstruction(p.getType().to_llvm(), r1, r2.get_name());
         this.enter.add_instruction(s);
      });
   }

   public String getLlvm_name() { return this.llvm_name; }
   public List<BasicBlock> getBlocks() { return this.blocks; }
}
