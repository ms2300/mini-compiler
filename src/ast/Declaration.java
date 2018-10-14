package ast;

public class Declaration {
   private final int lineNum;
   private final Type type;
   private final String name;

   public Declaration(int lineNum, Type type, String name) {
      this.lineNum = lineNum;
      this.type = type;
      this.name = name;
   }

   public Type getType() { return this.type; }

   public String getName() { return this.name; }
}
