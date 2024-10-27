package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public interface ShapeStrategy {
    public void mousePressed(MouseEvent mouseEvent);
    public void mouseDragged(MouseEvent mouseEvent);
    public void mouseReleased(MouseEvent mouseEvent);
}
