package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Polyline implements Shape {
    private ArrayList<Point> points;
    private Color color;
    private boolean isClosed;

    /**
     * Constructs a default black polyline with no points.
     */
    public Polyline() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.isClosed = false;
    }

    /**
     * Adds a new point to the user's squiggle drawing.
     * @param p new point in Polyline
     */
    public void addPoint(Point p) {
        this.points.add(p);
    }

    /**
     * @return the color of the Polyline
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Polyline
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the stroke thickness of the Polyline
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Polyline' as a string
     */
    @Override
    public String getShape() {
        return "Polyline";
    }

    /**
     * Checks if the Eraser is overlapping the Polyline.
     * If it is, then the Eraser will erase the entire Polyline.
     * @param eraser the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Polyline, False otherwise
     */
    @Override
    public boolean overlaps(Eraser eraser) {
        int size = this.isClosed ? this.points.size() : this.points.size() - 1;
        for (int i = 0; i < size; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get((i + 1) % this.points.size());
            }
        return false;
    }

    /**
     * Displays the Polyline with user-created color and points they drew.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        int size = this.isClosed ? this.points.size() : this.points.size() - 1;
        for (int i = 0; i < size; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get((i + 1) % this.points.size());
            g2d.setStroke(this.color);  // since there's no fill colour
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
