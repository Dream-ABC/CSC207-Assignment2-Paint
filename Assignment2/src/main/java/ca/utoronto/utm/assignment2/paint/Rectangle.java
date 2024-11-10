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
     * The fill style and line thickness are determined by the provided parameters.
     * @param fillStyle "Solid"/"Outline"
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Rectangle(String fillStyle, double lineThickness) {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * Returns the top left point of Rectangle.
     * @return the top left point of Rectangle
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * Sets the top left point of Rectangle.
     * @param topLeft top left corner of Rectangle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Returns the width of Rectangle.
     * @return the width of Rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Sets the width of Rectangle.
     * @param width width of Rectangle
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the height of Rectangle.
     * @return the height of Rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the height of Rectangle.
     * @param height height of Rectangle
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the origin point of Rectangle (first mouse click).
     * @return the origin of Rectangle
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin point of Rectangle (first mouse click).
     * @param origin origin of Rectangle
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the color of Rectangle.
     * @return the color of Rectangle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Rectangle.
     * @param color color of Rectangle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the Tool is overlapping the Rectangle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        if (this.fillStyle.equals("Outline")) {
            return overlapsOutline(tool);
        }
        return overlapsSolid(tool);
    }

    /**
     * Checks if the Tool is overlapping a solid Rectangle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
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

    /**
     * Checks if any corners/centre of the Tool is overlapping the Rectangle.
     *
     * @param tool the tool instance which is being checked for overlaps
     * @return True if any point of the tool is overlapping, False otherwise
     */
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

    /**
     * Checks if the Tool is overlapping an outlined Rectangle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
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

    /**
     * Shifts the top left point of Rectangle by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
    }

    /**
     * Creates a copy of the Rectangle instance.
     *
     * @return a copy of the Rectangle instance
     */
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
     * Displays the Rectangle with user-created color, size, fill style, and line thickness.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the Rectangle
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
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
     * @param data an array of strings containing the following information in order:
     *             <p>data[0] - x-coordinate of the top-left point</p>
     *             <p>data[1] - y-coordinate of the top-left point</p>
     *             <p>data[2] - width of the Rectangle</p>
     *             <p>data[3] - height of the Rectangle</p>
     *             <p>data[4] - x-coordinate of the origin point</p>
     *             <p>data[5] - y-coordinate of the origin point</p>
     *             <p>data[6] - color of the Rectangle in web format</p>
     *             <p>data[7] - fill style of the Rectangle</p>
     *             <p>data[8] - line thickness of the Rectangle</p>
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
     * Returns a string representation of the Rectangle object, including its position, color,
     * and style attributes.
     *
     * @return a string representation of the Rectangle
     */
    public String toString() {
        return "Rectangle{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.width + "," + this.height + ","
                + this.origin.x + "," + this.origin.y + ","
                + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
