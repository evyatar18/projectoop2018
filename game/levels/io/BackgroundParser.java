package project.game.levels.io;

import java.awt.Color;
import java.awt.Image;

import project.game.objects.collidables.block.Background;
import project.game.objects.collidables.block.OneBackground;

/**
 * {@link BackgroundParser} parses {@link Background} objects from given string.
 */
public class BackgroundParser {

    private ColorsParser colorsParser = new ColorsParser();
    private ImageParser imageParser = new ImageParser();

    /**
     * Get a {@link Background} instance from a given string.
     * @param value : the string
     * @return a {@link Background} instance or null if the string isn't good
     */
    public Background getBackground(String value) {
        OneBackground bg = null;

        Color c = this.colorsParser.colorFromString(value);
        Image img = this.imageParser.parseImage(value);

        if (c != null) {
            bg = new OneBackground(c);
        } else {
            bg = new OneBackground(img);
        }

        return bg;
    }
}
