package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * A class to represent drawing ovals.
 * Oval implements the Shape interface.
 */
public class Oval implements Shape {
    private Point origin;
    private Point topLeft;
    private double width;
    private double height;
    private Color color;

    /**
     * Constructs a default black oval with a width and height of 0.
     */
    public Oval() {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
    }

    /**
     * @return the origin of the Oval (first mouse click)
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * @param origin origin of Oval (first mouse click)
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * @return the top left point of the Oval
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * @param topLeft top left point of Oval
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the width of the Oval
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @param width width of Oval
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the height of the Oval
     */
    public double getHeight() { return this.height; }

    /**
     * @param height height of Oval
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * @return the color of the Circle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Circle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the stroke thickness of the Circle
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Oval' as a string
     */
    @Override
    public String getShape() {
        return "Oval";
    }

    /**
     * Checks if the Eraser is overlapping the Oval.
     * If it is, then the Eraser will erase the Oval.
     * @param tool the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Oval, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        double ovalCenterX = topLeft.x + (width / 2);
        double ovalCenterY = topLeft.y + (height / 2);
        double radiusX = width / 2;
        double radiusY = height / 2;

        double rectLeft = tool.getCentre().x - (tool.getDimensionX() / 2);
        double rectRight = tool.getCentre().x + (tool.getDimensionX() / 2);
        double rectTop = tool.getCentre().y - (tool.getDimensionY() / 2);
        double rectBottom = tool.getCentre().y + (tool.getDimensionY() / 2);

        double closestX = clamp(ovalCenterX, rectLeft, rectRight);
        double closestY = clamp(ovalCenterY, rectTop, rectBottom);

        double distanceX = (ovalCenterX - closestX) / radiusX;
        double distanceY = (ovalCenterY - closestY) / radiusY;
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        return distanceSquared <= 1;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Displays the Oval with user-created color and size.
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setFill(this.color);
        g2d.fillOval(this.topLeft.x, this.topLeft.y,
                this.width, this.height);
    }
}
