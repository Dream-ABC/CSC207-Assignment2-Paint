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

    /**
     * Constructs a default black square with a size of 0.
     */
    public Square(String fillStyle, double lineThickness) {
        this.size = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * @return the top left point of the Square
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * @param topLeft top left corner of Square
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the size of the Square
     */
    public double getSize() {
        return this.size;
    }

    /**
     * @param size size of Square (width and height)
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * @return the origin of the Rectangle (first mouse click)
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * @param origin origin of Square (first mouse click)
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * @return the color of the Square
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Square
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
     * Checks if the Tool is overlapping the Square.
     * If it is, then the Tool will erase the Square.
     *
     * @param tool the Tool instance which is currently erasing drawings
     * @return True if the Tool should erase this Square, False otherwise
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
        double rectRight = this.topLeft.x + this.size;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.size;

        return eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom;
    }

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

    @Override
    public void shift(double x, double y) {
        this.topLeft = new Point(topLeft.x + x, topLeft.y + y);
    }

    public Square copy(){
        Square s = new Square(fillStyle, lineThickness);
        s.setColor(color);
        s.setOrigin(origin.copy());
        s.setSize(size);
        s.setTopLeft(topLeft.copy());
        return s;
    }

    /**
     * Displays the Square with user-created color and size.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
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
     * Sets the properties of a Square instance using an array of Strings.
     *
     * @param data an array of Strings where:
     *             data[0] is the x-coordinate of the top left point,
     *             data[1] is the y-coordinate of the top left point,
     *             data[2] is the x-coordinate of the origin point,
     *             data[3] is the y-coordinate of the origin point,
     *             data[4] is the size of the square,
     *             data[5] is the color in web format,
     *             data[6] is the fill style,
     *             data[7] is the line thickness.
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
     * Returns a string representation of a square.
     *
     * @return a string representation of the square
     */
    public String toString() {
        return "Square{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.origin.x + "," + this.origin.y + ","
                + this.size + "," + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
