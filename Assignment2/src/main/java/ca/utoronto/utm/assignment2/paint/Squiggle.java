package ca.utoronto.utm.assignment2.paint;

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
    
    public ArrayList<Point> getpoints() {
        return this.points;
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
        return "Squiggle";
    }
}
