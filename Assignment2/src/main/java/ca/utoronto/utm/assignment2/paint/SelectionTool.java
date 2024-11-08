package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SelectionTool implements Tool {

    private Point topLeft;
    private Point origin;
    private double dimensionX;
    private double dimensionY;
    private final ArrayList<Shape> selectedShapes;


    /**
     * Constructs a default black rectangle with a width and height of 0.
     */
    public SelectionTool() {
        this.dimensionX = 0;
        this.dimensionY = 0;
        selectedShapes = new ArrayList<>();
    }

    public Point getTopLeft(){
        return this.topLeft;
    }
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }
    public Point getOrigin(){
        return this.origin;
    }
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public double getDimensionX(){
        return this.dimensionX;
    }
    public void setDimensionX(double dimensionX) {
        this.dimensionX = dimensionX;
    }
    public double getDimensionY(){
        return this.dimensionY;
    }
    public void setDimensionY(double dimensionY) {
        this.dimensionY = dimensionY;
    }
    public ArrayList<Shape> getSelectedShapes() {
        return selectedShapes;
    }
    public void addSelectedShape(Shape shape) {
        this.selectedShapes.add(shape);
    }
    public void removeSelectedShape(Shape shape) {
        this.selectedShapes.remove(shape);
    }

    /**
     * Displays the Rectangle with user-created color and size.
     * @param g2d GraphicsContext
     */
    public void display(GraphicsContext g2d) {
        g2d.setLineWidth(1);
        g2d.setStroke(Color.BLACK);
        g2d.strokeRect(this.topLeft.x - dimensionX/2, this.topLeft.y-dimensionY/2,
                this.dimensionX, this.dimensionY);
    }
}
