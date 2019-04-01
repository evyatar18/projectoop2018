package project.game.scores;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import project.misc.MaxCapacityList;

/**
 * {@link HighScoresTable} is a table that holds high scores for this game.
 */
public class HighScoresTable {

    private final MaxCapacityList<ScoreInfo> scores;
    private final int size;

    /**
     * Construct a new high scores table.
     * @param size : the size of the table
     */
    public HighScoresTable(int size) {
        this.scores = new MaxCapacityList<>(size + 1);
        this.size = size;
    }

    /**
     * Add a given score into this list.
     * @param score : the score
     */
    public void add(ScoreInfo score) {
        // get the position of the new score on the list
        int index = getRank(score.getScore()) - 1;

        // if can be added
        if (index < size()) {
            // but there is no space left on the list
            if (!this.scores.hasSpace()) {
                // remove last score in the list
                this.scores.remove(size() - 1);
            }

            // add the score at the found position
            this.scores.add(index, score);
        }
    }

    /**
     * Get the size of this table.
     * @return the size
     */
    public int size() {
        return this.size;
    }

    /**
     * Get a list of high scores in this table.
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Get the position where the given score will be added if it were added into this table.
     * @param score : the score
     * @return rank is 1 if the score will be the highest on the list
     *         rank is size if the score is the lowest
     *         rank higher than size means the score is too low to be added into the list
     */
    public int getRank(int score) {
        // generate pseudo score info for comparisons
        ScoreInfo pseudo = new ScoreInfo(score);

        int i;

        // try finding position in the list
        for (i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i).compareTo(pseudo) < 0) {
                break;
            }
        }

        return i + 1;
    }

    /**
     * Clear the scores in this table.
     */
    public void clear() {
        this.scores.clear();
    }

    /**
     * Loads scores from a given file into this table.
     * @param filename : the file to load from
     * @throws IOException : if an error occurred in the load operation
     */
    public void load(File filename) throws IOException {
        // first clear this table
        clear();

        // get input stream
        InputStream in = new FileInputStream(filename);
        ObjectInputStream objectReader = null;

        try {
            // get objects reader
            objectReader = new ObjectInputStream(in);

            // skip reading the size
            objectReader.readInt();

            // a variable that holds how much more we can read
            int maximalLeftSpace = size();

            // read all scores from file(at most 'size' scores)
            while (maximalLeftSpace-- > 0) {
                try {
                    try {
                        add((ScoreInfo) objectReader.readObject());
                    } catch (EOFException e) {
                        // if reached end of file do nothing
                        return;
                    }

                } catch (ClassNotFoundException e) { e.printStackTrace(); }
            }
        } finally {
            if (objectReader != null) {
                objectReader.close();
            }

            in.close();
        }
    }

    /**
     * Save this table data into a file.
     * @param filename : the file to save into
     * @throws IOException : if an error occurs while saving
     */
    public void save(File filename) throws IOException {
        // get output stream
        OutputStream os = new FileOutputStream(filename);

        // get object outputstream
        ObjectOutputStream objOS = new ObjectOutputStream(os);

        try {
            // write the file size into the file
            objOS.writeInt(size());

            // get an iterator for the scores
            Iterator<ScoreInfo> scoreIterator = getHighScores().iterator();

            // write all scores into the file
            while (scoreIterator.hasNext()) {
                objOS.writeObject(scoreIterator.next());
            }
        } finally {
            // close output stream
            objOS.close();
        }
    }

    /**
     * Loads a {@link HighScoresTable} from a given file.
     * @param filename : the file to read from
     * @return a {@link HighScoresTable} instance which holds the data from the file
     */
    public static HighScoresTable loadFromFile(File filename) {
        InputStream is = null;

        try {
            // construct input stream
            is = new FileInputStream(filename);

            // get objectinputstream for the file
            ObjectInputStream ois = new ObjectInputStream(is);

            // get the size of the table
            int size = ois.readInt();

            // close streams
            ois.close();
            is.close();

            // construct a table from a size read
            HighScoresTable table = new HighScoresTable(size);

            // load data into the table
            table.load(filename);

            return table;
        } catch (Exception e) {
            return new HighScoresTable(0);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    return new HighScoresTable(0);
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.scores.toString();
    }
}
