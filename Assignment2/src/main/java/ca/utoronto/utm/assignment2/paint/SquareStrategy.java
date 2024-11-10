package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A class to represent the square drawing strategy.
 * SquareStrategy implements the ShapeStrategy interface.
 */
public class SquareStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    /**
     * Creates a square strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public SquareStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user does a left mouse click, a new Square is created.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point origin = new Point(topLeft.x, topLeft.y);

            ShapeFactory shapeFactory = panel.getShapeFactory();
            Square square = (Square) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
            this.panel.setCurrentShape(square);

            square.setOrigin(origin);
            square.setTopLeft(topLeft);
            square.setColor(this.panel.getColor());
            this.panel.getModel().addShape(square);
        }
    }

    /**
     * When the user does a left mouse drag, the current Square's size is updated according to
     * the user's mouse position.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
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
            } else if (x < origin.x) {
                square.setTopLeft(new Point(origin.x - square.getSize(), origin.y));
            } else if (y < origin.y) {
                square.setTopLeft(new Point(origin.x, origin.y - square.getSize()));
            } else {
                square.setTopLeft(new Point(origin.x, origin.y));
            }

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(square);
        }
    }

    /**
     * When the user does a left mouse release, the final Square is drawn onto the canvas.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
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
            } else if (x < origin.x) {
                square.setTopLeft(new Point(origin.x - square.getSize(), origin.y));
            } else if (y < origin.y) {
                square.setTopLeft(new Point(origin.x, origin.y - square.getSize()));
            } else {
                square.setTopLeft(new Point(origin.x, origin.y));
            }

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(square);
            this.panel.setCurrentShape(null);
        }
    }
}