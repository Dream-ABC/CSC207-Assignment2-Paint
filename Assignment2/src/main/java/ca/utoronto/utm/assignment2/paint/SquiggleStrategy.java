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
        // this.panel.getModel().addPath(squiggle);
        this.panel.getModel().addShape(squiggle);
        squiggle.addPoint(point);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Squiggle squiggle = (Squiggle) this.panel.getCurrentShape();
        Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
        squiggle.addPoint(point);
        this.panel.getModel().notifyUpdate();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("Added Squiggle");
        this.panel.setCurrentShape(null);
    }
}