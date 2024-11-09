package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PrecisionEraser implements Shape {
    private final ArrayList<Point> points;
    private Point topLeft;
    private final double dimension;
    private Color color;

    /**
     * Constructs a default stroke eraser, represented as a square that is of size 14.
     */
    public PrecisionEraser(){
        this.dimension = 14;
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
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

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setLineThickness(double lineThickness) {
    }

    @Override
    public boolean overlaps(Tool tool) {
        return false;
    }

    @Override
    public void display(GraphicsContext g2d) {
        for (Point p : points) {
            g2d.setLineWidth(1);
            g2d.setStroke(this.color);
            g2d.setLineDashes(5, 3);
            g2d.clearRect(p.x  - dimension/2.0, p.y  - dimension/2.0, this.dimension, this.dimension);
            g2d.strokeRect(this.topLeft.x - dimension/2.0, this.topLeft.y - dimension/2.0,
                    this.dimension, this.dimension);
        }
    }
}
