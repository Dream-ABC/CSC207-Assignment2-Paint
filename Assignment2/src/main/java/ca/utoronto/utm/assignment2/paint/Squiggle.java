package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Squiggle implements Shape {
    
    private ArrayList<Point> points;
    private Color color;
    private double opaqueness;
    
    public Squiggle() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.opaqueness = 1.0;
    }
    
    public void addPoint(Point p) {
        this.points.add(p);
    }
    
    public ArrayList<Point> getPoints() {
        return this.points;
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
        return "Squiggle";
    }

    @Override
    public boolean overlaps(Eraser eraser) {
        double leftX = eraser.getCentre().x-(eraser.getDimension()/2.0);
        double rightX = eraser.getCentre().x+(eraser.getDimension()/2.0);
        double topY = eraser.getCentre().y-(eraser.getDimension()/2.0);
        double bottomY = eraser.getCentre().y+(eraser.getDimension()/2.0);
        for (Point p : this.points) {
            if (leftX <= p.x && p.x <= rightX && topY <= p.y && p.y <= bottomY){
                return true;
            }
        }
        return false;
    }

    @Override
    public void display(GraphicsContext g2d) {
        for (int i = 0; i < this.points.size() - 1; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i + 1);
            g2d.setGlobalAlpha(this.opaqueness);
            g2d.setStroke(this.color);  // since there's no fill colour
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
