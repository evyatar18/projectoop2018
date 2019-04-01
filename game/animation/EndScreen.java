package project.game.animation;

import project.misc.Counter;

/**
 * {@link EndScreen} is an end screen of a game.
 */
public class EndScreen extends BaseTextScreen {

    private static final String WIN_MESSAGE = "You Win!";
    private static final String LOSE_MESSAGE = "Game Over.";

    private static final String SCORE_MESSAGE_FORMAT = "Your score is %s.";

    private final String message;

    /**
     * Construct a new end screen.
     * @param won : whether the player won or lost
     * @param score : the score the player got
     */
    public EndScreen(boolean won, Counter score) {
        /* initialize screen message */
        String messagePrefix = won ? WIN_MESSAGE : LOSE_MESSAGE;
        String scoreMessage = String.format(SCORE_MESSAGE_FORMAT, score.getValue());

        // concat the messages
        this.message = messagePrefix + " " + scoreMessage;
    }

    @Override
    protected String getText() {
        return this.message;
    }

    @Override
    public boolean shouldStop() {
        return false;
    }

}
