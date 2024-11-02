package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Eraser {
    private Point centre;

    public Eraser(Point centre){
        this.centre = centre;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public void display(GraphicsContext g2d) {
        System.out.println(this.centre.x + " " + this.centre.y);
        g2d.strokeOval(this.centre.x, this.centre.y,
                14, 14);
    }
}
