package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    public Color getColor() {
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

    @Override
    public boolean overlaps(Eraser eraser) {
        Rectangle e = new Rectangle(eraser.getCentre().x+(eraser.getDimension()/2.0), eraser.getCentre().y+(eraser.getDimension()/2.0), eraser.getDimension(), eraser.getDimension());
        Rectangle s = new Rectangle(this.topLeft.x, this.topLeft.y, this.side, this.side);
        return e.getBoundsInParent().intersects(s.getBoundsInParent());
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.fillRect(this.topLeft.x, this.topLeft.y,
                this.side, this.side);
    }
}
