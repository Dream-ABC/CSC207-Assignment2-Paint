package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PrecisionEraser implements Shape {
    private ArrayList<Point> points;
    private Point topLeft;
    private double dimension;

    /**
     * Constructs a default stroke eraser, represented as a square that is of size 14.
     */
    public PrecisionEraser(){
        this.dimension = 14;
        this.points = new ArrayList<>();
    }

    /**
     * Adds a new point to the user's squiggle drawing.
     * @param p new point in Squiggle
     */
    public void addPoint(Point p) {
        this.points.add(p);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public double getDimension() {
        return dimension;
    }

    @Override
    public void setColor(Color color) {
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setLineThickness(double lineThickness) {
    }

    @Override
    public String getShape() {
        return "";
    }

    @Override
    public boolean overlaps(Tool tool) {
        return false;
    }

    @Override
    public void display(GraphicsContext g2d) {
        for (Point p : points) {
            g2d.setLineWidth(1);
            g2d.setStroke(Color.BLACK);
            g2d.clearRect(p.x, p.y, this.dimension, this.dimension);
        }
    }
}
