package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent an eraser.
 * Erasers are represented as a square, where the user's mouse is the topLeft of the square.
 */
public class StrokeEraser implements Tool {
    private Point topLeft;
    private final double dimensionX;
    private final double dimensionY;
    private final ArrayList<Shape> removedShapes;

    /**
     * Constructs a default stroke eraser, represented as a square that is of size 14.
     */
    public StrokeEraser() {
        this.dimensionX = 14;
        this.dimensionY = 14;
        this.removedShapes = new ArrayList<>();
    }

    /**
     * @return the topLeft of the Eraser
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * @param topLeft topLeft of Eraser's square representation
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the dimension of the Eraser
     */
    public double getDimensionX() {
        return this.dimensionX;
    }

    public double getDimensionY() {
        return this.dimensionY;
    }

    public void addRemovedShapes(Shape shape) {
        this.removedShapes.add(shape);
    }

    public ArrayList<Shape> getRemovedShapes() {
        return this.removedShapes;
    }

    /**
     * Displays the Eraser, represented as a square, centered at the user's current mouse location.
     *
     * @param g2d GraphicsContext
     */
    public void display(GraphicsContext g2d) {
        g2d.setLineWidth(1);
        g2d.setStroke(Color.BLACK);
        g2d.setLineDashes(5, 3);
        g2d.strokeRect(this.topLeft.x - dimensionX / 2, this.topLeft.y - dimensionY / 2,
                this.dimensionX, this.dimensionY);
    }
}
