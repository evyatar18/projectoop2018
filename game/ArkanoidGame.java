package project.game;

import java.io.File;
import java.io.IOException;

import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import project.game.animation.AnimationRunner;
import project.game.animation.menus.MainMenu;
import project.game.animation.menus.Menu;
import project.game.scores.HighScoresTable;
import project.game.tasks.Task;
import project.misc.Toggle;

/**
 * The {@link ArkanoidGame}.
 */
public class ArkanoidGame {

    public static final File HIGHSCORES_FILE = new File("highscores");
    public static final int HIGHSCORES_AMOUNT = 10;

    private final GUI gui;
    private final String levelsetPath;

    /**
     * Construct a new {@link ArkanoidGame} with given levels.
     * @param levelsetPath : a path to the levelset file positioned inside the class-path
     */
    public ArkanoidGame(String levelsetPath) {
        this.gui = new GUI("Arkanoid", GameLevel.WIDTH, GameLevel.HEIGHT);
        this.levelsetPath = levelsetPath;
    }



    /**
     * Get the highscores table for all the games.
     * @return the highscores table.
     */
    private HighScoresTable highScores() {
        HighScoresTable hs = HighScoresTable.loadFromFile(HIGHSCORES_FILE);

        // if there's no table
        if (hs.size() != HIGHSCORES_AMOUNT) {
            /* initialize table file */
            hs = new HighScoresTable(HIGHSCORES_AMOUNT);

            try {
                hs.save(HIGHSCORES_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return hs;
    }

    /**
     * Save high scores to a file.
     * @param hs : the high scores
     */
    private void saveHighscores(HighScoresTable hs) {
        try {
            hs.save(HIGHSCORES_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run the game.
     */
    public void run() {
        // initialize animation runner, keyboard sensor and high scores
        AnimationRunner runner = new AnimationRunner(this.gui);
        KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        DialogManager dialogs = this.gui.getDialogManager();
        HighScoresTable highScores = highScores();

        // initialize a toggle for the running game
        Toggle runGame = new Toggle(true);

        Menu<Task<Void>> menu = new MainMenu(runner, keyboard, dialogs, highScores,
                runGame, this.levelsetPath, new Task<Void>() {

            @Override
            public Void run() {
                saveHighscores(highScores);
                return null;
            }
        });

        // while the game is running
        while (runGame.state()) {
            // reset the menu
            menu.reset();

            // show the menu
            runner.run(menu);

            // get the user choice
            Task<Void> task = menu.getStatus();

            // perform according to the choice
            task.run();
        }

        // close the gui
        this.gui.close();
    }


}
