package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

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
        g2d.fillOval(this.centre.x, this.centre.y,
                50, 50);
    }
}
