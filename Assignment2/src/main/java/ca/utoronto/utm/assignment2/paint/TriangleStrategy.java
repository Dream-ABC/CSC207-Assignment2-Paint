package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class TriangleStrategy implements ShapeStrategy {
    
    private PaintPanel panel;

    public TriangleStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Started Triangle");
        
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point origin = new Point(topLeft.x, topLeft.y);

        // Create a triangle using factory
        ShapeFactory shapeFactory = panel.getShapeFactory();
        Triangle triangle = (Triangle) shapeFactory.getShape(panel.getMode());
        this.panel.setCurrentShape(triangle);

        // Set info of triangle (radius=0)
        triangle.setOrigin(origin);
        triangle.setTopLeft(topLeft);
        triangle.updatePoints();
        this.panel.getModel().addShape(triangle);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        Triangle triangle = (Triangle) this.panel.getCurrentShape();
        double newWidth = Math.abs(triangle.getOrigin().x-mouseEvent.getX());
        double newHeight = Math.abs(triangle.getOrigin().y-mouseEvent.getY());
        triangle.setBase(newWidth);
        triangle.setHeight(newHeight);
        double x = Math.min(triangle.getOrigin().x, mouseEvent.getX());
        double y = Math.min(triangle.getOrigin().y, mouseEvent.getY());
        triangle.setTopLeft(new Point(x, y));
        triangle.updatePoints();

        // this.panel.getModel().addtriangle(triangle);
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(triangle);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Triangle triangle = (Triangle) this.panel.getCurrentShape();

        double newWidth = Math.abs(triangle.getOrigin().x-mouseEvent.getX());
        double newHeight = Math.abs(triangle.getOrigin().y-mouseEvent.getY());
        triangle.setBase(newWidth);
        triangle.setHeight(newHeight);
        double x = Math.min(triangle.getTopLeft().x, mouseEvent.getX());
        double y = Math.min(triangle.getTopLeft().y, mouseEvent.getY());
        triangle.setTopLeft(new Point(x, y));
        triangle.updatePoints();

        // this.panel.getModel().addtriangle(triangle);
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(triangle);
        System.out.println("Added Triangle");
        this.panel.setCurrentShape(null);
    }
}
