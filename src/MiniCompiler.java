import ast.Function;
import ast.Program;
import cfg.CfgToLLVM;
import cfg.FunctionCFG;
import cfg.Label;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

import java.io.*;
import java.util.stream.Collectors;

public class MiniCompiler {
   public static void main(String[] args) throws FileNotFoundException {
      parseParameters(args);

      CommonTokenStream tokens = new CommonTokenStream(createLexer());
      MiniParser parser = new MiniParser(tokens);
      ParseTree tree = parser.program();

      if (parser.getNumberOfSyntaxErrors() != 0) {
         error("Parsing error");
      }
      MiniToAstProgramVisitor programVisitor = new MiniToAstProgramVisitor();
      ast.Program program = programVisitor.visit(tree);
      program.static_type_check();
      System.out.println("Static Type Checking Passed");
      List<FunctionCFG> cfgs = null;
      if (_outputSSA) {
         System.out.println("Generating ssa-based LLVM");
         if (_optimizations) {
            System.out.println("Using optimizations");
            Label.setOP(true);
         }
      } else if (_outputStack) {
         System.out.println("Generating stack-based LLVM");
         Label.setSSA(false);
      }
      cfgs = program.getFuncs().stream().map(Function::toCfg).collect(Collectors.toList());
      writeFile(cfgs, program);
   }

   private static String _inputFile = null;
   private static boolean _outputStack = false;
   private static boolean _outputSSA = false;
   private static boolean _optimizations = false;

   private static void writeFile(List<FunctionCFG> cfgs, Program program) throws FileNotFoundException {
      String llvm_gen = CfgToLLVM.process_cfgs(cfgs, program);
      PrintWriter writer = new PrintWriter(_inputFile.split("\\.")[0] + ".ll");
      writer.println(llvm_gen);
      writer.close();
   }

   private static void parseParameters(String [] args) {
      for (int i = 0; i < args.length; i++) {
         if (args[i].charAt(0) == '-') {
            if (args[i].equals("-stack")) {
               _outputStack = true;
            } else if (args[i].equals("-llvm")) {
               _outputSSA = true;
            } else if (args[i].equals("-opt")) {
               _optimizations = true;
            } else {
               System.err.println("unexpected option: " + args[i]);
               System.exit(1);
            }
         } else if (_inputFile != null) {
            System.err.println("too many files specified");
            System.exit(1);
         } else {
            _inputFile = args[i];
         }
      }
   }

   private static void error(String msg) {
      System.err.println(msg);
      System.exit(1);
   }

   private static MiniLexer createLexer() {
      try {
         CharStream input;
         if (_inputFile == null) {
            input = CharStreams.fromStream(System.in);
         } else {
            input = CharStreams.fromFileName(_inputFile);
         }
         return new MiniLexer(input);
      }
      catch (IOException e) {
         System.err.println("file not found: " + _inputFile);
         System.exit(1);
         return null;
      }
   }
}
