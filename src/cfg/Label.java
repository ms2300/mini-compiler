package cfg;

public class Label {
   private static int label = 0;
   private static int register = 0;
   private static int phi = 0;

   public static String nextBlockLabel() {
      return "LU" + Integer.toString(label++); }

   public static int nextRegister() { return register++; }

   public static int nextPhi() { return phi++; }
}
