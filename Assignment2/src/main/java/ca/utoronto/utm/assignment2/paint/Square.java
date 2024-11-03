package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square implements Shape {
    private Point topLeft;
    private Point origin;
    private double side;
    private Color color;

    public Square() {
        this.side = 0;
        this.color = Color.BLACK;
    }

    public Square(Point topLeft, double side){
        this.topLeft = topLeft;
        this.side = side;
        this.color = Color.BLACK;
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
    public String getShape() {
        return "Square";
    }

    @Override
    public boolean overlaps(Eraser eraser) {
        double eraserLeft = eraser.getCentre().x-(eraser.getDimension()/2.0);
        double eraserRight = eraser.getCentre().x+(eraser.getDimension()/2.0);
        double eraserTop = eraser.getCentre().y-(eraser.getDimension()/2.0);
        double eraserBottom = eraser.getCentre().y+(eraser.getDimension()/2.0);

        double rectLeft = this.topLeft.x;
        double rectRight = this.topLeft.x + this.side;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.side;

        return eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom;
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.setFill(this.color);
        g2d.fillRect(this.topLeft.x, this.topLeft.y,
                this.side, this.side);
    }
}
