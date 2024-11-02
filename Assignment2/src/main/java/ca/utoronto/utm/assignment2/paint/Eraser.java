package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

public class Eraser {
    private Point centre;
    private int dimension;

    public Eraser(){
        this.dimension = 14;
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

    public void display(GraphicsContext g2d) {
        g2d.strokeRect(this.centre.x, this.centre.y,
                this.dimension, this.dimension);
    }
}
