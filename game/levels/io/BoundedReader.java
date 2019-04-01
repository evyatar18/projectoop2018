package project.game.levels.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link BoundedReader} reads text between two lines.
 */
public class BoundedReader {

    private final String firstLine;
    private final String lastLine;

    private boolean readFirst = false;
    private boolean finished = false;

    /**
     * Construct a new bounded reader.
     * @param firstLine : the first line marking the beginning of the text
     * @param lastLine : the last line marking the end of the text
     */
    public BoundedReader(String firstLine, String lastLine) {
        this.firstLine = firstLine;
        this.lastLine = lastLine;
    }


    /**
     * Query whether a given line is the first line of this reader.
     * @param str : the line
     * @return true if it is, false otherwise.
     */
    public boolean isFirstLine(String str) {
        return this.firstLine.equals(str);
    }

    /**
     * Set the flag telling this reader it has read the first line.
     * @param b : the value
     */
    public void setReadFirstLine(boolean b) {
        this.readFirst = b;
    }

    /**
     * Read a line from a given {@link BufferedReader}.
     * @param reader : the given {@link BufferedReader}
     * @return a line or null if arrived at the end
     * @throws IOException : if an error occurred while reading
     */
    public String readLine(BufferedReader reader) throws IOException {
        // if done reading
        if (this.finished) {
            return null;
        }

        // make sure to arrive and read the first bounding line
        while (!this.readFirst) {
            String ln = reader.readLine();

            if (ln == null) {
                return null;
            } else {
                // if arrived at first line, exit loop
                if (isFirstLine(ln)) {
                    this.readFirst = true;
                    break;
                }
            }
        }

        // read the current line
        String ln = reader.readLine();

        if (ln.equals(this.lastLine)) {
            this.finished = true;
            return null;
        } else {
            return ln;
        }
    }

    /**
     * Read all lines in the attached reader.
     * @param reader : the attached {@link BufferedReader}
     * @return a list of all the lines
     * @throws IOException : if an exception occurred while reading
     */
    public List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();

        String line;

        while ((line = readLine(reader)) != null) {
            lines.add(line);
        }

        return lines;
    }


}
