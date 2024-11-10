package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.awt.geom.Point2D;

/**
 * A class to represent the polyline drawing strategy.
 * PolylineStrategy implements the ShapeStrategy interface.
 */
public class PolylineStrategy implements ShapeStrategy {
    private final PaintPanel panel;
    private boolean isDragging = false;

    /**
     * Creates a polyline strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public PolylineStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * When the user does a left mouse click, a new Polyline is created.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            Point newPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
            isDragging = false;

            if (this.panel.getCurrentShape() == null) {
                ShapeFactory shapeFactory = panel.getShapeFactory();
                Polyline polyline = (Polyline) shapeFactory.getShape(panel.getMode(), "", panel.getLineThickness());
                this.panel.setCurrentShape(polyline);

                polyline.addPoint(newPoint);
                polyline.setColor(this.panel.getColor());

                this.panel.getModel().addShape(polyline);
            } else {
                Polyline polyline = (Polyline) this.panel.getCurrentShape();
                Point lastPoint = polyline.getLast();

                if (lastPoint.x == newPoint.x && lastPoint.y == newPoint.y) {
                    refreshCurrentShape(polyline, true);
                    this.panel.setCurrentShape(null);
                } else {
                    polyline.addPoint(newPoint);
                    refreshCurrentShape(polyline, false);
                }
            }
        }
    }

    /**
     * When the user does a left mouse drag, the current Polyline's line is updated according to
     * the user's mouse position.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            isDragging = true;
            if (this.panel.getCurrentShape() != null) {
                Polyline polyline = (Polyline) this.panel.getCurrentShape();
                Point dragPoint = new Point(mouseEvent.getX(), mouseEvent.getY());

                polyline.popPoint();
                polyline.addPoint(dragPoint);
                refreshCurrentShape(polyline, false);
            }
        }
    }

    /**
     * When the user does a left mouse release, the final Polyline is drawn onto the canvas.
     * @param mouseEvent the mouse action performed by the user
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            if (this.panel.getCurrentShape() != null) {
                Polyline polyline = (Polyline) this.panel.getCurrentShape();
                Point newPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                Point firstPoint = polyline.getFirst();

                if (Point2D.distance(newPoint.x, newPoint.y, firstPoint.x, firstPoint.y) < 10) {
                    polyline.setClosed(true);
                    refreshCurrentShape(polyline, true);
                    this.panel.setCurrentShape(null);
                } else if (isDragging) {
                    refreshCurrentShape(polyline, false);
                }
            }
            isDragging = false;
        }
    }

    private void refreshCurrentShape(Polyline p, boolean isFinal) {
        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        if (isFinal) {
            this.panel.getModel().addShapeFinal(p);
        } else {
            this.panel.getModel().addShape(p);
        }
    }
}