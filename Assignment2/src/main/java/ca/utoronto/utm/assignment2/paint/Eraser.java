package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/**
 * A class to represent an eraser.
 * Erasers are represented as a square, where the user's mouse is the centre of the square.
 */
public class Eraser {
    private Point centre;
    private int dimension;
    private ArrayList<Shape> removedShapes;

    /**
     * Constructs a default eraser, represented as a square that is of size 14.
     */
    public Eraser(){
        this.dimension = 14;
        this.removedShapes = new ArrayList<>();
    }

    /**
     * @return the centre of the Eraser
     */
    public Point getCentre() {
        return centre;
    }

    /**
     * @param centre centre of Eraser's square representation
     */
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    /**
     * @return the dimension of the Eraser
     */
    public int getDimension(){ return this.dimension; }

    public void addRemovedShapes(Shape shape){
        this.removedShapes.add(shape);
    }
    public ArrayList<Shape> getRemovedShapes(){
        return this.removedShapes;
    }

    /**
     * Displays the Eraser, represented as a square, centered at the user's current mouse location.
     * @param g2d GraphicsContext
     */
    public void display(GraphicsContext g2d) {
        g2d.strokeRect(this.centre.x, this.centre.y,
                this.dimension, this.dimension);
    }

    public void setEraser(String[] data) {
        this.centre = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.dimension = Integer.parseInt(data[2]);
    }

    /**
     * Returns a string representation of the Eraser object.
     * The format includes the centre coordinates and the dimension.
     *
     * @return a string representation of the Eraser object
     */
    public String toString() {
        return "Eraser{" + this.centre.x + "," + this.centre.y + "," + this.dimension + "}";
    }
}
