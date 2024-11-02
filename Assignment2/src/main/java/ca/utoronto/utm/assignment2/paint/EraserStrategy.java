package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class EraserStrategy implements ShapeStrategy{
    private PaintPanel panel;

    public EraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Started Eraser");
        Point centre = new Point(mouseEvent.getX(), mouseEvent.getY());
        Eraser eraser = new Eraser(centre);
        this.panel.setEraser(eraser);

        // Set info of rectangle (radius=0)
        rectangle.setTopLeft(topLeft);
        rectangle.setOrigin(origin);
        this.panel.getModel().addShape(rectangle);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) panel.getCurrentShape();
        Point origin = rectangle.getOrigin();

        double width = Math.abs(origin.x - mouseEvent.getX());
        double height = Math.abs(origin.y - mouseEvent.getY());
        double x = Math.min(origin.x, mouseEvent.getX());
        double y = Math.min(origin.y, mouseEvent.getY());
        rectangle.setTopLeft(new Point(x, y));
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        // this.panel.getModel().addRectangle(rectangle);
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(rectangle);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) panel.getCurrentShape();
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
            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(rectangle);
            System.out.println("Added Rectangle");
            this.panel.setCurrentShape(null);
        }
    }
}
