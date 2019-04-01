package project.game.objects.collidables.block;

import biuoop.DrawSurface;
import project.geometry.Point;

/**
 * {@link ChangingBackground} is a background that changes according to the hitpoints.
 */
public class ChangingBackground implements Background {

    // a default background
    private Background defaultBackground;

    // a background which will first be checked
    private Background firstPriorityBackground;

    /**
     * Construct a new changing background.
     * @param firstPriority : a background which should be checked for match first
     */
    public ChangingBackground(Background firstPriority) {
        this.firstPriorityBackground = firstPriority;
    }

    /**
     * Add a default mapping for this changing background.
     * @param bg : to which background
     */
    public void setDefaultMapping(Background bg) {
        this.defaultBackground = bg;
    }

    /**
     * Adds a background to the collection of backgrounds.
     * @param hits : the amount of hits to initiate this background at
     * @param bg : the background
     */
    public void addMapping(int hits, Background bg) {
        this.firstPriorityBackground = new Background() {

            private Background secondPriority = ChangingBackground.this.firstPriorityBackground;
            private int amountOfHits = hits;

            @Override
            public Background getBackground(int hits) {
                if (amountOfHits == hits) {
                    return bg;
                } else {
                    return secondPriority;
                }
            }

            @Override
            public void draw(Point p, int width, int height, int hitPoints, DrawSurface surface) {
                getBackground(hits).draw(p, width, height, hitPoints, surface);
            }
        };
    }

    @Override
    public void draw(Point p, int width, int height, int hitPoints, DrawSurface surface) {
        // draw the background
        getBackground(hitPoints).draw(p, width, height, hitPoints, surface);
    }

    @Override
    public Background getBackground(int hits) {
        Background bg = null;

        if (this.firstPriorityBackground != null) {
            bg = this.firstPriorityBackground.getBackground(hits);
        }

        if (bg == null) {
            bg = this.defaultBackground;
        }

        return bg;
    }

}
