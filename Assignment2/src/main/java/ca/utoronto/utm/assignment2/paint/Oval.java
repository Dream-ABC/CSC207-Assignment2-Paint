package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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
    public Color getColor() {
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

    @Override
    public boolean overlaps(Eraser eraser) {
        double h = this.topLeft.x+this.width/2.0;
        double k = this.topLeft.y+this.height/2.0;
        double a, b;
        if (this.height > this.width){
            a = this.height/2.0;
            b = this.width/2.0;
        }
        else{
            a = this.width/2.0;
            b = this.height/2.0;
        }
        double leftX = eraser.getCentre().x-(eraser.getDimension()/2.0);
        double rightX = eraser.getCentre().x+(eraser.getDimension()/2.0);
        double topY = eraser.getCentre().y-(eraser.getDimension()/2.0);
        double bottomY = eraser.getCentre().y+(eraser.getDimension()/2.0);
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(leftX, topY));
        allPoints.add(new Point(leftX, bottomY));
        allPoints.add(new Point(rightX, topY));
        allPoints.add(new Point(rightX, bottomY));
        allPoints.add(eraser.getCentre());
        for (Point point : allPoints) {
            if (Math.pow((point.x-h)/a, 2) + Math.pow((point.y-k)/b, 2) <= 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.fillOval(this.topLeft.x, this.topLeft.y,
                this.width, this.height);
    }
}
