package ca.utoronto.utm.assignment2.paint;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;

/**
 * A class to represent drawing isosceles triangles.
 * Triangle implements the Shape interface and extends the Polygon class.
 */
public class Triangle extends Polygon implements Shape {
    private Point topLeft;
    private double base;
    private double height;
    private Point origin;
    private Color color;
    private String fillStyle;
    private double lineThickness;

    /**
     * Constructs a default black triangle with a base and height of 0.
     */
    public Triangle(String fillStyle, double lineThickness) {
        this.base = 0.0;
        this.height = 0.0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * @return the top left point of the Triangle
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * @param topLeft top left point of Triangle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the origin of the Triangle (first mouse click)
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * @param origin origin of Triangle (first mouse click)
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * @param base base width of Triangle
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * @return the height of the Triangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @param height height of Triangle
     */
    public void setHeight(double height) {
        this.height = height;
    }


    public void updatePoints() {
        Point topVertex = new Point(this.topLeft.x + (this.base / 2), this.topLeft.y);
        Point bottomLeftVertex = new Point(this.topLeft.x, this.topLeft.y + this.height);
        Point bottomRightVertex = new Point(this.topLeft.x + this.base, this.topLeft.y + this.height);
        this.getPoints().setAll(topVertex.x, bottomLeftVertex.x, bottomRightVertex.x,
                topVertex.y, bottomLeftVertex.y, bottomRightVertex.y);
    }

    /**
     * @return the color of the Triangle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Triangle
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
     * Calculates the area of a triangle created by three points (x1, y1), (x2, y2), (x3, y3).
     * @param x1 x-coord of first point on triangle
     * @param y1 y-coord of first point on triangle
     * @param x2 x-coord of second point on triangle
     * @param y2 y-coord of second point on triangle
     * @param x3 x-coord of third point on triangle
     * @param y3 y-coord of third point on triangle
     * @return the area of the triangle
     */
    private double areaOfTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
    }

    /**
     * Checks if the Eraser is overlapping the Triangle.
     * If it is, then the Eraser will erase the Triangle.
     * @param tool the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Triangle, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        if (this.fillStyle.equals("Outline")){
            return overlapsOutline(tool);
        }
        return overlapsSolid(tool);
    }

    private boolean overlapsSolid(Tool tool){
        ObservableList<Double> points = this.getPoints(); // top, left, right
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }

        double A = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

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

        for (Point point : allPoints) {
            double a1 = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], point.x, point.y);
            double a2 = areaOfTriangle(point.x, point.y, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
            double a3 = areaOfTriangle(xPoints[0], yPoints[0], point.x, point.y, xPoints[2], yPoints[2]);
            if (a1 + a2 + a3 == A){
                return true;
            }
        }

        for (int i = 0; i < 3; i++){
            if ((leftX <= xPoints[i]) && (xPoints[i] <= rightX) && (topY <= yPoints[i]) && (yPoints[i] <= bottomY)) {return true;}
        }

        return false;
    }

    private boolean overlapsInsideAtPoint(Point p){
        ObservableList<Double> points = this.getPoints(); // top, left, right
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        xPoints[0] = points.get(0);
        yPoints[0] = points.get(3) + (this.lineThickness);
        xPoints[1] = points.get(1) + (this.lineThickness/2.0);
        yPoints[1] = points.get(4) - (this.lineThickness/2.0);
        xPoints[2] = points.get(2) - (this.lineThickness/2.0);
        yPoints[2] = points.get(5) - (this.lineThickness/2.0);

        double A = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

        double a1 = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], p.x, p.y);
        double a2 = areaOfTriangle(p.x, p.y, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
        double a3 = areaOfTriangle(xPoints[0], yPoints[0], p.x, p.y, xPoints[2], yPoints[2]);
        double marginOfError = 1e-10;
        return Math.abs(A - a1 - a2 - a3) <= marginOfError;
    }

    private boolean overlapsOutline(Tool tool){
        ObservableList<Double> points = this.getPoints(); // top, left, right
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = points.get(0);
        yPoints[0] = points.get(3) - (this.lineThickness);
        xPoints[1] = points.get(1) - (this.lineThickness/2.0);
        yPoints[1] = points.get(4) + (this.lineThickness/2.0);
        xPoints[2] = points.get(2) + (this.lineThickness/2.0);
        yPoints[2] = points.get(5) + (this.lineThickness/2.0);

        double A = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

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

        for (Point point : allPoints) {
            double a1 = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], point.x, point.y);
            double a2 = areaOfTriangle(point.x, point.y, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
            double a3 = areaOfTriangle(xPoints[0], yPoints[0], point.x, point.y, xPoints[2], yPoints[2]);
            if (a1 + a2 + a3 == A && !overlapsInsideAtPoint(point)){
                return true;
            }
        }

        for (int i = 0; i < 3; i++){
            if ((leftX <= xPoints[i]) &&
                    (xPoints[i] <= rightX) &&
                    (topY <= yPoints[i]) &&
                    (yPoints[i] <= bottomY) &&
                    (overlapsInsideAtPoint(new Point(xPoints[i], yPoints[i]))))
            {return true;}
        }

        return false;
    }

    /**
     * Displays the Triangle with user-created color and size.
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        ObservableList<Double> points = this.getPoints();
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }

        if (this.fillStyle.equals("Solid")){
            g2d.setFill(this.color);
            g2d.fillPolygon(xPoints, yPoints, 3);
        }
        else if (this.fillStyle.equals("Outline")){
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokePolygon(xPoints, yPoints, 3);
        }
    }

    /**
     * Sets the shape properties of the Triangle object based on the provided data array.
     * Each element in the data array should correspond to a specific property of the Triangle.
     *
     * @param data an array of strings containing the values to set the Triangle properties.
     *             The elements should be in the following order:
     *             data[0] - x coordinate of the top-left point
     *             data[1] - y coordinate of the top-left point
     *             data[2] - base length of the Triangle
     *             data[3] - height of the Triangle
     *             data[4] - x coordinate of the origin point
     *             data[5] - y coordinate of the origin point
     *             data[6] - fill style of the Triangle
     *             data[7] - line thickness of the Triangle
     *             data[8] - color of the Triangle in web format (e.g., "#ff0000" for red)
     */
    @Override
    public void setShape(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.base = Double.parseDouble(data[2]);
        this.height = Double.parseDouble(data[3]);
        this.origin = new Point(Double.parseDouble(data[4]), Double.parseDouble(data[5]));
        this.fillStyle = data[6];
        this.lineThickness = Double.parseDouble(data[7]);
        this.color = Color.web(data[8]);

        this.updatePoints();
    }

    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
        updatePoints();
    }

    /**
     * Returns a string representation of a triangle.
     *
     * @return a string representation of the triangle
     */
    public String toString() {
        return "Triangle{" + this.topLeft.x + "," + this.topLeft.y + "," + this.base + "," + this.height + ","
                + this.origin.x + "," + this.origin.y + "," + this.fillStyle + "," + this.lineThickness + ","
                + this.color.toString() + "}";
    }
}
