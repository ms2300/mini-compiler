package cfg;

public class Label {
   private static int label = 0;

   public static String next() {
      return Integer.toString(label++);
   }
}
