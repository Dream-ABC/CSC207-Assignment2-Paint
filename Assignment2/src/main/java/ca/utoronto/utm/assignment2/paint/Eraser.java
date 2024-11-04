package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

/**
 * A class to represent an eraser.
 * Erasers are represented as a square, where the user's mouse is the centre of the square.
 */
public class Eraser {
    private Point centre;
    private int dimension;

    /**
     * Constructs a default eraser, represented as a square that is of size 14.
     */
    public Eraser(){
        this.dimension = 14;
    }

    /**
     * Constructs an eraser, represented as a square that is of size 14, at a user-specified centre.
     */
    public Eraser(Point centre){
        this.dimension = 14;
        this.centre = centre;
    }

    /**
     * @return the centre of the Eraser
     */
    public Point getCentre() {
        return centre;
    }

    /**
     * Sets the centre of the Eraser.
     * @param centre
     */
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    /**
     * @return the dimension of the Eraser
     */
    public int getDimension(){ return this.dimension; }

    /**
     * Displays the Eraser, represented as a square, centered at the user's current mouse location.
     * @param g2d GraphicsContext
     */
    public void display(GraphicsContext g2d) {
        g2d.strokeRect(this.centre.x, this.centre.y,
                this.dimension, this.dimension);
    }
}
