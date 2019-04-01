package project.game.levels.io.blocks;

import java.util.Map;

import project.game.objects.collidables.block.Block;

/**
 * {@link BlocksFromSymbolFactory} is a factory that assists in generating spacers and blocks
 *  in the game environment.
 */
public class BlocksFromSymbolFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Construct a new {@link BlocksFromSymbolFactory}.
     * @param spacerWidths : a mapping between spacer symbols to widths
     * @param blockCreators : a mapping between block symbols to {@link BlockCreator} objects
     */
    public BlocksFromSymbolFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Verify whether a given symbol is a spacer symbol.
     * @param s : the given symbol
     * @return true if it is. otherwise false.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * Verify whether a given symbol is a block symbol.
     * @param s : the symbol
     * @return true if it is. false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }


    /**
     * Generate a block for a given symbol at a given location.
     * @param s : the symbol
     * @param x : the x position of the block
     * @param y : the y position of the block
     * @return a generated block
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * Get a width of a spacer with a given symbol.
     * @param s : the symbol
     * @return the width of the spacer
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
