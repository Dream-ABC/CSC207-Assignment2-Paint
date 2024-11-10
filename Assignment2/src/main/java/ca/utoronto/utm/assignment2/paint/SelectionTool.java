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
    private Point oldLocation;
    private boolean dragging;


    /**
     * Constructs a default black rectangle with a width and height of 0.
     */
    public SelectionTool() {
        this.dimensionX = 0;
        this.dimensionY = 0;
        selectedShapes = new ArrayList<>();
        dragging = false;
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
    public Point getOldLocation() {return oldLocation;}
    public void setOldLocation(Point oldLocation) {this.oldLocation = oldLocation;}
    public void shift(double x, double y) {
        this.topLeft.shift(x, y);
        for (Shape shape : selectedShapes) {
            shape.shift(x, y);
        }
    }
    public boolean inBounds(double x, double y) {
        boolean a = this.topLeft.x - dimensionX/2.0 <= x && x <= this.topLeft.x + dimensionX/2.0;
        boolean b = this.topLeft.y - dimensionY/2.0 <= y && y <= this.topLeft.y + dimensionY/2.0;
        return a && b;
    }

    public void clearSelectedShapes() {
        this.selectedShapes.clear();
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
    public boolean getDragging() {
        return this.dragging;
    }

    /**
     * Displays the Rectangle with user-created color and size.
     * @param g2d GraphicsContext
     */
    public void display(GraphicsContext g2d) {
        g2d.setLineWidth(1);
        g2d.setStroke(Color.BLACK);
        g2d.strokeRect(this.topLeft.x - dimensionX/2.0, this.topLeft.y-dimensionY/2.0,
                this.dimensionX, this.dimensionY);
    }
}
