package project.game.levels.io.levels.properties;

import project.game.levels.io.levels.ModifiableLevelInformation;

/**
 * {@link LevelProperty} is a property that levels can have.
 */
public interface LevelProperty {

    /**
     * Query whether a property is this property.
     * @param name : the name of the property
     * @return true if it is. false otherwise.
     */
    boolean is(String name);

    /**
     * Apply this property to a given level information and value.
     * @param name : the name of the property
     * @param value : the value of the property
     * @param mli : {@link ModifiableLevelInformation} instance to apply into
     */
    void apply(String name, String value, ModifiableLevelInformation mli);
}
