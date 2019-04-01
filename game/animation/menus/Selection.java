package project.game.animation.menus;

import project.game.tasks.Task;

/**
 * Selection is a selection that can be made by the user.
 *
 * @param <T> : type of the selection
 */
public class Selection<T> {

    private final String key;
    private final String message;
    private final T value;
    private Task<Void> onSelection;

    /**
     * Construct a new Selection instance.
     * @param key : the key used to select this selection
     * @param message : the message displayed with this selection
     * @param value : the value of this selection
     * @param onSelection : a task to run when this selection has been selected
     */
    public Selection(String key, String message, T value, Task<Void> onSelection) {
        this.key = key;
        this.message = message;
        this.value = value;
        this.onSelection = onSelection;
    }

    /**
     * Get the key associated with this selection.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the message of this selection.
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Get the return value of this selection.
     * @return the value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Notify this selection that it was selected.
     */
    public void notifySelected() {
        if (this.onSelection != null) {
            this.onSelection.run();
        }
    }

    /**
     * Get a formatted text displayed for this selection.
     * @return formatted text
     */
    public String getText() {
        String format = "%s: %s";
        return String.format(format, getKey(), getMessage());
    }
}
