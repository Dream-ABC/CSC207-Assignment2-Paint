package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Shape {
    Color getColor();
    int getThickness();
    double getTransparency();
    void setTransparency(int transparency);
    String getShape();
    void display(GraphicsContext g2d);
}
