package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent the precision eraser.
 * PrecisionEraser implements the Shape interface.
 */
public class PrecisionEraser implements Shape {
    private final ArrayList<Point> points;
    private Point topLeft;
    private double dimension;
    private Color color;

    /**
     * Constructs a default precision eraser, which is a square of size 14.
     */
    public PrecisionEraser() {
        this.dimension = 14;
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
    }

    /**
     * Adds a new point to the user's precise erasing.
     *
     * @param p new point in PrecisionEraser
     */
    public void addPoint(Point p) {
        this.points.add(p);
    }

    /**
     * Sets the top left point of PrecisionEraser.
     * @param topLeft top left point of PrecisionEraser
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Sets the color of PrecisionEraser.
     * @param color color of PrecisionEraser
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the color of PrecisionEraser.
     * @return the color of PrecisionEraser
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Checks if the Tool is overlapping the PrecisionEraser.
     *
     * @param tool the tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        return false;
    }

    /**
     * Shifts the top left point of the PreciseEraser by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x, y);
    }

    /**
     * Creates a copy of the PreciseEraser instance.
     *
     * @return a copy of the PreciseEraser instance
     */
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
     * Displays the PrecisionEraser as a square with a black dashed outline.
     * Then, it draws a transparent-colored square the size of the precision eraser,
     * at the PrecisionEraser's position.
     * The user's mouse position represents the centre of the PrecisionEraser.
     * The default size of this eraser is 14.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the PrecisionEraser
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();

        g2d.setLineWidth(1);
        g2d.setStroke(this.color);
        g2d.setLineDashes(5, 3);

        double clearWidth = 5.0;
        double clearHeight = 5.0;

        for (int i = 0; i <= this.points.size() - 2; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i + 1);

            int steps = 20;
            for (int j = 0; j <= steps; j++) {
                double t = j / (double) steps;

                double x = p1.x * (1 - t) + p2.x * t;
                double y = p1.y * (1 - t) + p2.y * t;

                g2d.clearRect(x - clearWidth / 2, y - clearHeight / 2, clearWidth, clearHeight);
            }

            g2d.strokeRect(this.topLeft.x - dimension / 2.0, this.topLeft.y - dimension / 2.0,
                    this.dimension, this.dimension);

        }
    }

    /**
     * Sets the properties of the PrecisionEraser based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - x-coordinate of the top-left point
     *             data[1] - y-coordinate of the top-left point
     *             data[2] - dimension of the PreciseEraser
     *             data[3] - color of the PreciseEraser in web format
     *             data[4 and onwards] - points as pairs of x and y coordinates
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

    /**
     * Returns a string representation of the PrecisionEraser instance,
     * including its top-left coordinates, dimension, color, and the points
     * it contains in "x,y" format.
     *
     * @return a string representation of the PrecisionEraser instance
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
