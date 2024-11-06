package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class EraserStrategy implements ShapeStrategy {
    private PaintPanel panel;

    public EraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.panel.getModel().storeState();
        Eraser eraser = new Eraser();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x-(eraser.getDimension()/2.0), topLeft.y-(eraser.getDimension()/2.0));
        eraser.setCentre(centre);
        this.panel.setEraser(eraser);
        this.panel.getModel().addEraser(eraser);
        eraseDrawings();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Eraser eraser = panel.getEraser();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x-(eraser.getDimension()/2.0), topLeft.y-(eraser.getDimension()/2.0));
        eraser.setCentre(centre);
        this.panel.getModel().addEraser(eraser);
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
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }
}
