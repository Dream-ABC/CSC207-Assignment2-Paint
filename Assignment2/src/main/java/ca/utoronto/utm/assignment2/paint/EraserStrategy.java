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
        System.out.println("Started Eraser");
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
        System.out.println("x:" + eraser.getCentre().x + " y:" + eraser.getCentre().y);
        eraseDrawings();
    }

    private void eraseDrawings(){
        PaintLayer currLayer = this.panel.getModel().getSelectedLayer();
        ArrayList<Shape> removeShapes = new ArrayList<Shape>();
        for (Shape shape : currLayer.getShapes()) {
            if (shape.overlaps(this.panel.getEraser())) {
                removeShapes.add(shape);
            }
        }
        for (Shape shape: removeShapes){
            currLayer.removeShape(shape);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.panel.getModel().removeEraser();
        this.panel.setEraser(null);
    }
}
