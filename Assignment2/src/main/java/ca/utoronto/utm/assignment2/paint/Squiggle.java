package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Squiggle implements Shape {
    
    private ArrayList<Point> points;
    private Color color;
    
    public Squiggle() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
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
    public int getThickness() {
        return -1;
    }

    @Override
    public String getShape() {
        return "Squiggle";
    }

    @Override
    public boolean overlaps(Eraser eraser) {
        return false;
    }

    @Override
    public void display(GraphicsContext g2d) {
        for (int i = 0; i < this.points.size() - 1; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i + 1);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
