package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class to represent drawing rectangles.
 * Rectangle implements the Shape interface.
 */
public class Rectangle implements Shape {
    private Point topLeft;
    private double width;
    private double height;
    private Point origin;
    private Color color;

    /**
     * Constructs a default black rectangle with a width and height of 0.
     */
    public Rectangle() {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
    }

    /**
     * @return the top left point of the Rectangle
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * @param topLeft top left corner of Rectangle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the width of the Rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @param width width of Rectangle
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the height of the Rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @param height height of Rectangle
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the origin of the Rectangle (first mouse click)
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * @param origin origin of Rectangle (first mouse click)
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * @return the color of the Rectangle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Rectangle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the stroke thickness of the Rectangle
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Rectangle' as a string
     */
    @Override
    public String getShape() {
        return "Rectangle";
    }

    /**
     * Checks if the Eraser is overlapping the Rectangle.
     * If it is, then the Eraser will erase the Rectangle.
     * @param eraser the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Rectangle, False otherwise
     */
    @Override
    public boolean overlaps(Eraser eraser) {
        double rectLeft = this.topLeft.x;
        double rectRight = this.topLeft.x + this.width;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.height;

        return eraser.getRightX() >= rectLeft &&
                eraser.getLeftX() <= rectRight &&
                eraser.getBottomY() >= rectTop &&
                eraser.getTopY() <= rectBottom;
    }

    /**
     * Displays the Rectangle with user-created color and size.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        if (fillStyle.equals("Solid")){
            g2d.setFill(this.color);
            g2d.fillRect(this.topLeft.x, this.topLeft.y,
                    this.width, this.height);
        }
        else if (fillStyle.equals("Outline")){
            g2d.setStroke(this.color);
            g2d.strokeRect(this.topLeft.x, this.topLeft.y,
                    this.width, this.height);
        }
    }
}
