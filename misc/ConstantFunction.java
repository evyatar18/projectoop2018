package project.misc;

import java.util.function.Function;

/**
 * {@link ConstantFunction} is a constant function implementation.
 * @param <X> : the x type of values of the function
 * @param <Y> : the y type of values of the function
 */
public class ConstantFunction<X, Y> implements Function<X, Y> {

    private final Y value;

    /**
     * Construct a new constant function.
     * @param value : the constant value
     */
    public ConstantFunction(Y value) {
        this.value = value;
    }

    @Override
    public Y apply(X t) {
        return this.value;
    }

}
