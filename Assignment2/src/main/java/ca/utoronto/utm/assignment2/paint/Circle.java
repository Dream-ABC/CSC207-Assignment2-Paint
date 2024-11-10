package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent drawing circles.
 * Circle implements the Shape interface.
 */
public class Circle implements Shape {
    private Point topLeft;
    private Point centre;
    private double diameter;
    private Color color;
    private String fillStyle;
    private double lineThickness;

    /**
     * Constructs a default black circle with a diameter of 0.
     * The fill style and line thickness are determined by the provided parameters.
     * @param fillStyle "Solid"/"Outline"
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Circle(String fillStyle, double lineThickness) {
        this.diameter = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * Sets the center point of Circle
     * @param centre centre of Circle
     */
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    /**
     * Sets the top left point of Circle
     * @param topLeft top left point of Circle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Returns the centre point of Circle
     * @return centre of Circle.
     */
    public Point getCentre() {
        return this.centre;
    }

    /**
     * Sets the diameter of Circle
     * @param diameter diameter of Circle
     */
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    /**
     * Returns the color of Circle
     * @return color of Circle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Circle
     * @param color color of Circle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the Tool is overlapping the Circle.
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
     * Checks if the Tool is overlapping a solid Circle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    private boolean overlapsSolid(Tool tool) {
        double centerX = topLeft.x + (diameter / 2.0);
        double centerY = topLeft.y + (diameter / 2.0);
        double radius = (diameter / 2.0);

        double rectLeft = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double rectRight = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double rectTop = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double rectBottom = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);

        double closestX = clamp(centerX, rectLeft, rectRight);
        double closestY = clamp(centerY, rectTop, rectBottom);

        double distanceX = (centerX - closestX) / radius;
        double distanceY = (centerY - closestY) / radius;
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        return distanceSquared <= 1;
    }

    /**
     * Checks if the Point is overlapping the Circle.
     *
     * @param p the point which is being checked for overlaps
     * @return True if the point is overlapping, False otherwise
     */
    private boolean overlapsInsideAtPoint(Point p) {
        double centerX = topLeft.x + (diameter / 2.0);
        double centerY = topLeft.y + (diameter / 2.0);
        double radius = (diameter / 2.0) - (this.lineThickness / 2.0);

        double distanceX = (centerX - p.x) / radius;
        double distanceY = (centerY - p.y) / radius;
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        return distanceSquared <= 1;
    }

    /**
     * Checks if the Tool is overlapping an outlined Circle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    private boolean overlapsOutline(Tool tool) {
        double centerX = topLeft.x + (diameter / 2.0);
        double centerY = topLeft.y + (diameter / 2.0);
        double radius = (diameter / 2.0) + (this.lineThickness / 2.0);

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
            double distanceX = (centerX - p.x) / radius;
            double distanceY = (centerY - p.y) / radius;
            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
            if (distanceSquared <= 1 && !overlapsInsideAtPoint(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     *
     * @param value given value
     * @param min minimum value
     * @param max maximum value
     * @return
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Shifts the top left point of Circle by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x, y);
    }

    /**
     * Creates a copy of the Circle instance.
     *
     * @return a copy of the Circle instance
     */
    @Override
    public Shape copy(){
        Circle c = new Circle(fillStyle, lineThickness);
        c.setDiameter(diameter);
        c.setCentre(centre.copy());
        c.setColor(color);
        c.setTopLeft(topLeft.copy());
        return c;
    }

    /**
     * Displays the Circle with user-created color, size, fill style, and line thickness.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
        if (this.fillStyle.equals("Solid")) {
            g2d.setFill(this.color);
            g2d.fillOval(this.topLeft.x, this.topLeft.y,
                    this.diameter, this.diameter);
        } else if (this.fillStyle.equals("Outline")) {
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokeOval(this.topLeft.x, this.topLeft.y, this.diameter, this.diameter);
        }
    }

    /**
     * Sets the properties of the Circle based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             <p>data[0] - x-coordinate of the top-left point</p>
     *             <p>data[1] - y-coordinate of the top-left point</p>
     *             <p>data[2] - x-coordinate of the centre point</p>
     *             <p>data[3] - y-coordinate of the centre point</p>
     *             <p>data[4] - diameter of the Circle</p>
     *             <p>data[5] - color of the Circle in web format</p>
     *             <p>data[6] - fill style of the Circle</p>
     *             <p>data[7] - line thickness of the Circle</p>
     */
    @Override
    public void setShape(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.centre = new Point(Double.parseDouble(data[2]), Double.parseDouble(data[3]));
        this.diameter = Double.parseDouble(data[4]);
        this.color = Color.web(data[5]);
        this.fillStyle = data[6];
        this.lineThickness = Double.parseDouble(data[7]);
    }

    /**
     * Returns a string representation of a Circle.
     *
     * @return a string representation of the Circle
     */
    public String toString() {
        return "Circle{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.centre.x + "," + this.centre.y + ","
                + this.diameter + "," + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
