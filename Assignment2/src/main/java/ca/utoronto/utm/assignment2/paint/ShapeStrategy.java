package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public interface ShapeStrategy {
    void mousePressed(MouseEvent mouseEvent);
    void mouseDragged(MouseEvent mouseEvent);
    void mouseReleased(MouseEvent mouseEvent);
    void mouseClicked(MouseEvent mouseEvent);
}
