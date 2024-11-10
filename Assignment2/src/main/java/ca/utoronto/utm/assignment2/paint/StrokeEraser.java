package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent the stroke eraser.
 * StrokeEraser implements the Tool interface.
 */
public class StrokeEraser implements Tool {
    private Point topLeft;
    private final double dimensionX;
    private final double dimensionY;
    private final ArrayList<Shape> removedShapes;

    /**
     * Constructs a default StrokeEraser, which is a square of size 14.
     */
    public StrokeEraser() {
        this.dimensionX = 14;
        this.dimensionY = 14;
        this.removedShapes = new ArrayList<>();
    }

    /**
     * Returns the top left point of StrokeEraser
     * @return the top left point of StrokeEraser
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Sets the top left point of StrokeEraser
     * @param topLeft top left point of StrokeEraser
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Returns the dimension of the StrokeEraser along the x-axis.
     * @return the x-dimension
     */
    public double getDimensionX() {
        return this.dimensionX;
    }

    /**
     * Returns the dimension of the StrokeEraser along the y-axis.
     * @return the y-dimension
     */
    public double getDimensionY() {
        return this.dimensionY;
    }

    /**
     * Adds a new Shape to an ArrayList of all shapes that must be removed (because they've been erased).
     * @param shape the Shape that needs to be removed.
     */
    public void addRemovedShapes(Shape shape) {
        this.removedShapes.add(shape);
    }

    /**
     * Returns the ArrayList of all shapes that must be removed (because they've been erased).
     * @return an ArrayList of all the Shapes that need to be removed
     */
    public ArrayList<Shape> getRemovedShapes() {
        return this.removedShapes;
    }

    /**
     * Displays the StrokeEraser as a square with a black dashed outline.
     * The user's mouse position represents the centre of the StrokeEraser.
     * The default size of this eraser is 14.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the PrecisionEraser
     */
    public void display(GraphicsContext g2d) {
        g2d.setLineWidth(1);
        g2d.setStroke(Color.BLACK);
        g2d.setLineDashes(5, 3);
        g2d.strokeRect(this.topLeft.x - dimensionX / 2, this.topLeft.y - dimensionY / 2,
                this.dimensionX, this.dimensionY);
    }
}
