package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class OvalStrategy implements ShapeStrategy {

    private PaintPanel panel;

    public OvalStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());

        System.out.println("Started Oval");

        // Create a circle using factory
        ShapeFactory shapeFactory = panel.getShapeFactory();
        Oval oval = (Oval) shapeFactory.getShape(panel.getMode());
        this.panel.setShape(oval);

        // Set info of circle (radius=0)
        oval.setOrigin(topLeft);
        oval.setTopLeft(topLeft);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Oval oval = (Oval) this.panel.getShape();
        double newWidth = Math.abs(oval.getOrigin().x-mouseEvent.getX());
        double newHeight = Math.abs(oval.getOrigin().y-mouseEvent.getY());
        oval.setWidth(newWidth);
        oval.setHeight(newHeight);
        double x = Math.min(oval.getOrigin().x, mouseEvent.getX());
        double y = Math.min(oval.getOrigin().y, mouseEvent.getY());
        oval.setTopLeft(new Point(x, y));
        // this.panel.getModel().addOval(circle);
        this.panel.getModel().addShape(oval);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Oval oval = (Oval) this.panel.getShape();
        double newWidth = Math.abs(oval.getOrigin().x-mouseEvent.getX());
        double newHeight = Math.abs(oval.getOrigin().y-mouseEvent.getY());
        oval.setWidth(newWidth);
        oval.setHeight(newHeight);
        double x = Math.min(oval.getOrigin().x, mouseEvent.getX());
        double y = Math.min(oval.getOrigin().y, mouseEvent.getY());
        oval.setTopLeft(new Point(x, y));
        // this.panel.getModel().addOval(circle);
        this.panel.getModel().addShape(oval);
        System.out.println("Added Oval");
        this.panel.setShape(null);
    }
}
