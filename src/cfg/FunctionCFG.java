package cfg;

import ast.Declaration;
import ast.Statement;
import java.util.List;

public class FunctionCFG {
   private BasicBlock enter;
   private BasicBlock exit;
   private final List<Declaration> params;
   private final List<Declaration> locals;
   private final Statement body;

   public FunctionCFG(List<Declaration> params, List<Declaration> locals, Statement body) {
      this.params = params;
      this.locals = locals;
      this.body = body;
      createCFG();
   }

   public void createCFG() {
      enter = new BasicBlock("Start");
      exit = new BasicBlock("End");
      BasicBlock cur = new BasicBlock(Label.next());
      enter.add_desc(cur);
      cur.add_pred(enter);
      BasicBlock fin = body.make_cfg(cur, exit);
      exit.add_pred(fin);
      fin.add_desc(exit);
   }
}
