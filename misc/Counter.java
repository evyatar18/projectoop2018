package project.misc;

/**
 * {@link Counter} is a simple class used to count things.
 */
public class Counter {

    private int amount;

    /**
     * Construct a new counter which is initialized to 0.
     */
    public Counter() {
        this(0);
    }

    /**
     * Construct a new counter with a given amount.
     * @param start : the starting amount
     */
    public Counter(int start) {
        this.amount = start;
    }

    /**
     * Increase this counter.
     * @param number : the number to increase by
     */
    public void increase(int number) {
        this.amount = this.amount + number;
    }

    /**
     * Decrease this counter.
     * @param number : the number to decrease by
     */
    public void decrease(int number) {
        this.amount = this.amount - number;
    }

    /**
     * Get the value of this counter.
     * @return an integer value
     */
    public int getValue() {
        return this.amount;
    }

    /**
     * Reset this counter to 0.
     */
    public void reset() {
        this.amount = 0;
    }

    @Override
    public String toString() {
        return Integer.toString(this.amount);
    }
}
