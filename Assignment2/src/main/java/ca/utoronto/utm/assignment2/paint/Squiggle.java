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
     * The line thickness is determined by the provided parameters.
     * @param lineThickness ranges from 1.0 to 10.0
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
     * Checks if the Tool is overlapping the Squiggle.
     *
     * @param tool the Tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
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
     * Shifts all points of the Squiggle by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        for (Point p : this.points) {
            p.shift(x, y);
        }
    }

    /**
     * Creates a copy of the Squiggle instance.
     *
     * @return a copy of the Squiggle instance
     */
    public Squiggle copy(){
        Squiggle s = new Squiggle(this.lineThickness);
        s.setColor(this.color);
        for (Point p : this.points) {
            s.addPoint(p.copy());
        }
        return s;
    }

    /**
     * Displays the Squiggle with user-created color, line thickness, and points they drew.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
        for (int i = 0; i < this.points.size() - 1; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i + 1);
            g2d.setStroke(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Sets the properties of the Squiggle shape based on the provided data array.
     *
     * @param data an array should contain the following elements in order:
     *             <p>data[0] - line thickness of the Squiggle</p>
     *             <p>data[2] - color of the Squiggle in web format</p>
     *             <p>data[i, i+1] - the x and y coordinates of a Point in the Squiggle</p>
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

    /**
     * Returns a string representation of a Squiggle.
     *
     * @return a string representation of the Squiggle
     */
    public String toString() {
        StringBuilder points = new StringBuilder();
        for (Point p : this.points) {
            points.append(p.x + "," + p.y + ",");
        }
        return "Squiggle{" + this.lineThickness + "," + this.color.toString() + "," + points + "}";
    }
}
