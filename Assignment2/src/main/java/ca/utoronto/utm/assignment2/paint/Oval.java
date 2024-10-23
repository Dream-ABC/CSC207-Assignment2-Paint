package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

public class Oval implements Shape {
    private Point origin;
    private Point topLeft;
    private double width;
    private double height;
    private Color color;

    public Oval() {
        this.width = 0;
        this.height = 0;
        this.color = Color.PURPLE;
    }

    public Oval(Point origin, Point topLeft, double width, double height) {
        this.origin = origin;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
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

    @Override
    public Color getColour() {
        return this.color;
    }

    @Override
    public int getThickness() {
        return -1;
    }

    @Override
    public String getShape() {
        return "Oval";
    }
}
