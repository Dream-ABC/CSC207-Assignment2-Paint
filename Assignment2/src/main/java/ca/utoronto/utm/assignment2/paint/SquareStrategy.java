package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class SquareStrategy implements ShapeStrategy {

    private PaintPanel panel;

    public SquareStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("Started Square");
        Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point origin = new Point(topLeft.x, topLeft.y);

        // Create a circle using factory
        ShapeFactory shapeFactory = panel.getShapeFactory();
        Square square = (Square) shapeFactory.getShape(panel.getMode());
        this.panel.setCurrentShape(square);

        // Set info of circle (radius=0)
        square.setOrigin(origin);
        square.setTopLeft(topLeft);
        square.setOpaqueness(this.panel.getOpaqueness());
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
        square.setSide(sideLength);

        if (x < origin.x && y < origin.y) {
            square.setTopLeft(new Point(origin.x - square.getSide(), origin.y - square.getSide()));
        }
        else if (x < origin.x) {
            square.setTopLeft(new Point(origin.x - square.getSide(), origin.y));
        }
        else if (y < origin.y){
            square.setTopLeft(new Point(origin.x, origin.y - square.getSide()));
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
        if(square!=null){
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            double width = Math.abs(origin.x - x);
            double height = Math.abs(origin.y - y);
            double sideLength = Math.min(width, height);
            square.setSide(sideLength);

            if (x < origin.x && y < origin.y) {
                square.setTopLeft(new Point(origin.x - square.getSide(), origin.y - square.getSide()));
            }
            else if (x < origin.x) {
                square.setTopLeft(new Point(origin.x - square.getSide(), origin.y));
            }
            else if (y < origin.y){
                square.setTopLeft(new Point(origin.x, origin.y - square.getSide()));
            }
            else {
                square.setTopLeft(new Point(origin.x, origin.y));
            }

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(square);
            System.out.println("Added Square");
            this.panel.setCurrentShape(null);
        }
    }
}