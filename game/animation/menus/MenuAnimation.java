package project.game.animation.menus;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import project.game.tasks.Task;
import project.geometry.Point;
import project.misc.DrawUtils;

/**
 * {@link MenuAnimation} is an implementation of the {@link Menu} interface.
 *
 * @param <T> : the return value of this menu
 */
public class MenuAnimation<T> implements Menu<T> {

    private final String menuName;
    private final KeyboardSensor keyboard;

    private List<Selection<T>> selections;
    private T choice;

    private Menu<T> enabledSubMenu;

    /**
     * Construct a new {@link MenuAnimation}.
     * @param menuName : the title of the menu
     * @param keyboard : a keyboard used to identify selections
     */
    public MenuAnimation(String menuName, KeyboardSensor keyboard) {
        this.selections = new LinkedList<>();
        this.menuName = menuName;
        this.keyboard = keyboard;

        this.enabledSubMenu = null;
    }

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        // if selected a sub menu
        if (this.enabledSubMenu != null) {
            this.enabledSubMenu.doOneFrame(surface, dt);

            if (this.choice == null) {
                this.choice = this.enabledSubMenu.getStatus();
            }

            return;
        }

        DrawUtils draw = new DrawUtils(surface);

        int x = 50;
        int y = 100;

        int fontSize = 20;

        // draw menu title
        draw.drawText(new Point(x, y), this.menuName, fontSize * 2, Color.BLACK);

        /* show each selection on the screen */
        for (Selection<T> sel : this.selections) {
            // if a selection was selected
            if (this.keyboard.isPressed(sel.getKey()) && this.choice == null) {
                // get its selection
                this.choice = sel.getValue();

                // and notify it that it was selected
                sel.notifySelected();
            }

            y += fontSize + 2;
            draw.drawText(new Point(x, y), sel.getText(), fontSize, Color.BLACK);
        }
    }

    @Override
    public boolean shouldStop() {
        // if found a selection
        return this.choice != null;
    }

    @Override
    public void addSelection(String key, String message, T returnValue) {
        this.selections.add(new Selection<T>(key, message, returnValue, null));
    }

    @Override
    public T getStatus() {
        return this.choice;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        Task<Void> onSelection = new Task<Void>() {

            @Override
            public Void run() {
                // mark this sub menu as selected
                MenuAnimation.this.enabledSubMenu = subMenu;
                return null;
            }
        };

        this.selections.add(new Selection<T>(key, message, null, onSelection));
    }

    @Override
    public void reset() {
        this.choice = null;

        if (this.enabledSubMenu != null) {
            this.enabledSubMenu.reset();
            this.enabledSubMenu = null;
        }
    }

}
