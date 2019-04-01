package project.game.levels.io.levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.game.levels.LevelInformation;
import project.game.levels.io.BoundedReader;
import project.game.levels.io.PropertyParser;
import project.game.levels.io.levels.properties.BackgroundProperty;
import project.game.levels.io.levels.properties.BallVelocitiesProperty;
import project.game.levels.io.levels.properties.BlockDefinitionsProperty;
import project.game.levels.io.levels.properties.BlocksStartProperty;
import project.game.levels.io.levels.properties.LevelNameProperty;
import project.game.levels.io.levels.properties.LevelProperty;
import project.game.levels.io.levels.properties.NumBlocksProperty;
import project.game.levels.io.levels.properties.PaddleSpeedProperty;
import project.game.levels.io.levels.properties.PaddleWidthProperty;
import project.game.levels.io.levels.properties.RowHeightProperty;
import project.misc.Utils;

/**
 * {@link LevelSpecificationReader} reads levels from given input.
 */
public class LevelSpecificationReader {

    private static final LevelProperty[] LEVEL_PROPERTIES = new LevelProperty[] {
            new BackgroundProperty(),
            new BallVelocitiesProperty(),
            new BlockDefinitionsProperty(),
            new BlocksStartProperty(),
            new LevelNameProperty(),
            new NumBlocksProperty(),
            new PaddleSpeedProperty(),
            new PaddleWidthProperty(),
            new RowHeightProperty()
    };

    /**
     * Read list of level information from readers according to the specification.
     * @param reader : the reader to read from
     * @return a list of {@link LevelInformation} instances
     */
    public List<LevelInformation> fromReader(Reader reader) {
        List<LevelInformation> levels = new ArrayList<>();

        // use buffered reader because it is better
        BufferedReader br = new BufferedReader(reader);

        try {
            while (!Utils.isEOF(br)) {
                // add level
                levels.add(parseLevel(br));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return levels;
    }

    /**
     * Parse {@link LevelInformation} from a given {@link BufferedReader}.
     * @param br : the reader
     * @return a level information instance
     * @throws IOException : if an error occurred
     */
    private LevelInformation parseLevel(BufferedReader br) throws IOException {
        /* create bounded readers */
        BoundedReader levelReader = new BoundedReader("START_LEVEL", "END_LEVEL");
        BoundedReader blocksReader = new BoundedReader("START_BLOCKS", "END_BLOCKS");

        // generate level information which can be modified
        ModifiableLevelInformation mli = new ModifiableLevelInformation();

        String line;

        /* read all the lines in the file */
        while ((line = levelReader.readLine(br)) != null) {
            // parse property from line
            Map<String, String> properties = PropertyParser.parseProperties(line);

            // try applying matching properties
            for (String propName : properties.keySet()) {
                for (LevelProperty lp : LEVEL_PROPERTIES) {
                    if (lp.is(propName)) {
                        lp.apply(propName, properties.get(propName), mli);
                    }
                }
            }

            // read blocks information
            if (blocksReader.isFirstLine(line)) {
                blocksReader.setReadFirstLine(true);

                List<String> lns = blocksReader.readLines(br);
                mli.getBlockGenerator().setLines(lns);
            }
        }

        return mli;
    }
}
