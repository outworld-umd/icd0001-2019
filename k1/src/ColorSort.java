public class ColorSort {

   enum Color {red, green, blue};

   public static void main (String[] param) {
      // for debugging
   }

   public static void reorder (Color[] balls) { // source: https://en.wikipedia.org/wiki/Dutch_national_flag_problem
      int i = 0; int red = 0;
      int blue = balls.length - 1;
      while (i <= blue) {
         if (balls[i] == Color.red) {
            change(balls, i, red, Color.red);
            i++; red++;
         } else if (balls[i] == Color.blue) {
            change(balls, i, blue, Color.blue);
            blue--;
         } else i++;
      }
   }

   private static void change(Color[] balls, int source, int target, Color color) {
      balls[source] = balls[target];
      balls[target] = color;
   }
}