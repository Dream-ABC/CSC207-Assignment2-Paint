package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A class to represent the squiggle drawing strategy.
 * SquiggleStrategy implements the ShapeStrategy interface.
 */
public class SquiggleStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    /**
     * Creates a squiggle strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public SquiggleStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user performs a left mouse click, a new Squiggle is created.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());

            ShapeFactory shapeFactory = panel.getShapeFactory();
            Squiggle squiggle = (Squiggle) shapeFactory.getShape(panel.getMode(), "", panel.getLineThickness());
            this.panel.setCurrentShape(squiggle);

            // Set info of squiggle (start point)
            squiggle.addPoint(point);
            squiggle.setColor(this.panel.getColor());
            this.panel.getModel().addShape(squiggle);
        }
    }

    /**
     * When the user performs a left mouse drag, the current Squiggle's line is updated according to
     * the user's mouse position.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Squiggle squiggle = (Squiggle) this.panel.getCurrentShape();
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
            squiggle.addPoint(point);
            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(squiggle);
        }
    }

    /**
     * When the user performs a left mouse release, the final Squiggle is drawn onto the canvas.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Squiggle squiggle = (Squiggle) this.panel.getCurrentShape();
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
            squiggle.addPoint(point);
            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(squiggle);
            this.panel.setCurrentShape(null);
        }
    }
}