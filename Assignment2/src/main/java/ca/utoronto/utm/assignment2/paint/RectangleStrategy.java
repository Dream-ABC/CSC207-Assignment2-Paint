package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class RectangleStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    public RectangleStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point origin = new Point(topLeft.x, topLeft.y);

            // Create a rectangle using factory
            ShapeFactory shapeFactory = panel.getShapeFactory();
            Rectangle rectangle = (Rectangle) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
            this.panel.setCurrentShape(rectangle);

            // Set info of rectangle (radius=0)
            rectangle.setTopLeft(topLeft);
            rectangle.setOrigin(origin);
            rectangle.setColor(this.panel.getColor());
            this.panel.getModel().addShape(rectangle);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Rectangle rectangle = (Rectangle) panel.getCurrentShape();
            Point origin = rectangle.getOrigin();

            double width = Math.abs(origin.x - mouseEvent.getX());
            double height = Math.abs(origin.y - mouseEvent.getY());
            double x = Math.min(origin.x, mouseEvent.getX());
            double y = Math.min(origin.y, mouseEvent.getY());
            rectangle.setTopLeft(new Point(x, y));
            rectangle.setWidth(width);
            rectangle.setHeight(height);

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(rectangle);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Rectangle rectangle = (Rectangle) panel.getCurrentShape();
            Point origin = rectangle.getOrigin();

            double width = Math.abs(origin.x - mouseEvent.getX());
            double height = Math.abs(origin.y - mouseEvent.getY());
            double x = Math.min(rectangle.getTopLeft().x, mouseEvent.getX());
            double y = Math.min(rectangle.getTopLeft().y, mouseEvent.getY());
            rectangle.setTopLeft(new Point(x, y));
            rectangle.setWidth(width);
            rectangle.setHeight(height);

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(rectangle);
            this.panel.setCurrentShape(null);
        }
    }
}
