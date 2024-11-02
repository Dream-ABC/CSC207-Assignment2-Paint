package ca.utoronto.utm.assignment2.paint;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon implements Shape {
    private Point topLeft;
    private double base;
    private double height;
    private Point origin;
    private Color color;
    private double transparency;

    public Triangle() {
        this.base = 0.0;
        this.height = 0.0;
        this.color = Color.RED;
        this.transparency = 1.0;
    }

    public Triangle(Point topLeft, double base, double height) {
        this.topLeft = topLeft;
        this.base = base;
        this.height = height;
        this.color = Color.RED;
        this.transparency = 1.0;
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
    public void setTransparency(int transparency) {
        this.transparency = transparency / 100.0;
    }

    @Override
    public double getTransparency() {
        return this.transparency;
    }

    @Override
    public String getShape() {
        return "Triangle";
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
        g2d.setGlobalAlpha(this.transparency);
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}

