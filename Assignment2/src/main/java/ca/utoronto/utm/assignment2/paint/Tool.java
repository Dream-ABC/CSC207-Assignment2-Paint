package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

/**
 * An interface for all tools the Paint application has.
 */
public interface Tool {
    Point getTopLeft();
    void setTopLeft(Point topLeft);
    double getDimensionX();
    double getDimensionY();
    void display(GraphicsContext g2d);
}
