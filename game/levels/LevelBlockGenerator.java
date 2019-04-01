package project.game.levels;

import java.util.ArrayList;
import java.util.List;

import project.game.levels.io.blocks.BlocksFromSymbolFactory;
import project.game.objects.collidables.block.Block;

/**
 * {@link LevelBlockGenerator} generates level blocks.
 */
public class LevelBlockGenerator implements BlocksGenerator {

    private BlocksFromSymbolFactory factory;
    private List<String> lines;
    private int blockStartX;
    private int blockStartY;
    private int rowHeight;

    /**
     * Set the start x of the blocks.
     * @param i : the start x position
     */
    public void setBlockStartX(int i) {
        this.blockStartX = i;
    }


    /**
     * Set the start y of the blocks.
     * @param i : the start y position
     */
    public void setBlockStartY(int i) {
        this.blockStartY = i;
    }

    /**
     * Set the row height of each row.
     * @param i : the row height
     */
    public void setRowHeight(int i) {
        this.rowHeight = i;
    }

    /**
     * Set the factory which creates blocks for this level.
     * @param fac : the factory
     */
    public void setBlockFactory(BlocksFromSymbolFactory fac) {
        this.factory = fac;
    }

    /**
     * Set the input lines of the level.
     * @param ls : the input lines
     */
    public void setLines(List<String> ls) {
        this.lines = ls;
    }

    /**
     * Generates all the blocks according to inner information.
     * @return the blocks
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int y = this.blockStartY;

        /* go over all lines */
        for (int i = 0; i < this.lines.size(); i++) {
            int x = this.blockStartX;

            // get current line
            String ln = this.lines.get(i);

            // skip empty lines
            if (ln.length() == 0) { continue; }

            for (int j = 0; j < ln.length(); j++) {
                String symbol = ln.substring(j, j + 1);

                if (this.factory.isBlockSymbol(symbol)) {
                    // add the current block
                    Block b = this.factory.getBlock(symbol, x, y);
                    blocks.add(b);

                    // and its width
                    x += b.width();

                } else if (this.factory.isSpaceSymbol(symbol)) {
                    // if this is a space, just add it
                    x += this.factory.getSpaceWidth(symbol);
                }
            }

            // go down one row
            y += this.rowHeight;
        }

        return blocks;
    }

    @Override
    public List<Block> generate() {
        return blocks();
    }

    @Override
    public int amount() {
        throw new UnsupportedOperationException("Unsupported for this instance.");
    }
}
