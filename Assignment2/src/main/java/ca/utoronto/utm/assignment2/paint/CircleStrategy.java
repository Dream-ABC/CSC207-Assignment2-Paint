package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class CircleStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    public CircleStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {

            Point firstClick = new Point(mouseEvent.getX(), mouseEvent.getY());

            ShapeFactory shapeFactory = panel.getShapeFactory();
            Circle circle = (Circle) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
            this.panel.setCurrentShape(circle);

            circle.setCentre(firstClick);
            circle.setTopLeft(firstClick);
            circle.setColor(this.panel.getColor());
            this.panel.getModel().addShape(circle);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Circle circle = (Circle) this.panel.getCurrentShape();
            double dx = circle.getCentre().x - mouseEvent.getX();
            double dy = circle.getCentre().y - mouseEvent.getY();
            double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            double newX = circle.getCentre().x - radius;
            double newY = circle.getCentre().y - radius;
            Point newTopLeft = new Point(newX, newY);
            circle.setTopLeft(newTopLeft);
            circle.setDiameter(radius * 2);
            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(circle);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Circle circle = (Circle) this.panel.getCurrentShape();
            if (circle != null) {
                double dx = circle.getCentre().x - mouseEvent.getX();
                double dy = circle.getCentre().y - mouseEvent.getY();
                double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                double newX = circle.getCentre().x - radius;
                double newY = circle.getCentre().y - radius;
                Point newTopLeft = new Point(newX, newY);
                circle.setTopLeft(newTopLeft);
                circle.setDiameter(radius * 2);
                Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
                this.panel.getModel().getSelectedLayer().removeShape(shape);
                this.panel.getModel().addShapeFinal(circle);
                this.panel.setCurrentShape(null);
            }
        }
    }
}
