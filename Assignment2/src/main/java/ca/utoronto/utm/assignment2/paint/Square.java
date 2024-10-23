package ca.utoronto.utm.assignment2.paint;


import javafx.scene.paint.Color;

public class Square implements Shape {
    private Point topLeft;
    private Point origin;
    private double side;
    private Color color;

    public Square() {
        this.side = 0;
        this.color = Color.ORANGE;
    }

    public Square(Point topLeft, double side){
        this.topLeft = topLeft;
        this.side = side;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

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
        return "Square";
    }
}
