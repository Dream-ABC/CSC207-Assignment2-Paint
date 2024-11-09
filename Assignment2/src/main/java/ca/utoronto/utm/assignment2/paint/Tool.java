package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

public interface Tool {
    Point getTopLeft();
    void setTopLeft(Point topLeft);
    double getDimensionX();
    double getDimensionY();
    void display(GraphicsContext g2d);
}
