package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class PrecisionEraserStrategy implements ShapeStrategy {
    private PaintPanel panel;

    public PrecisionEraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.panel.getModel().storeState();
        PrecisionEraser precisionEraser = new PrecisionEraser();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x, topLeft.y);
        precisionEraser.setTopLeft(centre);
        precisionEraser.addPoint(topLeft);
        this.panel.setCurrentShape(precisionEraser);
        this.panel.getModel().addShape(precisionEraser);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        PrecisionEraser precisionEraser = (PrecisionEraser) this.panel.getCurrentShape();
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point centre = new Point(topLeft.x, topLeft.y);
        precisionEraser.setTopLeft(centre);
        precisionEraser.addPoint(topLeft);
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(precisionEraser);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        PrecisionEraser precisionEraser = (PrecisionEraser) this.panel.getCurrentShape();
        if(precisionEraser!=null) {
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            precisionEraser.addPoint(topLeft);
            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(precisionEraser);
            this.panel.setCurrentShape(null);
        }
    }
}
