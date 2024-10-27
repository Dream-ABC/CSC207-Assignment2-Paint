package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class RectangleStrategy implements ShapeStrategy {

    private PaintPanel panel;

    public RectangleStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Started Rectangle");
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point origin = new Point(topLeft.x, topLeft.y);

        // Create a rectangle using factory
        ShapeFactory shapeFactory = panel.getShapeFactory();
        Rectangle rectangle = (Rectangle) shapeFactory.getShape(panel.getMode());
        this.panel.setShape(rectangle);

        // Set info of rectangle (radius=0)
        rectangle.setTopLeft(topLeft);
        rectangle.setOrigin(origin);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) panel.getShape();
        Point origin = rectangle.getOrigin();

        double width = Math.abs(origin.x - mouseEvent.getX());
        double height = Math.abs(origin.y - mouseEvent.getY());
        double x = Math.min(origin.x, mouseEvent.getX());
        double y = Math.min(origin.y, mouseEvent.getY());
        rectangle.setTopLeft(new Point(x, y));
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        // this.panel.getModel().addRectangle(rectangle);
        this.panel.getModel().addShape(rectangle);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) panel.getShape();
        Point origin = rectangle.getOrigin();

        if (rectangle != null) {
            double width = Math.abs(origin.x - mouseEvent.getX());
            double height = Math.abs(origin.y - mouseEvent.getY());
            double x = Math.min(rectangle.getTopLeft().x, mouseEvent.getX());
            double y = Math.min(rectangle.getTopLeft().y, mouseEvent.getY());
            rectangle.setTopLeft(new Point(x, y));
            rectangle.setWidth(width);
            rectangle.setHeight(height);
            // this.panel.getModel().addRectangle(rectangle);
            this.panel.getModel().addShape(rectangle);
            System.out.println("Added Rectangle");
            this.panel.setShape(null);
        }
    }
}
