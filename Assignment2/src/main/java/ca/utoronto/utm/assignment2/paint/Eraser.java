package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/**
 * A class to represent an eraser.
 * Erasers are represented as a square, where the user's mouse is the centre of the square.
 */
public class Eraser implements Tool{
    private Point centre;
    private double dimensionX;
    private double dimensionY;
    private ArrayList<Shape> removedShapes;

    /**
     * Constructs a default eraser, represented as a square that is of size 14.
     */
    public Eraser(){
        this.dimensionX = 14;
        this.dimensionY = 14;
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
    public double getDimensionX(){ return this.dimensionX; }
    public double getDimensionY(){ return this.dimensionY; }

    /**
     * @return the left x-coordinate of the Eraser
     */
    public double getLeftX(){ return this.centre.x-(this.dimension/2.0);}
    /**
     * @return the right x-coordinate of the Eraser
     */
    public double getRightX(){ return this.centre.x+(this.dimension/2.0); }
    /**
     * @return the top y-coordinate of the Eraser
     */
    public double getTopY(){ return this.centre.y-(this.dimension/2.0); }
    /**
     * @return the bottom y-coordinate of the Eraser
     */
    public double getBottomY() { return this.centre.y+(this.dimension/2.0); }

    /**
     * @return an ArrayList of all the corner points and centre point of an Eraser
     */
    public ArrayList<Point> getAllPoints(){
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(this.getLeftX(), this.getTopY()));
        allPoints.add(new Point(this.getLeftX(), this.getBottomY()));
        allPoints.add(new Point(this.getRightX(), this.getTopY()));
        allPoints.add(new Point(this.getRightX(), this.getBottomY()));
        allPoints.add(this.centre);
        return allPoints;
    }

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
        g2d.strokeRect(this.centre.x - dimensionX/2, this.centre.y - dimensionY/2,
                this.dimensionX, this.dimensionY);
    }
}
