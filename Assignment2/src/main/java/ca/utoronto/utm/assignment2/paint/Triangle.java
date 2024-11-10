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
     * The fill style and line thickness are determined by the provided parameters.
     * @param fillStyle "Solid"/"Outline"
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Triangle(String fillStyle, double lineThickness) {
        this.base = 0.0;
        this.height = 0.0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
    }

    /**
     * Returns the top left point of Triangle.
     * @return the top left point of Triangle
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * Sets the top left point of Triangle.
     * @param topLeft top left point of Triangle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Returns the origin point of Triangle (first mouse click).
     * @return the origin of the Triangle
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Sets the origin point of Triangle (first mouse click).
     * @param origin origin of Triangle
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Sets the base width of Triangle.
     * @param base base width of Triangle
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * Returns the height of Triangle.
     * @return the height of Triangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the height of Triangle.
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
     * Returns the color of Triangle.
     * @return the color of Triangle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Triangle.
     * @param color color of Triangle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
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
     * Checks if the Tool is overlapping the Triangle.
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
     * Checks if the Tool is overlapping a solid Triangle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    private boolean overlapsSolid(Tool tool){
        ObservableList<Double> points = this.getPoints(); // top, left, right
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }

        // Checks if any point of the tool (left, right, top, bottom) are inside the triangle.
        // This is calculated using math:
        //      If a point is inside a triangle, then the 3 triangles formed from that point
        //      with the vertexes of the og triangle, will have the same area as the og triangle

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
            double marginOfError = 1e-10;
            if (Math.abs(A - a1 - a2 - a3) <= marginOfError){
                return true;
            }
        }

        // Checks if any of the triangle vertexes is inside the tool
        for (int i = 0; i < 3; i++){
            if ((leftX <= xPoints[i]) && (xPoints[i] <= rightX) && (topY <= yPoints[i]) && (yPoints[i] <= bottomY)) {return true;}
        }

        return false;
    }

    /**
     * Checks if the Point is overlapping the Triangle.
     *
     * @param p the point which is being checked for overlaps
     * @return True if the point is overlapping, False otherwise
     */
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

    /**
     * Checks if the Tool is overlapping an outlined Triangle.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
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
            double marginOfError = 1e-10;
            if (Math.abs(A - a1 - a2 - a3) <= marginOfError && !overlapsInsideAtPoint(point)){
                return true;
            }
        }

        for (int i = 0; i < 3; i++){
            if ((leftX <= xPoints[i]) &&
                    (xPoints[i] <= rightX) &&
                    (topY <= yPoints[i]) &&
                    (yPoints[i] <= bottomY) &&
                    (!overlapsInsideAtPoint(new Point(xPoints[i], yPoints[i]))))
            {return true;}
        }
        return false;
    }

    /**
     * Shifts the top left point of Triangle by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
        updatePoints();
    }

    /**
     * Creates a copy of the Triangle instance.
     *
     * @return a copy of the Triangle instance
     */
    public Triangle copy() {
        Triangle t = new Triangle(fillStyle, lineThickness);
        t.setBase(base);
        t.setColor(color);
        t.setHeight(height);
        t.setOrigin(origin.copy());
        t.setTopLeft(topLeft.copy());
        return t;
    }

    /**
     * Displays the Triangle with user-created color, size, fill style, and line thickness.
     * @param g2d the GraphicsContext for the current layer used to draw the Triangle
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
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
     * Sets the properties of the Triangle based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - x-coordinate of the top-left point
     *             data[1] - y-coordinate of the top-left point
     *             data[2] - base length of the Triangle
     *             data[3] - height of the Triangle
     *             data[4] - x-coordinate of the origin point
     *             data[5] - y-coordinate of the origin point
     *             data[6] - fill style of the Triangle
     *             data[7] - line thickness of the Triangle
     *             data[8] - color of the Triangle in web format
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

    /**
     * Returns a string representation of the Triangle instance, including its top-left coordinates,
     * base, height, origin coordinates, fill style, line thickness, and color.
     *
     * @return a string representation of the Triangle instance
     */
    public String toString() {
        return "Triangle{" + this.topLeft.x + "," + this.topLeft.y + "," + this.base + "," + this.height + ","
                + this.origin.x + "," + this.origin.y + "," + this.fillStyle + "," + this.lineThickness + ","
                + this.color.toString() + "}";
    }
}
