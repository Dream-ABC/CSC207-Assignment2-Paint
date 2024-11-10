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
    private final ArrayList<Point> points;
    private Color color;
    private double lineThickness;

    /**
     * Constructs a default black squiggle with no points.
     */
    public Squiggle(double lineThickness) {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.lineThickness = lineThickness;
    }

    /**
     * Adds a new point to the user's squiggle drawing.
     *
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
     *
     */
    @Override
    public void setLineThickness(double lineThickness) {
        this.lineThickness = lineThickness;
    }

    /**
     * Checks if the Eraser is overlapping the Squiggle.
     * If it is, then the Eraser will erase the entire Squiggle.
     *
     * @return True if the Eraser should erase this Squiggle, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        double leftX = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double rightX = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double topY = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double bottomY = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);
        for (Point p : this.points) {
            if (leftX <= p.x + (this.lineThickness / 2.0) && p.x - (this.lineThickness / 2.0) <= rightX && topY <= p.y + (this.lineThickness / 2.0) && p.y - (this.lineThickness / 2.0) <= bottomY) {
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
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Sets the shape's properties including line thickness, color, and points.
     * The data array must contain these elements in the following order:
     * line thickness, color (in web format), followed by coordinate pairs (x, y) for points.
     *
     * @param data an array of strings where the first element is the line thickness,
     *             the second element is the color in web format, and the remaining
     *             elements are coordinate pairs representing points.
     */
    @Override
    public void setShape(String[] data) {
        this.lineThickness = Double.parseDouble(data[0]);
        this.color = Color.web(data[1]);

        for (int i = 2; i < data.length; i += 2) {
            double x = Double.parseDouble(data[i]);
            double y = Double.parseDouble(data[i + 1]);
            this.points.add(new Point(x, y));
        }
    }

    @Override
    public void shift(double x, double y) {
        for (Point p : this.points) {
            p.shift(x, y);
        }
    }

    public Squiggle copy(){
        Squiggle s = new Squiggle(this.lineThickness);
        s.setColor(this.color);
        for (Point p : this.points) {
            s.addPoint(p.copy());
        }
        return s;
    }

    /**
     * Returns a string representation of a squiggle.
     *
     * @return a string representation of the squiggle
     */
    public String toString() {
        StringBuilder points = new StringBuilder();
        for (Point p : this.points) {
            points.append(p.x + "," + p.y + ",");
        }
        return "Squiggle{" + this.lineThickness + "," + this.color.toString() + "," + points + "}";
    }
}
