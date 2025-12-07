package game;

/**
 * The Counter class keeps track of a count value.
 * It allows increasing and decreasing the count by specified amounts.
 */
public class Counter {
    private int count;

    /**
     * Constructor.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Increases the count by a specified number.
     * @param number the number to add
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decreases the count by a specified number.
     * @param number the number to subtract
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Returns the current count.
     * @return the current count
     */

    public int getValue() {
        return this.count;
    }
}
