package project.misc;
import java.awt.Color;
import java.awt.Image;

import biuoop.DrawSurface;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Rectangle;

/**
 * Utilities for drawing objects on {@link DrawSurface}.
 */
public class DrawUtils {

    /**
     *  dimension of a drawn point.
     */
    public static final int POINT_RADIUS = 3;

    private DrawSurface surface;

    /**
     * Construct a new DrawUtils instance for a given surface.
     * @param surface : the draw surface to draw on
     */
    public DrawUtils(DrawSurface surface) {
        this.surface = surface;
    }


    /**
     * Draws a point.
     * @param pt : the point to draw
     * @param color : the color of the point
     */
    public void drawPoint(Point pt, Color color) {
        this.surface.setColor(color);
        this.surface.fillCircle(pt.xFloored(), pt.yFloored(), POINT_RADIUS);
    }

    /**
     * Fills a circle.
     * @param center : the center point
     * @param radius : the circle radius
     * @param color : the color of the circle
     */
    public void fillCircle(Point center, int radius, Color color) {
        this.surface.setColor(color);
        surface.fillCircle(center.xFloored(), center.yFloored(), radius);
    }

    /**
     * Draws a circle.
     * @param center : the center point
     * @param radius : the radius
     * @param color : the color of the circle
     */
    public void drawCircle(Point center, int radius, Color color) {
        this.surface.setColor(color);
        this.surface.drawCircle(center.xFloored(), center.yFloored(), radius);
    }

    /**
     * Draws a line.
     * @param line : the line to draw
     * @param color : the color of the line
     */
    public void drawLine(Line line, Color color) {
        this.surface.setColor(color);
        this.surface.drawLine(line.start().xFloored(), line.start().yFloored(),
                line.end().xFloored(), line.end().yFloored());
    }

    /**
     * Fills the background.
     * @param color : the color to fill with
     */
    public void fillBackground(Color color) {
        this.surface.setColor(color);
        this.surface.fillRectangle(0, 0, this.surface.getWidth(), this.surface.getHeight());
    }

    /**
     * Draws rectangle boundaries.
     * @param topLeft : the top left point of the rectangle
     * @param width : the width of the rectangle
     * @param height : the height of the rectangle
     * @param color : the color of the boundaries
     */
    public void drawRectangle(Point topLeft, int width, int height, Color color) {
        // x and y of top left point
        int x = topLeft.xFloored();
        int y = topLeft.yFloored();

        // draw
        this.surface.setColor(color);
        this.surface.drawRectangle(x, y, width, height);
    }

    /**
     * Draws rectangle boundaries.
     * @param rect : the rectangle to be drawn
     * @param color : the color of the boundaries
     */
    public void drawRectangle(Rectangle rect, Color color) {
        drawRectangle(rect.getUpperLeft(), rect.getWidth(), rect.getHeight(), color);
    }

    /**
     * Fills a rectangle.
     * @param topLeft : the top left point of the rectangle
     * @param width : the width of the rectangle
     * @param height : the height of the rectangle
     * @param color : the color of the rectangle
     */
    public void fillRectangle(Point topLeft, int width, int height, Color color) {
        // x and y of top left point
        int x = topLeft.xFloored();
        int y = topLeft.yFloored();

        // draw
        this.surface.setColor(color);
        this.surface.fillRectangle(x, y, width, height);
    }

    /**
     * Fills a rectangle.
     * @param rect : the rectangle to be drawn
     * @param color : the color of the rectangle
     */
    public void fillRectangle(Rectangle rect, Color color) {
        fillRectangle(rect.getUpperLeft(), rect.getWidth(), rect.getHeight(), color);
    }

    /**
     * Draw a text in a given location.
     * @param topLeft : the top left corner of the text
     * @param text : the text to be drawn
     * @param fontSize : the font size of the text
     * @param color : the color of the text
     */
    public void drawText(Point topLeft, String text, int fontSize, Color color) {
        this.surface.setColor(color);
        this.surface.drawText(topLeft.xFloored(), topLeft.yFloored(), text, fontSize);
    }

    /**
     * Draw text with the thought that its color might not match the background.
     * @param topLeft : the top left position of the text
     * @param text : the text
     * @param fontSize : the font size
     * @param color1 : the first color
     * @param color2 : the second color
     */
    public void drawTextSafe(Point topLeft, String text, int fontSize, Color color1, Color color2) {
        drawText(new Point(topLeft.getX() + 1, topLeft.getY() + 1), text, fontSize, Color.WHITE);
        drawText(topLeft, text, fontSize, color1);
    }

    public static final double SPACE_BOUNDARIES = 0.05;

    /**
     * Draw a text inside a rectangle.
     * @param rect : the rectangle
     * @param text : the text
     * @param fontSize : the font size
     * @param color : the color
     */
    public void drawTextInRectangle(Rectangle rect, String text, int fontSize, Color color) {
        // get the relevant center position
        Point topLeft = rect.center();

        // draw the text
        drawText(topLeft, text, fontSize, color);
    }

    /**
     * Draw an image at a given position.
     * @param p : the position
     * @param img : the image
     */
    public void drawImage(Point p, Image img) {
        this.surface.drawImage(p.xFloored(), p.yFloored(), img);
    }
}
