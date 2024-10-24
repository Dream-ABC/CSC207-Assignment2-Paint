package ca.utoronto.utm.assignment2.paint;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {
    private Point topLeft;
    private double base;
    private double height;

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

    public double getBase() {
        return this.base;
    }

    public void setBase(double width) {
        this.base = width;
    }

    public double getHeight() { return this.height; }
    public void setHeight(double height) { this.height = height; }
    public void updatePoints() {
        Point topVertex = new Point(this.topLeft.x+(this.base/2), this.topLeft.y);
        Point bottomLeftVertex = new Point(this.topLeft.x, this.topLeft.y+this.height);
        Point bottomRightVertex = new Point(this.topLeft.x+this.base, this.topLeft.y+this.height);
        this.getPoints().setAll(topVertex.x, bottomLeftVertex.x, bottomRightVertex.x,
                topVertex.y, bottomLeftVertex.y, bottomRightVertex.y);
    }
}

