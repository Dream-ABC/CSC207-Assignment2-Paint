package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square implements Shape {
    private Point topLeft;
    private Point origin;
    private double side;
    private Color color;
    private double transparency;

    public Square() {
        this.side = 0;
        this.color = Color.ORANGE;
        this.transparency = 1.0;
    }

    public Square(Point topLeft, double side){
        this.topLeft = topLeft;
        this.side = side;
        this.color = Color.ORANGE;
        this.transparency = 1.0;
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
    public void setTransparency(int transparency) {
        this.transparency = transparency / 100.0;
    }

    @Override
    public double getTransparency() {
        return this.transparency;
    }

    @Override
    public String getShape() {
        return "Square";
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.setGlobalAlpha(this.transparency);
        g2d.fillRect(this.topLeft.x, this.topLeft.y,
                this.side, this.side);
    }
}
