package ca.utoronto.utm.assignment2.paint;

public class Oval {
    private Point origin;
    private Point topLeft;
    private double width;
    private double height;

    public Oval(Point origin, Point topLeft, double width, double height) {
        this.origin = origin;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() { return this.height; }
    public void setHeight(double height) { this.height = height; }
}
