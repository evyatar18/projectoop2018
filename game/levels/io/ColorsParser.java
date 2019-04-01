package project.game.levels.io;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link ColorsParser} parses colors.
 */
public class ColorsParser {

    /**
     * This pattern checks whether a given string is a color.
     * Contains an inner capture group which captures the value x as in color(x)
     */
    private static final Pattern COLOR_WARPPER_PATTERN = Pattern.compile("color\\((.*)\\)");

    /**
     * This pattern identifies whether a given format is of 'rgb' format.
     * RGB\((\d{1,3}),(\d{1,3}),(\d{1,3})\)
     */
    private static final Pattern RGB_PATTERN = Pattern.compile("RGB\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)");

    /**
     * A mapping between color names to actual colors.
     */
    private static final Map<String, Color> STRING_TO_COLOR_MAP;

    static {
        /* fill the string to color map */
        STRING_TO_COLOR_MAP = new HashMap<>();

        STRING_TO_COLOR_MAP.put("black", Color.black);
        STRING_TO_COLOR_MAP.put("blue", Color.blue);
        STRING_TO_COLOR_MAP.put("cyan", Color.cyan);
        STRING_TO_COLOR_MAP.put("gray", Color.gray);
        STRING_TO_COLOR_MAP.put("lightGray", Color.lightGray);
        STRING_TO_COLOR_MAP.put("green", Color.green);
        STRING_TO_COLOR_MAP.put("orange", Color.orange);
        STRING_TO_COLOR_MAP.put("pink", Color.pink);
        STRING_TO_COLOR_MAP.put("red", Color.red);
        STRING_TO_COLOR_MAP.put("white", Color.white);
        STRING_TO_COLOR_MAP.put("yellow", Color.yellow);
    }

    /**
     * Generate a color from a given string.
     * @param s : the string
     * @return a color. or if the string was invalid null.
     */
    public Color colorFromString(String s) {
        Matcher m = COLOR_WARPPER_PATTERN.matcher(s);

        if (!m.matches()) {
            return null;
        }

        return colorFromInnerString(m.group(1));
    }

    /**
     * Get a color from its inner value[the value x inside color(x)].
     * @param innerValue : the inner value of the color function
     * @return a color matching or null if none was found
     */
    private Color colorFromInnerString(String innerValue) {
        // check if the color matches
        Matcher rgbMatcher = RGB_PATTERN.matcher(innerValue);

        if (rgbMatcher.matches()) {
            /* get red, green and blue values from the string */
            int r = Integer.parseInt(rgbMatcher.group(1));
            int g = Integer.parseInt(rgbMatcher.group(2));
            int b = Integer.parseInt(rgbMatcher.group(3));

            // make sure all the color values are valid
            if (r > 255 || g > 255 || b > 255) {
                return null;
            }

            // return a matching color
            return new Color(r, g, b);
        } else {
            return STRING_TO_COLOR_MAP.get(innerValue);
        }
    }
}
