package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An interface for all Shapes that users can draw.
 */
public interface Shape {
    void setColor(Color color);
    Color getColor();
    void setLineThickness(double lineThickness);
    boolean overlaps(Tool tool);
    void display(GraphicsContext g2d);
    void setShape(String[] data);
    void shift(double x, double y);
    Shape copy();
}
