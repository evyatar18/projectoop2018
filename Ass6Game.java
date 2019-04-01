package project;


import project.game.ArkanoidGame;

/**
 * {@link Ass6Game} is the game for the 6th assignment.
 */
public class Ass6Game {

    private static final String DEFAULT_LEVEL_SETS = "level_sets.txt";

    /**
     * The main method.
     * @param args : the arguments
     */
    public static void main(String[] args) {
        // if no args were supplied, use the default level sets file
        if (args.length == 0) {
            args = new String[] {DEFAULT_LEVEL_SETS};
        }

        Ass6Game game = new Ass6Game(args);
        game.run();
    }

    private final ArkanoidGame game;

    /**
     * Construct a new game.
     * @param fromArgs : the arguments from main to construct from
     */
    public Ass6Game(String[] fromArgs) {
        String fileName = fromArgs[0];
        this.game = new ArkanoidGame(fileName);
    }

    /**
     * Run this game.
     */
    public void run() {
        this.game.run();
    }
}
