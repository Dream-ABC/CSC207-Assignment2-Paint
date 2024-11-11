package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * A class to represent drawing squares.
 * Square implements the Shape interface.
 */
public class Square implements Shape {
    private Point topLeft;
    private Point origin;
    private double size;
    private Color color;
    private String fillStyle;
    private double lineThickness;
    private Point originalPosition;

    /**
     * Constructs a default black square with a size of 0.
     * The fill style and line thickness are determined by the provided parameters.
     * @param fillStyle "Solid"/"Outline"
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Square(String fillStyle, double lineThickness) {
        this.size = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * Returns the top left point of Square.
     * @return the top left point of Square
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * Sets the top left point of Square.
     * @param topLeft top left corner of Square
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
        this.originalPosition = topLeft.copy();
    }

    /**
     * Returns the size of Square (width and height).
     * @return the size of Square
     */
    public double getSize() {
        return this.size;
    }

    /**
     * Sets the size of Square (width and height).
     * @param size size of Square
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Returns the origin point of Square (first mouse click).
     * @return the origin of Square
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin point of Square (first mouse click).
     * @param origin origin of Square
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the color of Square.
     * @return the color of Square
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Square.
     * @param color color of Square
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the Tool is overlapping the Square.
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
     * Checks if the Tool is overlapping a solid Square.
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
        double rectRight = this.topLeft.x + this.size;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.size;

        return eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom;
    }

    /**
     * Checks if any corners/centre of the Tool is overlapping the Square.
     *
     * @param tool the tool instance which is being checked for overlaps
     * @return True if any point of the tool is overlapping, False otherwise
     */
    private boolean overlapsInsideAtPoint(Tool tool) {
        double rectLeft = this.topLeft.x + (this.lineThickness / 2.0);
        double rectRight = this.topLeft.x + this.size - (this.lineThickness / 2.0);
        double rectTop = this.topLeft.y + (this.lineThickness / 2.0);
        double rectBottom = this.topLeft.y + this.size - (this.lineThickness / 2.0);

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
     * Checks if the Tool is overlapping an outlined Square.
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
        double rectRight = this.topLeft.x + this.size + (this.lineThickness / 2.0);
        double rectTop = this.topLeft.y - (this.lineThickness / 2.0);
        double rectBottom = this.topLeft.y + this.size + (this.lineThickness / 2.0);

        return (eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom)
                && !overlapsInsideAtPoint(tool);
    }

    /**
     * Shifts the top left point of Square by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft = new Point(topLeft.x + x, topLeft.y + y);
    }

    /**
     * Creates a copy of the Square instance.
     *
     * @return a copy of the Square instance
     */
    public Square copy(){
        Square s = new Square(fillStyle, lineThickness);
        s.setColor(color);
        s.setOrigin(origin.copy());
        s.setSize(size);
        s.setTopLeft(topLeft.copy());
        return s;
    }

    /**
     * Displays the Square with user-created color, size, fill style, and line thickness.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the Square
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
        if (this.fillStyle.equals("Solid")) {
            g2d.setFill(this.color);
            g2d.fillRect(this.topLeft.x, this.topLeft.y,
                    this.size, this.size);
        } else if (this.fillStyle.equals("Outline")) {
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokeRect(this.topLeft.x, this.topLeft.y,
                    this.size, this.size);
        }
    }

    /**
     * Sets the properties of the Square based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - x-coordinate of the top-left point
     *             data[1] - y-coordinate of the top-left point
     *             data[2] - x-coordinate of the origin point
     *             data[3] - y-coordinate of the origin point
     *             data[4] - size of the Square
     *             data[5] - color of the Square in web format
     *             data[6] - fill style of the Square
     *             data[7] - line thickness of the Square
     */
    @Override
    public void setShape(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.origin = new Point(Double.parseDouble(data[2]), Double.parseDouble(data[3]));
        this.size = Double.parseDouble(data[4]);
        this.color = Color.web(data[5]);
        this.fillStyle = data[6];
        this.lineThickness = Double.parseDouble(data[7]);
    }

    /**
     * Returns a string representation of the Square instance, including its original
     * top-left point coordinates, origin point coordinates, size, color, fill style,
     * and line thickness.
     *
     * @return a string representation of the Square instance
     */
    public String toString() {
        return "Square{" + this.originalPosition.x + "," + this.originalPosition.y + ","
                + this.origin.x + "," + this.origin.y + ","
                + this.size + "," + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
