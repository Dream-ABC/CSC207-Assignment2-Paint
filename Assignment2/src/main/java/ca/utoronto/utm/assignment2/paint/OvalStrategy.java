package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A class to represent the oval drawing strategy.
 * OvalStrategy implements the ShapeStrategy interface.
 */
public class OvalStrategy implements ShapeStrategy {

    private final PaintPanel panel;

    /**
     * Creates an oval strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public OvalStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user performs a left mouse click, a new Oval is created.
     * The mouse click position is the top left corner of the bounding box of the Oval.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());

            ShapeFactory shapeFactory = panel.getShapeFactory();
            Oval oval = (Oval) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
            this.panel.setCurrentShape(oval);

            oval.setOrigin(topLeft);
            oval.setTopLeft(topLeft);
            oval.setColor(this.panel.getColor());
            this.panel.getModel().addShape(oval);
        }
    }

    /**
     * When the user performs a left mouse drag, the current Oval's size is updated according to
     * the user's mouse position.
     * The drag position is the bottom-right vertex of the bounding box of the Oval.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Oval oval = (Oval) this.panel.getCurrentShape();
            double newWidth = Math.abs(oval.getOrigin().x - mouseEvent.getX());
            double newHeight = Math.abs(oval.getOrigin().y - mouseEvent.getY());
            oval.setWidth(newWidth);
            oval.setHeight(newHeight);
            double x = Math.min(oval.getOrigin().x, mouseEvent.getX());
            double y = Math.min(oval.getOrigin().y, mouseEvent.getY());
            oval.setTopLeft(new Point(x, y));

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(oval);
        }
    }

    /**
     * When the user performs a left mouse release, the final Oval is drawn onto the canvas.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        {
            Oval oval = (Oval) this.panel.getCurrentShape();
            double newWidth = Math.abs(oval.getOrigin().x - mouseEvent.getX());
            double newHeight = Math.abs(oval.getOrigin().y - mouseEvent.getY());
            oval.setWidth(newWidth);
            oval.setHeight(newHeight);
            double x = Math.min(oval.getOrigin().x, mouseEvent.getX());
            double y = Math.min(oval.getOrigin().y, mouseEvent.getY());
            oval.setTopLeft(new Point(x, y));

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(oval);
            this.panel.resetCurrentShape();
        }
    }
}
