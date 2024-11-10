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
     * @param topLeft top left point of Oval
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @param width width of Oval
     */
    public void setWidth(double width) {
        this.width = width;
    }

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
     * Checks if the Eraser is overlapping the Oval.
     * If it is, then the Eraser will erase the Oval.
     * @param tool the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Oval, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        if (this.fillStyle.equals("Outline")){
            return overlapsOutline(tool);
        }
        return overlapsSolid(tool);
    }

    private boolean overlapsSolid(Tool tool){
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

    private boolean overlapsInsideAtPoint(Point p){
        double ovalCenterX = topLeft.x + (width / 2.0);
        double ovalCenterY = topLeft.y + (height / 2.0);
        double radiusX = (width / 2.0) - (this.lineThickness/2.0);
        double radiusY = (height / 2.0) - (this.lineThickness/2.0);

        double distanceX = (ovalCenterX - p.x) / radiusX;
        double distanceY = (ovalCenterY - p.y) / radiusY;
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        return distanceSquared <= 1;
    }

    private boolean overlapsOutline(Tool tool){
        double ovalCenterX = topLeft.x + (width / 2.0);
        double ovalCenterY = topLeft.y + (height / 2.0);
        double radiusX = (width / 2.0) + (this.lineThickness/2.0);
        double radiusY = (height / 2.0) + (this.lineThickness/2.0);

        double leftX = tool.getTopLeft().x-(tool.getDimensionX()/2.0);
        double rightX = tool.getTopLeft().x+(tool.getDimensionX()/2.0);
        double topY = tool.getTopLeft().y-(tool.getDimensionY()/2.0);
        double bottomY = tool.getTopLeft().y+(tool.getDimensionY()/2.0);
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(leftX, topY));
        allPoints.add(new Point(leftX, bottomY));
        allPoints.add(new Point(rightX, topY));
        allPoints.add(new Point(rightX, bottomY));
        allPoints.add(tool.getTopLeft());

        for (Point p : allPoints){
            double distanceX = (ovalCenterX - p.x) / radiusX;
            double distanceY = (ovalCenterY - p.y) / radiusY;
            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
            if (distanceSquared <= 1 && !overlapsInsideAtPoint(p)){
                return true;
            }
        }
        return false;
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
     * Sets the properties of the Oval shape based on the provided data array.
     *
     * @param data an array should contain the following elements in order:
     *             data[0] - x-coordinate of the top-left point
     *             data[1] - y-coordinate of the top-left point
     *             data[2] - width of the Oval
     *             data[3] - height of the Oval
     *             data[4] - x-coordinate of the origin
     *             data[5] - y-coordinate of the origin
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

    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
    }

    /**
     * Returns a string representation of an oval.
     * @return a string representation of the oval
     */
    public String toString() {
        return "Oval{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.width + "," + this.height + ","
                + this.origin.x + "," + this.origin.y + ","
                + this.color.toString() + ","
                + this.fillStyle + "," + this.lineThickness + "}";
    }
}
