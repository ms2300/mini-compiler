package cfg;

public class Label {
   private static int label = 0;
   private static int register = 0;
   private static int phi = 0;
   private static boolean ssa = true;

   public static String nextBlockLabel() {
      return "LU" + Integer.toString(label++); }

   public static int nextRegister() { return register++; }

   public static int nextPhi() { return phi++; }

   public static void setSSA(boolean n) { ssa = n; }

   public static boolean isSSA() { return ssa; }
}
