package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class SquareStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    public SquareStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point origin = new Point(topLeft.x, topLeft.y);

        // Create a circle using factory
        ShapeFactory shapeFactory = panel.getShapeFactory();
        Square square = (Square) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
        this.panel.setCurrentShape(square);

        // Set info of circle (radius=0)
        square.setOrigin(origin);
        square.setTopLeft(topLeft);
        square.setColor(this.panel.getColor());
        this.panel.getModel().addShape(square);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Square square = (Square) this.panel.getCurrentShape();
        Point origin = square.getOrigin();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double width = Math.abs(origin.x - x);
        double height = Math.abs(origin.y - y);
        double sideLength = Math.min(width, height);
        square.setSize(sideLength);

        if (x < origin.x && y < origin.y) {
            square.setTopLeft(new Point(origin.x - square.getSize(), origin.y - square.getSize()));
        }
        else if (x < origin.x) {
            square.setTopLeft(new Point(origin.x - square.getSize(), origin.y));
        }
        else if (y < origin.y){
            square.setTopLeft(new Point(origin.x, origin.y - square.getSize()));
        }
        else {
            square.setTopLeft(new Point(origin.x, origin.y));
        }

        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(square);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Square square = (Square) this.panel.getCurrentShape();
        Point origin = square.getOrigin();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double width = Math.abs(origin.x - x);
        double height = Math.abs(origin.y - y);
        double sideLength = Math.min(width, height);
        square.setSize(sideLength);

        if (x < origin.x && y < origin.y) {
            square.setTopLeft(new Point(origin.x - square.getSize(), origin.y - square.getSize()));
        }
        else if (x < origin.x) {
            square.setTopLeft(new Point(origin.x - square.getSize(), origin.y));
        }
        else if (y < origin.y){
            square.setTopLeft(new Point(origin.x, origin.y - square.getSize()));
        }
        else {
            square.setTopLeft(new Point(origin.x, origin.y));
        }

        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShapeFinal(square);
        this.panel.setCurrentShape(null);
    }
}