package project.misc;

/**
 * {@link Toggle} is a class that can be toggled on and off.
 */
public class Toggle {

    private boolean state;

    /**
     * Construct a new toggle with a default state.
     * @param state : the first state of the toggle
     */
    public Toggle(boolean state) {
        this.state = state;
    }

    /**
     * Change the state of this toggle.
     */
    public void toggleState() {
        this.state = !this.state;
    }

    /**
     * Get the state of this toggle.
     * @return the state
     */
    public boolean state() {
        return this.state;
    }

    /**
     * Set the state of this toggle.
     * @param newState : the new state
     */
    public void setState(boolean newState) {
        this.state = newState;
    }
}
