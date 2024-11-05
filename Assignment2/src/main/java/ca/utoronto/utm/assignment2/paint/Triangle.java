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

    /**
     * Constructs a default black triangle with a base and height of 0.
     */
    public Triangle() {
        this.base = 0.0;
        this.height = 0.0;
        this.color = Color.BLACK;
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
     * @return the stroke thickness of the Triangle
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Triangle' as a string
     */
    @Override
    public String getShape() {
        return "Triangle";
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
     * @param eraser the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Triangle, False otherwise
     */
    @Override
    public boolean overlaps(Eraser eraser) {
        ObservableList<Double> points = this.getPoints();
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }
        double A = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2]);

        double leftX = eraser.getCentre().x-(eraser.getDimension()/2.0);
        double rightX = eraser.getCentre().x+(eraser.getDimension()/2.0);
        double topY = eraser.getCentre().y-(eraser.getDimension()/2.0);
        double bottomY = eraser.getCentre().y+(eraser.getDimension()/2.0);
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(leftX, topY));
        allPoints.add(new Point(leftX, bottomY));
        allPoints.add(new Point(rightX, topY));
        allPoints.add(new Point(rightX, bottomY));
        allPoints.add(eraser.getCentre());

        for (Point point : allPoints) {
            double a1 = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], point.x, point.y);
            double a2 = areaOfTriangle(point.x, point.y, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
            double a3 = areaOfTriangle(xPoints[0], yPoints[0], point.x, point.y, xPoints[2], yPoints[2]);
            if (a1 + a2 + a3 == A){
                return true;
            }
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
        g2d.setFill(this.color);
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}

