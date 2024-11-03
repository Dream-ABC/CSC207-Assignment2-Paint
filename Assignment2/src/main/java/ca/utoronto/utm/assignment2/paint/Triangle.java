package ca.utoronto.utm.assignment2.paint;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Triangle extends Polygon implements Shape {
    private Point topLeft;
    private double base;
    private double height;
    private Point origin;
    private Color color;

    public Triangle() {
        this.base = 0.0;
        this.height = 0.0;
        this.color = Color.RED;
    }

    public Triangle(Point topLeft, double base, double height) {
        this.topLeft = topLeft;
        this.base = base;
        this.height = height;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public double getBase() {
        return this.base;
    }

    public void setBase(double width) {
        this.base = width;
    }

    public double getHeight() {
        return this.height;
    }

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

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public int getThickness() {
        return -1;
    }

    @Override
    public String getShape() {
        return "Triangle";
    }

    private double areaOfTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
    }

    @Override
    public boolean overlaps(Eraser eraser) {
        ObservableList<Double> points = this.getPoints();
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }
        // Area A = [ x1(y2 – y3) + x2(y3 – y1) + x3(y1-y2)]/2
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
        for (Point point : allPoints) {
            // Area A = [ x0(y1 – y2) + x1(y2 – y0) + x2(y0-y1)]/2
            // A = (x0, y0), B = (x1, y1), C = (x2, y2), P = x, y
            // ABP
            double a1 = areaOfTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], point.x, point.y);
            // PBC
            double a2 = areaOfTriangle(point.x, point.y, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
            // APC
            double a3 = areaOfTriangle(xPoints[0], yPoints[0], point.x, point.y, xPoints[2], yPoints[2]);
            if (a1 + a2 + a3 == A){
                return true;
            }
        }
        return false;
    }

    @Override
    public void display(GraphicsContext g2d) {
        ObservableList<Double> points = this.getPoints();
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}

