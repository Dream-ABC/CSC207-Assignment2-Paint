package ca.utoronto.utm.assignment2.paint;


import javafx.scene.paint.Color;

public class Rectangle implements Shape {
    private Point topLeft;
    private double width;
    private double height;
    private Point origin;
    private Color color;

    /*
    Constructor with no parameter. This constructor
    creates a default rectangle with width 0 and height 0.
     */
    public Rectangle() {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLUE;
    }

    /*
    Constructor that created a circle with user defined
    topLeft, width and height.
     */
    public Rectangle(Point topLeft, double width, double height){
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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
        return "Rectangle";
    }
}
