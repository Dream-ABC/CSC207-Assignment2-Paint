package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Eraser {
    private Point centre;
    private int dimension;
    private ArrayList<Shape> removedShapes;

    public Eraser(){
        this.dimension = 14;
        this.removedShapes = new ArrayList<>();
    }
    public Eraser(Point centre){
        this.centre = centre;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public int getDimension(){ return this.dimension; }

    public void addRemovedShapes(Shape shape){
        this.removedShapes.add(shape);
    }
    public ArrayList<Shape> getRemovedShapes(){
        return this.removedShapes;
    }

    public void display(GraphicsContext g2d) {
        g2d.strokeRect(this.centre.x, this.centre.y,
                this.dimension, this.dimension);
    }
}
