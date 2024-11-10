package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

/**
 * A class to represent the stroke eraser erasing strategy.
 * PrecisionEraserStrategy implements the ShapeStrategy interface.
 */
public class StrokeEraserStrategy implements ShapeStrategy {
    private final PaintPanel panel;

    /**
     * Creates a stroke eraser strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public StrokeEraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user performs a left mouse click, a new StrokeEraser
     * is created, with the current mouse position representing the centre
     * of the StrokeEraser.
     * If the StrokeEraser is overlapping any shapes on the current canvas layer, then
     * those shapes will be erased.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            this.panel.getModel().storeState();
            StrokeEraser strokeEraser = new StrokeEraser();
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point centre = new Point(topLeft.x, topLeft.y);
            strokeEraser.setTopLeft(centre);
            this.panel.setStrokeEraser(strokeEraser);
            this.panel.getModel().addStrokeEraser(strokeEraser);
            eraseDrawings();
        }
    }

    /**
     * When the user performs a left mouse drag, the current mouse position represents the centre
     * of the StrokeEraser.
     * If the StrokeEraser is overlapping any shapes on the current canvas layer, then
     * those shapes will be erased.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getStrokeEraser() != null) {
            StrokeEraser strokeEraser = panel.getStrokeEraser();
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point centre = new Point(topLeft.x, topLeft.y);
            strokeEraser.setTopLeft(centre);
            this.panel.getModel().addStrokeEraser(strokeEraser);
            eraseDrawings();
        }
    }

    /**
     * When the user performs a left mouse release, the StrokeEraser disappears from the screen.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            this.panel.getModel().getHistory().addToLast(this.panel.getStrokeEraser().getRemovedShapes());
            this.panel.getModel().removeStrokeEraser();
            this.panel.setStrokeEraser(null);
            this.panel.setCurrentShape(null);
        }
    }

    /**
     * This method erases any drawings that the StrokeEraser overlaps on the current canvas layer.
     */
    private void eraseDrawings(){
        ArrayList<Shape> currLayer = new ArrayList<>(this.panel.getModel().getSelectedLayer().getShapes());
        for (Shape shape : currLayer) {
            if (shape.overlaps(this.panel.getStrokeEraser())) {
                this.panel.getStrokeEraser().addRemovedShapes(shape);
                this.panel.getModel().removeShape(shape);
            }
        }
    }
}
