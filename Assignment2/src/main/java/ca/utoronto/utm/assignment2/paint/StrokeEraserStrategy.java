package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class StrokeEraserStrategy implements ShapeStrategy {
    private PaintPanel panel;

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
        StrokeEraser strokeEraser = panel.getEraser();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x, topLeft.y);
        strokeEraser.setTopLeft(centre);
        this.panel.getModel().addEraser(strokeEraser);
        eraseDrawings();
    }

    private void eraseDrawings(){
        ArrayList<Shape> currLayer = new ArrayList<>(this.panel.getModel().getSelectedLayer().getShapes());
        for (Shape shape : currLayer) {
            if (shape.overlaps(this.panel.getEraser())) {
                this.panel.getEraser().addRemovedShapes(shape);
                this.panel.getModel().removeShape(shape);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.panel.getModel().getHistory().addToLast(this.panel.getEraser().getRemovedShapes());
        this.panel.getModel().removeEraser();
        this.panel.setEraser(null);
        this.panel.setCurrentShape(null);
    }
}
