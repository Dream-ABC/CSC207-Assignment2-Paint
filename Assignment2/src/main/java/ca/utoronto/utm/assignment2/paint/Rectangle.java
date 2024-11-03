package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape {
    private Point topLeft;
    private double width;
    private double height;
    private Point origin;
    private Color color;
    private double opaqueness;

    /*
    Constructor with no parameter. This constructor
    creates a default rectangle with width 0 and height 0.
     */
    public Rectangle() {
        this.width = 0;
        this.height = 0;
        this.color = Color.BLACK;
        this.opaqueness = 1.0;
    }

    /*
    Constructor that created a circle with user defined
    topLeft, width and height.
     */
    public Rectangle(Point topLeft, double width, double height){
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = Color.BLACK;
        this.opaqueness = 1.0;
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
        return "Rectangle";
    }

    @Override
    public boolean overlaps(Eraser eraser) {
        double eraserLeft = eraser.getCentre().x-(eraser.getDimension()/2.0);
        double eraserRight = eraser.getCentre().x+(eraser.getDimension()/2.0);
        double eraserTop = eraser.getCentre().y-(eraser.getDimension()/2.0);
        double eraserBottom = eraser.getCentre().y+(eraser.getDimension()/2.0);

        double rectLeft = this.topLeft.x;
        double rectRight = this.topLeft.x + this.width;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.height;

        return eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom;
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.setGlobalAlpha(this.opaqueness);
        g2d.setFill(this.color);
        g2d.fillRect(this.topLeft.x, this.topLeft.y,
                this.width, this.height);
    }
}
