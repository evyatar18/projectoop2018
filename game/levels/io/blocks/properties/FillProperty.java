package project.game.levels.io.blocks.properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.game.levels.io.BackgroundParser;
import project.game.objects.collidables.block.Background;
import project.game.objects.collidables.block.Block;
import project.game.objects.collidables.block.ChangingBackground;
import project.misc.Utils;

/**
 * {@link FillProperty} recognizes and applies fill values.
 */
public class FillProperty implements BlockProperty {

    private static final Pattern FILL_PATTERN = Pattern.compile("fill(-(\\d+))?");

    private BackgroundParser parser = new BackgroundParser();

    @Override
    public boolean is(String name) {
        return FILL_PATTERN.matcher(name).matches();
    }

    @Override
    public void apply(String name, String value, Block b) {
        Background bg = this.parser.getBackground(value);

        if (bg == null) {
            return;
        }

        ChangingBackground wrappedPreviousBackground = new ChangingBackground(b.getBackground());

        if (name.equals("fill")) {
            // if we have fill property, we want to apply it only as a default
            wrappedPreviousBackground.setDefaultMapping(bg);
        } else {
            Matcher m = FILL_PATTERN.matcher(name);

            if (m.find()) {
                Integer i = Utils.tryParseInt(m.group(2));

                // we know the amount of hits(i) and we know the background(bg)
                wrappedPreviousBackground.addMapping(i, bg);
            }
        }

        b.setBackground(wrappedPreviousBackground);
    }


}
