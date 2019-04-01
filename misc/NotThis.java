package project.misc;

import java.util.function.Predicate;

/**
 * {@link NotThis} is a predicate that checks whether another object is equal to a known object.
 * @param <T> : for a given object
 */
public class NotThis<T> implements Predicate<T> {

    private final T instance;

    /**
     * Construct a new {@link NotThis} instance.
     * @param instance : an instance of an object that should be compared with
     */
    public NotThis(T instance) {
        this.instance = instance;
    }

    @Override
    public boolean test(T t) {
        return !t.equals(this.instance);
    }

}
