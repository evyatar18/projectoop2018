package project.game.animation.menus;

import project.game.animation.Animation;

/**
 * {@link Menu} is a menu that displays on the screen as an animation.
 * Then it returns the chosen option.
 *
 * @param <T> : type of the chosen option
 */
public interface Menu<T> extends Animation {

    /**
     * Inserts a selection into the menu.
     * @param key : the key which will be associated with this choice
     * @param message : the message this choice will display
     * @param returnValue : the value this choice holds
     */
    void addSelection(String key, String message, T returnValue);

    /**
     * Get the result of this menu.
     * @return the selected result or null if none was selected yet
     */
    T getStatus();

    /**
     * Add a sub-menu to this menu.
     * @param key : the key used in order to access the sub-menu
     * @param message : the message the sub-menu displays
     * @param subMenu : the actual sub-menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * Reset the choices of this menu.
     */
    void reset();
}
