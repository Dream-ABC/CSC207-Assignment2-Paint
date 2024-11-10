package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class OvalStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    public OvalStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());

            ShapeFactory shapeFactory = panel.getShapeFactory();
            Oval oval = (Oval) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
            this.panel.setCurrentShape(oval);

            oval.setOrigin(topLeft);
            oval.setTopLeft(topLeft);
            oval.setColor(this.panel.getColor());
            this.panel.getModel().addShape(oval);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Oval oval = (Oval) this.panel.getCurrentShape();
            double newWidth = Math.abs(oval.getOrigin().x - mouseEvent.getX());
            double newHeight = Math.abs(oval.getOrigin().y - mouseEvent.getY());
            oval.setWidth(newWidth);
            oval.setHeight(newHeight);
            double x = Math.min(oval.getOrigin().x, mouseEvent.getX());
            double y = Math.min(oval.getOrigin().y, mouseEvent.getY());
            oval.setTopLeft(new Point(x, y));

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(oval);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        {
            Oval oval = (Oval) this.panel.getCurrentShape();
            double newWidth = Math.abs(oval.getOrigin().x - mouseEvent.getX());
            double newHeight = Math.abs(oval.getOrigin().y - mouseEvent.getY());
            oval.setWidth(newWidth);
            oval.setHeight(newHeight);
            double x = Math.min(oval.getOrigin().x, mouseEvent.getX());
            double y = Math.min(oval.getOrigin().y, mouseEvent.getY());
            oval.setTopLeft(new Point(x, y));

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(oval);
            this.panel.setCurrentShape(null);
        }
    }
}
