package project.game.levels.io.levelsets;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

import project.game.levels.LevelInformation;
import project.game.levels.io.levels.LevelSpecificationReader;
import project.misc.Utils;

/**
 * {@link LevelSet} is a set of levels.
 */
public class LevelSet {

    private LevelSpecificationReader lsr = new LevelSpecificationReader();

    private String desc;
    private String key;
    private List<LevelInformation> lvls;

    /**
     * Set the levels file for this set.
     * @param levels : the levels
     */
    public void setLevelsFile(String levels) {
        this.lvls = this.lsr.fromReader(Utils.getResourceReader(levels));
    }

    /**
     * Get the levels.
     * @return the levels
     */
    public List<LevelInformation> getLevels() {
        return this.lvls;
    }

    /**
     * Get the description of the set.
     * @return the description
     */
    public String getDescription() {
        return this.desc;
    }

    /**
     * Set the description for this set.
     * @param s : the description
     */
    public void setDescription(String s) {
        this.desc = s;
    }

    /**
     * Get the key used to run this set.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Set the key used to run this set.
     * @param k : the key
     */
    public void setKey(String k) {
        this.key = k;
    }

    /**
     * Construct a new level set from a given {@link LineNumberReader}.
     * @param lnr : the reader
     * @return a new {@link LevelSet} instance
     */
    public static LevelSet fromReader(LineNumberReader lnr) {
        try {
            LevelSet ls = new LevelSet();

            // if this is not the first line
            if (lnr.getLineNumber() % 2 == 1) {
                lnr.readLine();
            }

            String[] splitLine = lnr.readLine().split(":");

            ls.setKey(splitLine[0]);
            ls.setDescription(splitLine[1]);

            ls.setLevelsFile(lnr.readLine());

            return ls;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
