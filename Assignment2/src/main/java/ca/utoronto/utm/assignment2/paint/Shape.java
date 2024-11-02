package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Shape {
    Color getColor();
    int getThickness();
    String getShape();
    boolean overlaps(Eraser eraser);
    void display(GraphicsContext g2d);
}
