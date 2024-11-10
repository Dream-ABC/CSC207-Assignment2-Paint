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
    private String fillStyle;
    private double lineThickness;

    /**
     * Constructs a default black oval with a width and height of 0.
     * The fill style and line thickness are determined by the provided parameters.
     * @param fillStyle "Solid"/"Outline"
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Oval(String fillStyle, double lineThickness) {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * Returns the origin point of Oval (first mouse click).
     * @return the origin of Oval
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin point of Oval (first mouse click).
     * @param origin origin of Oval
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Sets the top left point of Oval.
     * @param topLeft top left point of Oval
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Sets the width of Oval.
     * @param width width of Oval
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Sets the height of Oval.
     * @param height height of Oval
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * Returns the color of Oval.
     * @return the color of Oval
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Oval.
     * @param color color of Oval
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the Tool is overlapping the Oval.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        if (this.fillStyle.equals("Outline")){
            return overlapsOutline(tool);
        }
        return overlapsSolid(tool);
    }

    /**
     * Checks if the Tool is overlapping a solid Oval.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    private boolean overlapsSolid(Tool tool){
        if (overlapsOutline(tool)){
            return true;
        }

        double ovalCenterX = topLeft.x + (width / 2.0);
        double ovalCenterY = topLeft.y + (height / 2.0);
        double radiusX = (width / 2.0);
        double radiusY = (height / 2.0);

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

    /**
     * Checks if the Tool is overlapping an outlined Oval.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    private boolean overlapsOutline(Tool tool){
        int steps = 1000;
        double dx = this.width / steps;
        for (int i = 0; i < steps; i++) {
            double x = topLeft.x + i*dx;
            double y = calculateY(x);
            double centerY = this.topLeft.y + height/2;
            if (checkBound(x, centerY - y, tool) || checkBound(x, centerY + y, tool)) {
                return true;
            }
        }
        return false;
    }

    private double calculateY(double x){
        double a = this.width/2.0;
        double b = this.height/2.0;
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
     * Shifts the top left point of Oval by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
    }

    /**
     * Creates a copy of the Oval instance.
     *
     * @return a copy of the Oval instance
     */
    @Override
    public Oval copy(){
        Oval o = new Oval(fillStyle, lineThickness);
        o.setColor(color);
        o.setHeight(height);
        o.setWidth(width);
        o.setOrigin(origin.copy());
        o.setTopLeft(topLeft.copy());
        return o;
    }

    /**
     * Displays the Oval with user-created color, size, fill style, and line thickness.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the Oval
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
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

    /**
     * Sets the properties of the Oval based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - x-coordinate of the top-left point
     *             data[1] - y-coordinate of the top-left point
     *             data[2] - width of the Ova
     *             data[3] - height of the Oval
     *             data[4] - x-coordinate of the origin point
     *             data[5] - y-coordinate of the origin point
     *             data[6] - color of the Oval in web format
     *             data[7] - fill style of the Oval
     *             data[8] - line thickness of the Oval
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
     * Returns a string representation of the Oval instance, including its coordinates
     * of the top-left point, width, height, coordinates of the origin, color, fill style,
     * and line thickness.
     *
     * @return a string representation of the Oval instance
     */
    public String toString() {
        return "Oval{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.width + "," + this.height + ","
                + this.origin.x + "," + this.origin.y + ","
                + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
