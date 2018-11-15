package cfg;

import ast.Declaration;
import ast.Statement;
import ast.Type;

import java.util.List;

public class FunctionCFG {
   private BasicBlock enter;
   private BasicBlock exit;
   private final String name;
   private final List<Declaration> params;
   private final List<Declaration> locals;
   private final Type retType;
   private final Statement body;

   public FunctionCFG(String name, List<Declaration> params, List<Declaration> locals, Type retType ,Statement body) {
      this.name = name;
      this.params = params;
      this.locals = locals;
      this.retType = retType;
      this.body = body;
      createCFG();
   }

   private void createCFG() {
      enter = new BasicBlock("Entry");
      exit = new BasicBlock("Exit");
      BasicBlock cur = new BasicBlock(Label.nextBlockLabel());
      enter.add_desc(cur);
      cur.add_pred(enter);
      BasicBlock fin = body.make_cfg(cur, exit);
      if (fin != exit) {
         exit.add_pred(fin);
         fin.add_desc(exit);
      }
   }
}
