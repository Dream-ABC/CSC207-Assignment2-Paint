package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A class to represent the triangle drawing strategy.
 * TriangleStrategy implements the ShapeStrategy interface.
 */
public class TriangleStrategy implements ShapeStrategy {
    
    private final PaintPanel panel;

    /**
     * Creates a triangle strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public TriangleStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user performs a left mouse click, a new Triangle is created.
     * The mouse click position is the top left corner of the bounding box of the Triangle.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point origin = new Point(topLeft.x, topLeft.y);

            ShapeFactory shapeFactory = panel.getShapeFactory();
            Triangle triangle = (Triangle) shapeFactory.getShape(panel.getMode(), panel.getFillStyle(), panel.getLineThickness());
            this.panel.setCurrentShape(triangle);

            triangle.setOrigin(origin);
            triangle.setTopLeft(topLeft);
            triangle.updatePoints();
            triangle.setColor(this.panel.getColor());
            this.panel.getModel().addShape(triangle);
        }
    }

    /**
     * When the user performs a left mouse drag, the current Triangle's size is updated according to
     * the user's mouse position.
     * The drag position is the bottom-right vertex of the Triangle.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Triangle triangle = (Triangle) this.panel.getCurrentShape();
            double newWidth = Math.abs(triangle.getOrigin().x - mouseEvent.getX());
            double newHeight = Math.abs(triangle.getOrigin().y - mouseEvent.getY());
            triangle.setBase(newWidth);
            triangle.setHeight(newHeight);
            double x = Math.min(triangle.getOrigin().x, mouseEvent.getX());
            double y = Math.min(triangle.getOrigin().y, mouseEvent.getY());
            triangle.setTopLeft(new Point(x, y));
            triangle.updatePoints();

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShape(triangle);
        }
    }

    /**
     * When the user performs a left mouse release, the final Triangle is drawn onto the canvas.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Triangle triangle = (Triangle) this.panel.getCurrentShape();

            double newWidth = Math.abs(triangle.getOrigin().x - mouseEvent.getX());
            double newHeight = Math.abs(triangle.getOrigin().y - mouseEvent.getY());
            triangle.setBase(newWidth);
            triangle.setHeight(newHeight);
            double x = Math.min(triangle.getTopLeft().x, mouseEvent.getX());
            double y = Math.min(triangle.getTopLeft().y, mouseEvent.getY());
            triangle.setTopLeft(new Point(x, y));
            triangle.updatePoints();

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(triangle);
            this.panel.resetCurrentShape();
        }
    }
}
