package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class EraserStrategy implements ShapeStrategy {
    private PaintPanel panel;

    public EraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Started Eraser");
        Point centre = new Point(mouseEvent.getX()-7, mouseEvent.getY()-7);
        Eraser eraser = new Eraser(centre);
        this.panel.setEraser(eraser);
        this.panel.getModel().addEraser(eraser);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Eraser eraser = panel.getEraser();
        Point centre = new Point(mouseEvent.getX()-7, mouseEvent.getY()-7);
        eraser.setCentre(centre);
        this.panel.getModel().addEraser(eraser);


    }

    private void eraseThings(){
        PaintLayer currLayer = this.panel.getModel().getSelectedLayer();
        for (Shape shape : currLayer.getShapes()) {

        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.panel.getModel().removeEraser();
        this.panel.setEraser(null);
    }
}
