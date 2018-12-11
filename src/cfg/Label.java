package cfg;

public class Label {
   private static int label = 0;
   private static int register = 0;
   private static int phi = 0;
   private static boolean ssa = true;
   private static boolean optimize = false;

   public static String nextBlockLabel() { return "LU" + Integer.toString(label++); }

   public static int nextRegister() { return register++; } // I am a bitch boy

   public static int nextPhi() { return phi++; }

   public static void setSSA(boolean n) { ssa = n; }

   public static boolean isSSA() { return ssa; }

   public static void setOP(boolean n) { optimize = n; }

   public static boolean useOP() { return optimize; }
}
