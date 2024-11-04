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
     * @return the origin of the Oval (the first mouse click)
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin of the Oval (the first mouse click).
     * @param origin
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
     * Sets the top left point of the Oval.
     * @param topLeft
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
     * Sets the width of the Oval.
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the height of the Oval
     */
    public double getHeight() { return this.height; }

    /**
     * Sets the height of the Oval.
     * @param height
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
     * Sets the color of the Circle.
     * @param color
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
     * Check if the Eraser is overlapping the Oval.
     * If it is, then the Eraser will erase the Oval.
     * @param eraser the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Oval, False otherwise
     */
    @Override
    public boolean overlaps(Eraser eraser) {
        double h = this.topLeft.x+this.width/2.0;
        double k = this.topLeft.y+this.height/2.0;
        double a, b;
        if (this.height > this.width){
            a = this.height/2.0;
            b = this.width/2.0;
        }
        else{
            a = this.width/2.0;
            b = this.height/2.0;
        }
        double leftX = eraser.getCentre().x-(eraser.getDimension()/2.0);
        double rightX = eraser.getCentre().x+(eraser.getDimension()/2.0);
        double topY = eraser.getCentre().y-(eraser.getDimension()/2.0);
        double bottomY = eraser.getCentre().y+(eraser.getDimension()/2.0);
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(leftX, topY));
        allPoints.add(new Point(leftX, bottomY));
        allPoints.add(new Point(rightX, topY));
        allPoints.add(new Point(rightX, bottomY));
        allPoints.add(eraser.getCentre());
        for (Point point : allPoints) {
            if (Math.pow((point.x-h)/a, 2) + Math.pow((point.y-k)/b, 2) <= 1){
                return true;
            }
        }
        return false;
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
