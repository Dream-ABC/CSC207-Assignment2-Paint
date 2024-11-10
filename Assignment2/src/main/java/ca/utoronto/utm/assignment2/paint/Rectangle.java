package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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
    private String fillStyle;
    private double lineThickness;

    /**
     * Constructs a default black rectangle with a width and height of 0.
     */
    public Rectangle(String fillStyle, double lineThickness) {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
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
     *
     */
    @Override
    public void setLineThickness(double lineThickness) {
        this.lineThickness = lineThickness;
    }

    /**
     * Checks if the Tool is overlapping the Rectangle.
     * If it is, then the Eraser will erase the Rectangle.
     *
     * @param tool the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Rectangle, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        if (this.fillStyle.equals("Outline")) {
            return overlapsOutline(tool);
        }
        return overlapsSolid(tool);
    }

    private boolean overlapsSolid(Tool tool) {
        double eraserLeft = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double eraserRight = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double eraserTop = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double eraserBottom = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);
        double rectLeft = this.topLeft.x;
        double rectRight = this.topLeft.x + this.width;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.height;

        return eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom;
    }

    private boolean overlapsInsideAtPoint(Tool tool) {
        double rectLeft = this.topLeft.x + (this.lineThickness / 2.0);
        double rectRight = this.topLeft.x + this.width - (this.lineThickness / 2.0);
        double rectTop = this.topLeft.y + (this.lineThickness / 2.0);
        double rectBottom = this.topLeft.y + this.height - (this.lineThickness / 2.0);

        double leftX = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double rightX = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double topY = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double bottomY = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(leftX, topY));
        allPoints.add(new Point(leftX, bottomY));
        allPoints.add(new Point(rightX, topY));
        allPoints.add(new Point(rightX, bottomY));
        allPoints.add(tool.getTopLeft());

        for (Point p : allPoints) {
            if (!(rectLeft <= p.x && p.x <= rectRight && rectTop <= p.y && p.y <= rectBottom)) {
                return false;
            }
        }
        return true;
    }

    private boolean overlapsOutline(Tool tool) {
        double eraserLeft = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double eraserRight = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double eraserTop = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double eraserBottom = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);

        double rectLeft = this.topLeft.x - (this.lineThickness / 2.0);
        double rectRight = this.topLeft.x + this.width + (this.lineThickness / 2.0);
        double rectTop = this.topLeft.y - (this.lineThickness / 2.0);
        double rectBottom = this.topLeft.y + this.height + (this.lineThickness / 2.0);

        return (eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom)
                && !overlapsInsideAtPoint(tool);
    }

    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
    }

    public Rectangle copy(){
        Rectangle r = new Rectangle(fillStyle, lineThickness);
        r.setColor(color);
        r.setHeight(height);
        r.setWidth(width);
        r.setOrigin(origin.copy());
        r.setTopLeft(topLeft.copy());
        return r;
    }

    /**
     * Displays the Rectangle with user-created color and size.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        if (this.fillStyle.equals("Solid")) {
            g2d.setFill(this.color);
            g2d.fillRect(this.topLeft.x, this.topLeft.y,
                    this.width, this.height);
        } else if (this.fillStyle.equals("Outline")) {
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokeRect(this.topLeft.x, this.topLeft.y,
                    this.width, this.height);
        }
    }

    /**
     * Sets the properties of the Rectangle based on the provided data array.
     *
     * @param data an array of strings containing the properties for the Rectangle.
     *             The array should contain the following elements in order:
     *             data[0] - x-coordinate of the top-left corner of the rectangle
     *             data[1] - y-coordinate of the top-left corner of the rectangle
     *             data[2] - width of the rectangle
     *             data[3] - height of the rectangle
     *             data[4] - x-coordinate of the origin point
     *             data[5] - y-coordinate of the origin point
     *             data[6] - color of the rectangle
     *             data[7] - fill style of the rectangle
     *             data[8] - line thickness of the rectangle
     */
    @Override
    public void setShape(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.width = Double.parseDouble(data[2]);
        this.height = Double.parseDouble(data[3]);
        this.origin = new Point(Double.parseDouble(data[4]), Double.parseDouble(data[5]));
        this.color = Color.web(data[6]);
        this.fillStyle = data[7];
        this.lineThickness = Double.parseDouble(data[8]);
    }

    /**
     * Returns a string representation of a rectangle.
     *
     * @return a string representation of the rectangle
     */
    public String toString() {
        return "Rectangle{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.width + "," + this.height + ","
                + this.origin.x + "," + this.origin.y + ","
                + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
