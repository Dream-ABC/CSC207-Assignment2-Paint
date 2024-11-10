package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PrecisionEraser implements Shape {
    private final ArrayList<Point> points;
    private Point topLeft;
    private double dimension;
    private Color color;

    /**
     * Constructs a default stroke eraser, represented as a square that is of size 14.
     */
    public PrecisionEraser() {
        this.dimension = 14;
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
    }

    /**
     * Adds a new point to the user's squiggle drawing.
     *
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
            g2d.clearRect(p.x - dimension / 2.0, p.y - dimension / 2.0, this.dimension, this.dimension);
            g2d.strokeRect(this.topLeft.x - dimension / 2.0, this.topLeft.y - dimension / 2.0,
                    this.dimension, this.dimension);
        }
    }

    /**
     * Sets the shape properties using the provided array of data.
     *
     * @param data a String array containing the shape's properties. The array elements are expected to
     *             be in the following order:
     *             data[0] - top-left x-coordinate (String representation of a double)
     *             data[1] - top-left y-coordinate (String representation of a double)
     *             data[2] - dimension (String representation of a double)
     *             data[3] - color (String representation of a color in web format)
     *             data[4 and onwards] - additional points as pairs of x and y coordinates
     *                                   (String representation of doubles)
     */
    @Override
    public void setShape(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.dimension = Double.parseDouble(data[2]);
        this.color = Color.web(data[3]);

        for (int i = 4; i < data.length; i += 2) {
            double x = Double.parseDouble(data[i]);
            double y = Double.parseDouble(data[i + 1]);
            this.points.add(new Point(x, y));
        }
    }

    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x, y);
    }

    public PrecisionEraser copy() {
        PrecisionEraser p = new PrecisionEraser();
        p.setTopLeft(this.topLeft.copy());
        p.setColor(this.color);
        for (Point p1 : this.points) {
            p.addPoint(p1.copy());
        }
        return p;
    }

    /**
     * Returns a string representation of the PrecisionEraser object,
     * including its top-left coordinates, dimension, color, and the points
     * it contains.
     *
     * @return a string representation of the PrecisionEraser object
     */
    @Override
    public String toString() {
        StringBuilder points = new StringBuilder();
        for (Point p : this.points) {
            points.append(p.x).append(",").append(p.y).append(",");
        }
        return "Precision Eraser{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.dimension + "," + this.color.toString() + "," + points + "}";
    }
}
