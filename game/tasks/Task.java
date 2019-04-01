package project.game.tasks;

/**
 * {@link Task} is something that can be run and return a value afterwards.
 *
 * @param <T> : the type of the return value
 */
public interface Task<T> {

    /**
     * Run this task.
     * @return the result of this task
     */
    T run();

}
