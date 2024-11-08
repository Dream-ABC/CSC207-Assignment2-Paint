package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    private final String fillStyle;
    private double lineThickness;

    /**
     * Constructs a default black oval with a width and height of 0.
     */
    public Oval(String fillStyle, double lineThickness) {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
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
     *
     */
    @Override
    public void setLineThickness(double lineThickness) {
        this.lineThickness = lineThickness;
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
        double ovalCenterX = topLeft.x + (width / 2.0);
        double ovalCenterY = topLeft.y + (height / 2.0);
        double radiusX, radiusY;
        if (this.fillStyle.equals("Outline")){
            radiusX = (width / 2.0) + (this.lineThickness/2.0);
            radiusY = (height / 2.0) + (this.lineThickness/2.0);
        }
        else{
            radiusX = (width / 2.0) + (this.lineThickness/2.0);
            radiusY = (height / 2.0);
        }

        double rectLeft = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double rectRight = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double rectTop = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double rectBottom = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);

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
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        if (this.fillStyle.equals("Solid")){
            g2d.setFill(this.color);
            g2d.fillOval(this.topLeft.x, this.topLeft.y,
                    this.width, this.height);
        }
        else if (this.fillStyle.equals("Outline")){
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokeOval(this.topLeft.x, this.topLeft.y,
                    this.width, this.height);
        }
    }
}
