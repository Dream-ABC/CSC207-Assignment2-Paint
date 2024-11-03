package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval implements Shape {
    private Point origin;
    private Point topLeft;
    private double width;
    private double height;
    private Color color;
    private double opaqueness;

    public Oval() {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
        this.opaqueness = 1.0;
    }

    public Oval(Point origin, Point topLeft, double width, double height) {
        this.origin = origin;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = Color.BLACK;
        this.opaqueness = 1.0;
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
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int getThickness() {
        return -1;
    }

    @Override
    public void setOpaqueness(int opaque) {
        this.opaqueness = opaque / 100.0;
    }

    @Override
    public double getOpaqueness() {
        return this.opaqueness;
    }

    @Override
    public String getShape() {
        return "Oval";
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.setGlobalAlpha(this.opaqueness);
        g2d.setFill(this.color);
        g2d.fillOval(this.topLeft.x, this.topLeft.y,
                this.width, this.height);
    }
}
