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
    private Point originalPosition;

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
     * Sets the center point of Circle.
     * @param centre centre of Circle
     */
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    /**
     * Sets the top left point of Circle.
     * @param topLeft top left point of Circle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
        this.originalPosition = topLeft.copy();
    }

    /**
     * Returns the centre point of Circle.
     * @return centre of Circle
     */
    public Point getCentre() {
        return this.centre;
    }

    /**
     * Sets the diameter of Circle.
     * @param diameter diameter of Circle
     */
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    /**
     * Returns the color of Circle.
     * @return color of Circle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Circle.
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
        if (overlapsOutline(tool)){
            return true;
        }

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
     * Checks if the Tool is overlapping an outlined Circle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    private boolean overlapsOutline(Tool tool){
        int steps = 1000;
        double dx = this.diameter / steps;
        for (int i = 0; i < steps; i++) {
            double x = topLeft.x + i*dx;
            double y = calculateY(x);
            double centerY = this.topLeft.y + diameter/2;
            if (checkBound(x, centerY - y, tool) || checkBound(x, centerY + y, tool)) {
                return true;
            }
        }
        return false;
    }

    private double calculateY(double x){
        double a = this.diameter/2.0;
        double b = this.diameter/2.0;
        double h = this.topLeft.x + a;
        return b*Math.pow(1-(x-h)*(x-h)/(a*a),0.5);


    }

    private boolean checkBound(double x, double y, Tool tool){
        double leftX = tool.getTopLeft().x-(tool.getDimensionX()/2.0);
        double rightX = tool.getTopLeft().x+(tool.getDimensionX()/2.0);
        double topY = tool.getTopLeft().y-(tool.getDimensionY()/2.0);
        double bottomY = tool.getTopLeft().y+(tool.getDimensionY()/2.0);
        return leftX <= x && x <= rightX && topY <= y && y <= bottomY;
    }

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
        System.out.println("before shift:" +this.topLeft.x + "," + this.topLeft.y);
        this.topLeft.shift(x, y);
        System.out.println("after shift:" +this.topLeft.x + "," + this.topLeft.y);
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
     * @param g2d the GraphicsContext for the current layer used to draw the Circle
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
     *             data[0] - x-coordinate of the top-left point
     *             data[1] - y-coordinate of the top-left point
     *             data[2] - x-coordinate of the centre point
     *             data[3] - y-coordinate of the centre point
     *             data[4] - diameter of the Circle
     *             data[5] - color of the Circle in web format
     *             data[6] - fill style of the Circle
     *             data[7] - line thickness of the Circle
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
     * Returns a string representation of the Circle instance, including its original
     * top-left point, center point, diameter, color, fill style, and line thickness.
     *
     * @return a string representation of the Circle instance
     */
    public String toString() {
        return "Circle{" + this.originalPosition.x + "," + this.originalPosition.y + ","
                + this.centre.x + "," + this.centre.y + ","
                + this.diameter + "," + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
