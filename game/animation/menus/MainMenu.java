package project.game.animation.menus;

import java.io.IOException;
import java.io.LineNumberReader;

import biuoop.DialogManager;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import project.game.GameFlow;
import project.game.animation.AnimationRunner;
import project.game.animation.HighScoresAnimation;
import project.game.animation.KeyPressStoppableAnimation;
import project.game.levels.io.levelsets.LevelSet;
import project.game.scores.HighScoresTable;
import project.game.tasks.Task;
import project.misc.Toggle;
import project.misc.Utils;

/**
 * {@link MainMenu} is the main menu.
 */
public class MainMenu implements Menu<Task<Void>> {

    private final Menu<Task<Void>> menuAnimation;

    /**
     * Generate a menu for the Arkanoid game.
     * @param runner : an animation runner
     * @param keyboard : a keyboard sensor
     * @param dialogs : a dialogs manager
     * @param highscores : a {@link HighScoresTable}
     * @param runGame : a toggle which is on one state and once switched the game stops running
     * @param levelsetPath : a path to the levelset file positioned inside the class-path
     * @param saveHighscores : a task that saves highscores
     */
    public MainMenu(AnimationRunner runner, KeyboardSensor keyboard, DialogManager dialogs,
            HighScoresTable highscores, Toggle runGame, String levelsetPath,
            Task<Void> saveHighscores) {

        // construct menu animation
        Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid Game", keyboard);

        // initialize game flow for future use
        GameFlow game = new GameFlow(runner, keyboard, dialogs);
        game.setStartingLives(7);
        game.setHighScores(highscores);

        // add the play game selection
        initPlayGame(levelsetPath, saveHighscores, game, keyboard, menu);

        // add the show high scores animation
        initHighscores(runner, keyboard, highscores, menu);

        // add the quit game option
        initQuit(runGame, menu);

        this.menuAnimation = menu;
    }


    /**
     * Initialize the play game choice.
     * @param levelsetPath : a path to the level sets
     * @param saveHighscores : a task which saves highscores
     * @param game : a gameflow instance
     * @param keyboard : a keyboard sensor
     * @param menu : a menu
     */
    private void initPlayGame(String levelsetPath, Task<Void> saveHighscores,
            GameFlow game, KeyboardSensor keyboard, Menu<Task<Void>> menu) {
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Select a Level Set", keyboard);
        menu.addSubMenu("s", "Play Game", subMenu);

        try {
            // construct a new linenumberreader
            LineNumberReader levelSetReader = new LineNumberReader(Utils.getResourceReader(levelsetPath));

            // while didn't arrive at the end of the level set file
            while (!Utils.isEOF(levelSetReader)) {
                // read a level set
                LevelSet set = LevelSet.fromReader(levelSetReader);

                // add it as a selection to the level sets
                subMenu.addSelection(set.getKey(), set.getDescription(), new Task<Void>() {

                    @Override
                    public Void run() {
                        // run the levels
                        game.runLevels(set.getLevels());

                        // and finally save the high scores
                        saveHighscores.run();
                        return null;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the highscores choice.
     * @param runner : animation runner
     * @param keyboard : keyboard sensor
     * @param highscores : highscores table
     * @param menu : a menu to add the choice into
     */
    private void initHighscores(AnimationRunner runner, KeyboardSensor keyboard,
            HighScoresTable highscores, Menu<Task<Void>> menu) {
        menu.addSelection("h", "Show High Scores", new Task<Void>() {

            @Override
            public Void run() {
                runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(highscores)));
                return null;
            }

        });
    }

    /**
     * Initialize the quit game choice.
     * @param runGame : the toggle of the game
     * @param menu : a menu
     */
    private void initQuit(Toggle runGame, Menu<Task<Void>> menu) {
        menu.addSelection("q", "Quit Game", new Task<Void>() {

            @Override
            public Void run() {
                // change the state of run game
                runGame.toggleState();
                return null;
            }

        });
    }

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        this.menuAnimation.doOneFrame(surface, dt);
    }

    @Override
    public boolean shouldStop() {
        return this.menuAnimation.shouldStop();
    }

    @Override
    public void addSelection(String key, String message, Task<Void> returnValue) {
        // this is a constant menu
        throw new UnsupportedOperationException("This operation is not supported by this menu.");
    }

    @Override
    public Task<Void> getStatus() {
        return this.menuAnimation.getStatus();
    }

    @Override
    public void addSubMenu(String key, String message, Menu<Task<Void>> subMenu) {
        throw new UnsupportedOperationException("This operation is not supported by this menu.");
    }


    @Override
    public void reset() {
        this.menuAnimation.reset();
    }


}
