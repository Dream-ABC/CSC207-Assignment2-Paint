package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

public interface Tool {
    public Point getCentre();
    public void setCentre(Point centre);
    public double getDimensionX();
    public double getDimensionY();
    public void display(GraphicsContext g2d);
}
