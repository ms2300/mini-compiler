package ast;

import cfg.FunctionCFG;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Function {
   private final int lineNum;
   private final String name;
   private final Type retType;
   private final List<Declaration> params;
   private final List<Declaration> locals;
   private final Statement body;
   private Map<String, TypeScope> local_map;

   public Function(int lineNum, String name, List<Declaration> params,
                   Type retType, List<Declaration> locals, Statement body) {
      this.lineNum = lineNum;
      this.name = name;
      this.params = params;
      this.retType = retType;
      this.locals = locals;
      this.body = body;
      get_local_map();
   }

   private void get_local_map() {
      if (name.equals("main")) {
         if (!(retType instanceof IntType)) {
            Program.error("Main doesn't return an int");
         }
      }
      Map<String, TypeScope> local_map = new HashMap<>();
      /* Check if param is overwritten */
      locals.forEach(decl -> local_map.put(decl.getName(), new TypeScope(decl.getType(), TypeScope.Scope.Local)));
      params.forEach(decl -> local_map.put(decl.getName(), new TypeScope(decl.getType(), TypeScope.Scope.Param)));
      this.local_map = local_map;
   }

   public void static_type_check() {
      /* Check for return at some point */
      body.static_type_check(this.retType, this.local_map);
   }

   public FunctionCFG toCfg() {
      return new FunctionCFG(name, params, locals, body, retType);
   }

   public String getName() { return this.name; }

   public List<Declaration> getParams() { return this.params; }

   public Type getRetType() { return this.retType; }

}
