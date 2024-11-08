package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * A class to represent drawing squiggles.
 * Squiggle implements the Shape interface.
 * Squiggles are represented using Points.
 */
public class Squiggle implements Shape {
    private ArrayList<Point> points;
    private Color color;

    /**
     * Constructs a default black squiggle with no points.
     */
    public Squiggle() {
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

    /**
     * @return the color of the Squiggle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Squiggle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the stroke thickness of the Square
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Squiggle' as a string
     */
    @Override
    public String getShape() {
        return "Squiggle";
    }

    /**
     * Checks if the Eraser is overlapping the Squiggle.
     * If it is, then the Eraser will erase the entire Squiggle.
     * @param eraser the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Squiggle, False otherwise
     */
    @Override
    public boolean overlaps(Eraser eraser) {
        for (Point p : this.points) {
            if (eraser.getLeftX() <= p.x &&
                    p.x <= eraser.getRightX() &&
                    eraser.getTopY() <= p.y &&
                    p.y <= eraser.getBottomY()){
                return true;
            }
        }
        return false;
    }

    /**
     * Displays the Squiggle with user-created color and points they drew.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        for (int i = 0; i < this.points.size() - 1; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i + 1);
            g2d.setStroke(this.color);  // since there's no fill colour
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
