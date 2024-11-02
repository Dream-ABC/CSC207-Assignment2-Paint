package ca.utoronto.utm.assignment2.paint;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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

    @Override
    public boolean overlaps(Eraser eraser) {
        javafx.scene.shape.Rectangle e = new Rectangle(eraser.getCentre().x+(eraser.getDimension()/2.0), eraser.getCentre().y+(eraser.getDimension()/2.0), eraser.getDimension(), eraser.getDimension());
        Polygon t = new Polygon();
        ObservableList<Double> points = this.getPoints();
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = points.get(i);
            yPoints[i] = points.get(i + 3);
        }
        t.getPoints().addAll(
                xPoints[0], yPoints[0],
                xPoints[1], yPoints[1],
                xPoints[2], yPoints[2]);
        return e.getBoundsInParent().intersects(t.getBoundsInParent());
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

