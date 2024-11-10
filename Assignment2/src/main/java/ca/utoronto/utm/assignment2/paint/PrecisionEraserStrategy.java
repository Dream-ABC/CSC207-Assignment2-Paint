package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A class to represent the precision eraser erasing strategy.
 * PrecisionEraserStrategy implements the ShapeStrategy interface.
 */
public class PrecisionEraserStrategy implements ShapeStrategy {
    private final PaintPanel panel;

    /**
     * Creates a precision eraser strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public PrecisionEraserStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user performs a left mouse click, a new PrecisionEraser
     * is created and begins erasing at the current mouse position,
     * corresponding to the size of the precision eraser's square.
     * This is done by drawing transparent-colored squares the size of the precision eraser,
     * at the PrecisionEraser's position (current mouse position).
     * The centre of the PrecisionEraser is defined by the current mouse position.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            this.panel.getModel().storeState();
            this.panel.getModel().getHistory().popLastCommand();

            ShapeFactory shapeFactory = panel.getShapeFactory();
            PrecisionEraser precisionEraser = (PrecisionEraser) shapeFactory.getShape(panel.getMode(),
                    panel.getFillStyle(), panel.getLineThickness());

            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point centre = new Point(topLeft.x, topLeft.y);
            precisionEraser.setTopLeft(centre);
            precisionEraser.addPoint(topLeft);
            this.panel.setCurrentShape(precisionEraser);
            this.panel.getModel().addShape(precisionEraser);
        }
    }

    /**
     * When the user performs a left mouse drag, the PrecisionEraser
     * continues erasing according to the current mouse position,
     * corresponding to the size of the precision eraser's square.
     * This is done by drawing transparent-colored squares the size of the precision eraser,
     * at the PrecisionEraser's position (current mouse position).
     * The centre of the PrecisionEraser is defined by the current mouse position.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            PrecisionEraser precisionEraser = (PrecisionEraser) this.panel.getCurrentShape();
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point centre = new Point(topLeft.x, topLeft.y);
            precisionEraser.setTopLeft(centre);
            precisionEraser.addPoint(topLeft);
            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(precisionEraser);
        }
    }

    /**
     * When the user performs a left mouse release, the PrecisionEraser disappears from the screen.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            PrecisionEraser precisionEraser = (PrecisionEraser) this.panel.getCurrentShape();
            if (precisionEraser != null) {
                Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
                precisionEraser.addPoint(topLeft);
                Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
                this.panel.getModel().getSelectedLayer().removeShape(shape);
                precisionEraser.setColor(Color.TRANSPARENT);
                this.panel.getModel().addShapeFinal(precisionEraser);
                this.panel.setCurrentShape(null);
            }
        }
    }
}
