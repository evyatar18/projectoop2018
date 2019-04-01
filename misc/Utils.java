package project.misc;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import project.geometry.Point;

/**
 * A utilities class.
 */
public class Utils {

    /**
     * Converts an array of strings to array of corresponding ints.
     * @param strs : the strings to be converted
     * @return an array of ints containing the values of the corresponding ints
     */
    public static int[] stringsToInts(String[] strs) {
        int[] arr = new int[strs.length];

        for (int i = 0; i < strs.length; i++) {
            arr[i] = Integer.parseInt(strs[i]);
        }

        return arr;
    }

    /**
     * Splits an array of ints in the middle.
     * @param arr : the array to be splitted.
     * @return an array containing 2 int arrays that are the split result.
     */
    public static int[][] splitIntArray(int[] arr) {
        // create the two splitted arrays
        int[][] splitted = new int[2][];

        // create the first array
        int sizeArr1 = arr.length / 2;
        splitted[0] = new int[sizeArr1];

        // create the second array
        int sizeArr2 = arr.length - sizeArr1;
        splitted[1] = new int[sizeArr2];

        /* fill the first splitted array */
        for (int i = 0; i < sizeArr1; i++) {
            splitted[0][i] = arr[i];
        }

        /* fill the second splitted array */
        for (int i = sizeArr1; i < sizeArr1 + sizeArr2; i++) {
            splitted[1][i - sizeArr1] = arr[i];
        }

        return splitted;
    }

    /**
     * Get the slope of a line.
     * @param p1 : one point on the line
     * @param p2 : second point on the line
     * @return the slope
     */
    public static double getSlope(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return dy / dx;
    }

    /**
     * Check whether two values are within the same epsilon range.
     * @param d1 : the first double
     * @param d2 : the second double
     * @param epsilon : the epsilon
     * @return true if they are. false otherwise.
     */
    public static boolean equalWithinEpsilon(double d1, double d2, double epsilon) {
        return Math.abs(d1 - d2) <= epsilon;
    }

    /**
     * Normalize a number to a given range.
     * @param num : the number to normalize
     * @param a : the smallest number in the range
     * @param b : the biggest number in the range
     * @return the normalized number
     */
    public static double normalize(double num, double a, double b) {
        double range = b - a;

        while (num < a) {
            num += range;
        }

        while (num > b) {
            num -= range;
        }

        return num;
    }

    /**
     * Set saturation of color.
     * @param color : the color
     * @param saturation : the saturation
     * @return the saturated color
     */
    public static Color setSaturation(Color color, float saturation) {
        // get the hsb values for this color
        float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        // set customized brightness
        return Color.getHSBColor(hsbValues[0], saturation, hsbValues[2]);
    }

    /**
     * Try to parse an integer from string. If failed return null.
     * @param str : the string
     * @return the integer or null if failed
     */
    public static Integer tryParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Query whether arrived at end of stream.
     * @param reader : a reader to check
     * @return true if arrived at the end. false otherwise.
     * @throws IOException : if an error has occurred while reading
     */
    public static boolean isEOF(Reader reader) throws IOException {
        if (!reader.markSupported()) {
            throw new UnsupportedOperationException("Can't determine eof without mark supporting stream");
        }

        // mark current position
        reader.mark(1);

        // read and check eof
        boolean eof = reader.read() == -1;

        // go back to the marked position
        if (!eof) {
            reader.reset();
        }

        return eof;
    }

    /**
     * Get a resource in the class-path.
     * @param resourcePath : the path to the resource
     * @return an input stream instance or null if no such resource was found
     */
    public static InputStream getResource(String resourcePath) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
    }

    /**
     * Get a reader for a given resource - inside the class-path.
     * @param resourcePath : the path to the resource
     * @return a reader
     */
    public static Reader getResourceReader(String resourcePath) {
        return new InputStreamReader(getResource(resourcePath));
    }

    /**
     * Get an opposite color for the given color.
     * @param c : the color
     * @return the opposite color
     */
    public static Color oppositeColor(Color c) {
        return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
    }
}
