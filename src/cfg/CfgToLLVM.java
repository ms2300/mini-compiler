package cfg;

import ast.Declaration;
import ast.Program;
import ast.StructType;
import ast.TypeDeclaration;

import java.util.List;
import java.util.stream.Collectors;

public class CfgToLLVM {
   public static String process_cfgs(List<FunctionCFG> cfgs, Program program) {
      String header = "target triple=\"i686\"\n@_read_val_ = global i32 0\n";
      String structs = program.getTypes().stream().map(CfgToLLVM::process_type).collect(Collectors.joining("\n")) + "\n";
      String globals = program.getDecls().stream().map(CfgToLLVM::process_global).collect(Collectors.joining("\n")) + "\n\n";
      String body = cfgs.stream().map(CfgToLLVM::process_cfg).collect(Collectors.joining("\n")) + "\n";
      String footer =
            "declare i8* @malloc(i32)\n" +
            "declare void @free(i8*)\n" +
            "declare i32 @printf(i8*, ...)\n" +
            "declare i32 @scanf(i8*, ...)\n" +
            "@.println = private unnamed_addr constant [5 x i8] c\"%ld\\0A\\00\", align 1\n" +
            "@.print = private unnamed_addr constant [5 x i8] c\"%ld \\00\", align 1\n" +
            "@.read = private unnamed_addr constant [4 x i8] c\"%ld\\00\", align 1\n" +
            "@.read_scratch = common global i32 0, align 8\n";
      return header + structs + globals + body + footer;
   }

   public static String process_cfg(FunctionCFG cfg) {
      String blocks = cfg.getBlocks().stream().map(CfgToLLVM::process_block).collect(Collectors.joining("\n"));
      return cfg.getLlvm_name() + "\n{\n" + blocks + "\n}\n";
   }

   public static String process_block(BasicBlock cur) {
      String instructions = cur.getInstructions().stream()
            .map(x -> "\t" + x.toString()).collect(Collectors.joining("\n"));
      return cur.getLabel() + ":\n" + instructions;
   }

   public static String process_type(TypeDeclaration t) {
      String fields = t.getFields().stream().map(x -> x.getType().to_llvm()).collect(Collectors.joining(","));
      return "%struct." + t.getName() + " = type {" + fields + "}";
   }

   public static String process_global(Declaration d) {
      if (d.getType() instanceof StructType) {
         return "@" + d.getName() + " = common global " + d.getType().to_llvm() + " null, align 8";
      }
      return "@" + d.getName() + " =  global " + d.getType().to_llvm() + " 0";
   }
}
