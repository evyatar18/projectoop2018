package project.game.levels.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link PropertyParser} parses properties from strings.
 */
public class PropertyParser {

    /**
     * This pattern captures a duo of name-value in the format of
     * name_of_property-1:value_of_property-1.
     */
    public static final Pattern NAME_VALUE_PATTERN = Pattern.compile("((\\w|-)+):(([^\\s]|( (?!((\\w|-)+):)))+)");

    /**
     * Parses all the found properties in the given string.
     * @param str : the string to find the properties of
     * @return a list of properties contained in the string
     */
    public static Map<String, String> parseProperties(String str) {
        Matcher m = NAME_VALUE_PATTERN.matcher(str);
        Map<String, String> properties = new HashMap<>();

        while (m.find()) {
            String name = m.group(1);
            String value = m.group(3);

            properties.put(name, value);
        }

        return properties;
    }

    /**
     * Parses all the properties found in the given {@link BufferedReader}.
     * @param reader : a reader to parse the properties from
     * @return a mapping of all the properties
     * @throws IOException : if an error has occurred while parsing
     */
    public static Map<String, String> parseProperties(BufferedReader reader) throws IOException {
        String ln;

        Map<String, String> map = new HashMap<>();

        while ((ln = reader.readLine()) != null) {
            map.putAll(parseProperties(ln));
        }

        return map;
    }
}
