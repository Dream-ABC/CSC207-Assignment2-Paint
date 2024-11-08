package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class StrokeEraserStrategy implements ShapeStrategy {
    private final PaintPanel panel;

    public StrokeEraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.panel.getModel().storeState();
        StrokeEraser strokeEraser = new StrokeEraser();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x, topLeft.y);
        strokeEraser.setTopLeft(centre);
        this.panel.setStrokeEraser(strokeEraser);
        this.panel.getModel().addStrokeEraser(strokeEraser);
        eraseDrawings();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        StrokeEraser strokeEraser = panel.getStrokeEraser();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x, topLeft.y);
        strokeEraser.setTopLeft(centre);
        this.panel.getModel().addStrokeEraser(strokeEraser);
        eraseDrawings();
    }

    private void eraseDrawings(){
        ArrayList<Shape> currLayer = new ArrayList<>(this.panel.getModel().getSelectedLayer().getShapes());
        for (Shape shape : currLayer) {
            if (shape.overlaps(this.panel.getStrokeEraser())) {
                this.panel.getStrokeEraser().addRemovedShapes(shape);
                this.panel.getModel().removeShape(shape);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.panel.getModel().getHistory().addToLast(this.panel.getStrokeEraser().getRemovedShapes());
        this.panel.getModel().removeStrokeEraser();
        this.panel.setStrokeEraser(null);
        this.panel.setCurrentShape(null);
    }
}
