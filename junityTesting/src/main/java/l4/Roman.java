package l4;


/**
 * Program converts arabic numbers from range 1-4999 to roman numbers
 * <a href="https://github.com/tooster-university/JavaUwr/blob/master/src/W1/Roman.java">original source</a>
 *
 * @author Tooster
 */
public class Roman {

    private static String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static int[] arabic = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    /**
     * convert arabic number to roman number
     *
     * @param x number to be converted
     * @return string representing number in roman system
     * @throws IllegalArgumentException if argument is out of range [0-4999]
     */
    public static String toRoman(int x) {

        if (x <= 0 || x >= 5000)
            throw new IllegalArgumentException("Number " + x + " out of range [0-4999].");

        StringBuilder xRoman = new StringBuilder();
        int i = 0;
        while (x > 0 && i < arabic.length) {
            if (arabic[i] <= x) {
                xRoman.append(roman[i]);
                x -= arabic[i];
            } else
                i++;
        }
        return xRoman.toString();
    }
}