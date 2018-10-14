package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Program {
   private final List<TypeDeclaration> types;
   private final List<Declaration> decls;
   private final List<Function> funcs;
   public static Map<String, TypeScope> var_map;
   public static Map<String, Map<String, Type>> struct_map;

   public Program(List<TypeDeclaration> types, List<Declaration> decls, List<Function> funcs) {
      this.types = types;
      this.decls = decls;
      this.funcs = funcs;
      create_var_map();
      create_struct_map();
   }

   private void create_var_map() {
      Map<String, TypeScope> var_map = new HashMap<>();
      AtomicBoolean has_main = new AtomicBoolean();
      decls.forEach(decl -> var_map.put(decl.getName(), new TypeScope(decl.getType(), TypeScope.Scope.Global)));
      types.forEach(type -> var_map.put(type.getName(),
                      new TypeScope(new StructType(-1, type.getName()), TypeScope.Scope.Global))
      );
      funcs.forEach(func -> {
                  if (func.getName().equals("main")) { has_main.set(true); }
                  var_map.put(func.getName(),
                          new TypeScope(new FuncType(func.getParams(), func.getRetType()), TypeScope.Scope.Global));
      });
      if (has_main.get()) { this.var_map = var_map; }
      else { Program.error("No main function in Mini program"); }
   }

   private void create_struct_map() {
      Map<String, Map<String, Type>> struct_map = new HashMap<>();
      types.forEach(type -> {
         Map<String, Type> struct = new HashMap<>();
         type.getFields().forEach(field -> struct.put(field.getName(), field.getType()));
         struct_map.put(type.getName(), struct);
      });
      this.struct_map = struct_map;
   }

   public void static_type_check() { funcs.forEach(func -> func.static_type_check()); }

   public static void error(String msg) {
      System.err.println(msg);
      System.exit(1);
   }
}
