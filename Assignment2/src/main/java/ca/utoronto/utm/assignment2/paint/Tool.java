package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

public interface Tool {
    public Point getTopLeft();
    public void setTopLeft(Point topLeft);
    public double getDimensionX();
    public double getDimensionY();
    public void display(GraphicsContext g2d);
}
