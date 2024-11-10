package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * An interface for all shapes strategies.
 * Each Shape has a ShapeStrategy.
 */
public interface ShapeStrategy {
    void mousePressed(MouseEvent mouseEvent);
    void mouseDragged(MouseEvent mouseEvent);
    void mouseReleased(MouseEvent mouseEvent);
}
