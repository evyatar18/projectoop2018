package project.game.levels.io.blocks;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import project.game.levels.io.PropertyParser;
import project.game.levels.io.blocks.properties.BlockProperty;
import project.game.levels.io.blocks.properties.FillProperty;
import project.game.levels.io.blocks.properties.HeightProperty;
import project.game.levels.io.blocks.properties.HitsProperty;
import project.game.levels.io.blocks.properties.StrokeProperty;
import project.game.levels.io.blocks.properties.WidthProperty;
import project.game.objects.collidables.block.Block;
import project.geometry.Point;

/**
 * {@link BlocksDefinitionReader} reads blocks definitions from a given input.
 */
public class BlocksDefinitionReader {

    /**
     * This class holds a pair of a symbol and a value.
     *
     * @param <T> : the type of the value
     */
    private static class SymbolValuePair<T> {

        private final String symbol;
        private final T value;


        /**
         * Construct a new pair.
         * @param symbol : the symbol
         * @param value : the value
         */
        public SymbolValuePair(String symbol, T value) {
            this.symbol = symbol;
            this.value = value;
        }
    }

    /**
     * {@link PropertyDrivenBlockCreator} is a {@link BlockCreator} which
     *  creates blocks with given properties.
     */
    private static class PropertyDrivenBlockCreator implements BlockCreator {

        private final Map<String, String> props;
        private final Map<String, BlockProperty> nameToProperty;

        private final BlockCreator base;

        /**
         * Generate a {@link PropertyDrivenBlockCreator} from given properties.
         * @param props : a map of name-value properties
         * @param base : a base block creator to wrap around
         */
        public PropertyDrivenBlockCreator(Map<String, String> props, BlockCreator base) {
            this.props = props;
            this.base = base;

            this.nameToProperty = new HashMap<>();

            // calculate firsthand the matches between property names to the actual properties
            for (String propName : props.keySet()) {
                for (BlockProperty bp : BLOCK_PROPERTIES) {
                    if (bp.is(propName)) {
                        this.nameToProperty.put(propName, bp);
                        break;
                    }
                }
            }
        }

        @Override
        public Block create(int xpos, int ypos) {
            Block b = this.base == null ? new Block(new Point(xpos, ypos)) : this.base.create(xpos, ypos);

            for (Entry<String, String> prop : this.props.entrySet()) {
                BlockProperty currProperty = this.nameToProperty.get(prop.getKey());

                // might have a property which is not block-related
                if (currProperty == null) {
                    continue;
                }

                currProperty.apply(prop.getKey(), prop.getValue(), b);
            }

            return b;
        }
    }

    private static final BlockProperty[] BLOCK_PROPERTIES = new BlockProperty[] {
            new FillProperty(),
            new HeightProperty(),
            new WidthProperty(),
            new HitsProperty(),
            new StrokeProperty()
    };

    private static final String DEFAULT = "default";
    private static final String BLOCK = "bdef";
    private static final String SPACER = "sdef";

    /**
     * Create a BlocksFromSymbolFactory from a given reader.
     * @param reader : the reader
     * @return a {@link BlocksFromSymbolFactory}
     */
    public static BlocksFromSymbolFactory fromReader(Reader reader) {
        BufferedReader br = new BufferedReader(reader);

        Map<String, BlockCreator> bCreators = new HashMap<>();
        Map<String, Integer> spacers = new HashMap<>();

        BlockCreator defaultCreator = null;

        Iterator<String> lines = br.lines().iterator();

        while (lines.hasNext()) {
            String line = lines.next();

            if (line.startsWith(DEFAULT)) {
                // default properties
                defaultCreator = generateBlockCreator(line, null).value;
            } else if (line.startsWith(BLOCK)) {
                // block properties
                SymbolValuePair<BlockCreator> pair = generateBlockCreator(line, defaultCreator);
                bCreators.put(pair.symbol, pair.value);
            } else if (line.startsWith(SPACER)) {
                // spacer properties
                SymbolValuePair<Integer> pair = generateSpacer(line);
                spacers.put(pair.symbol, pair.value);
            }
        }

        return new BlocksFromSymbolFactory(spacers, bCreators);
    }

    /**
     * Generates a spacer from a given spacer line.
     * @param line : the line
     * @return a pair containing the symbol of the spacer and its width
     */
    private static SymbolValuePair<Integer> generateSpacer(String line) {
        Map<String, String> props = PropertyParser.parseProperties(line);

        String symbol = props.get("symbol");
        Integer val = Integer.parseInt(props.get("width"));

        return new SymbolValuePair<Integer>(symbol, val);
    }

    /**
     * Generates a {@link BlockCreator} from a given line.
     * @param input : the input line
     * @param base : a base {@link BlockCreator} to use
     * @return a new {@link BlockCreator} instance
     */
    private static SymbolValuePair<BlockCreator> generateBlockCreator(String input, BlockCreator base) {
        // get all properties found in the input line
        Map<String, String> props = PropertyParser.parseProperties(input);

        // get the symbol
        String symbol = props.get("symbol");

        BlockCreator creator = new PropertyDrivenBlockCreator(props, base);

        return new SymbolValuePair<BlockCreator>(symbol, creator);
    }
}
