package project.game.levels.io.levels.properties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import project.game.levels.io.blocks.BlocksDefinitionReader;
import project.game.levels.io.levels.ModifiableLevelInformation;

/**
 * {@link BlockDefinitionsProperty} reads symbol and blocks information from class-path files.
 */
public class BlockDefinitionsProperty implements LevelProperty {

    @Override
    public boolean is(String name) {
        return name.equals("block_definitions");
    }

    @Override
    public void apply(String name, String value, ModifiableLevelInformation mli) {
        // get input stream for given file path
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(value);

        // get reader
        Reader r = new InputStreamReader(is);

        // set block factory according to that reader
        mli.getBlockGenerator().setBlockFactory(BlocksDefinitionReader.fromReader(r));
    }

}
