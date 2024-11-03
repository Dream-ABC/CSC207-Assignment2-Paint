package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class SquiggleStrategy implements ShapeStrategy {

    private PaintPanel panel;

    public SquiggleStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Start Squiggle");
        Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

        ShapeFactory shapeFactory = panel.getShapeFactory();
        Squiggle squiggle = (Squiggle) shapeFactory.getShape(panel.getMode());
        this.panel.setCurrentShape(squiggle);

        // Set info of squiggle (start point)
        squiggle.addPoint(point);
        squiggle.setColor(this.panel.getColor());
        this.panel.getModel().addShape(squiggle);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Squiggle squiggle = (Squiggle) this.panel.getCurrentShape();
        Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
        squiggle.addPoint(point);
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(squiggle);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Squiggle squiggle = (Squiggle) this.panel.getCurrentShape();
        Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
        squiggle.addPoint(point);
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(squiggle);
        System.out.println("Added Squiggle");
        this.panel.setCurrentShape(null);
    }
}