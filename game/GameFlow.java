package project.game;

import java.util.List;

import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import project.game.animation.Animation;
import project.game.animation.AnimationRunner;
import project.game.animation.EndScreen;
import project.game.animation.HighScoresAnimation;
import project.game.animation.KeyPressStoppableAnimation;
import project.game.levels.LevelInformation;
import project.game.scores.HighScoresTable;
import project.game.scores.ScoreInfo;
import project.misc.Counter;

/**
 * {@link GameFlow} is the flow of a game.
 */
public class GameFlow {

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private DialogManager dialogManager;

    /**
     * Construct a new game flow.
     * @param animationRunner : the animation runner for this game
     * @param keyboardSensor : the keyboard sensor
     * @param dialogManager : a dialog manager used to display dialogs
     */
    public GameFlow(AnimationRunner animationRunner, KeyboardSensor keyboardSensor, DialogManager dialogManager) {
        this.animationRunner = animationRunner;
        this.keyboardSensor = keyboardSensor;
        this.dialogManager = dialogManager;
    }

    private int startLives = 5;
    private HighScoresTable highScores;

    /**
     * Set the starting lives for this flow.
     * @param lives : the amount of lives
     */
    public void setStartingLives(int lives) {
        this.startLives = lives;
    }

    /**
     * Set a {@link HighScoresTable} for this flow.
     * @param hs : the {@link HighScoresTable}
     */
    public void setHighScores(HighScoresTable hs) {
        this.highScores = hs;
    }

    /**
     * Run a list of given levels.
     * @param levels : the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        // initialize lives
        Counter lives = new Counter(this.startLives);

        // initialize score
        Counter score = new Counter();

        // run each level
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, lives, score);

            // init the level
            level.initialize();

            // while there are more blocks waiting for removal and the player is alive
            while (level.moreBlocksLeft() && lives.getValue() >= 0) {
                level.playOneTurn();
            }

            // if there are no more lives left
            if (lives.getValue() < 0) {
                break;
            }

            // add 100 points for finishing level
            score.increase(100);
        }

        // run results screen until space key is pressed
        Animation endScreen = new EndScreen(lives.getValue() >= 0, score);
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                KeyboardSensor.SPACE_KEY, endScreen));

        // try to add the score of the player as a highscore
        executeHighscores(score.getValue());

        // show all high scores
        Animation highscoresScreen = new HighScoresAnimation(this.highScores);
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                KeyboardSensor.SPACE_KEY, highscoresScreen));
    }

    /**
     * Try adding the score of the player to the high scores.
     * @param score : the score of the player
     */
    private void executeHighscores(int score) {
        // if the score won't be added to the highscores table
        if (this.highScores.getRank(score) > this.highScores.size()) {
            return;
        }

        // display dialog and get name
        String name = this.dialogManager.showQuestionDialog("Name", "What is your name?", "");

        // add as high score
        this.highScores.add(new ScoreInfo(name, score));
    }

}
