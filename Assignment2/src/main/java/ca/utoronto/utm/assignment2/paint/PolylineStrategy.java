package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;

public class PolylineStrategy implements ShapeStrategy {

    private PaintPanel panel;

    public PolylineStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point newPoint = new Point(mouseEvent.getX(), mouseEvent.getY());

        if (this.panel.getCurrentShape() == null) {
            ShapeFactory shapeFactory = panel.getShapeFactory();
            Polyline polyline = (Polyline) shapeFactory.getShape(panel.getMode(), "");
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
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Polyline polyline = (Polyline) this.panel.getCurrentShape();
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
            polyline.popPoint();
            polyline.addPoint(point);
            refreshCurrentShape(polyline, false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.panel.getCurrentShape() != null) {
            Polyline polyline = (Polyline) this.panel.getCurrentShape();
            Point newPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
            Point firstPoint = polyline.getFirst();

            if (Point2D.distance(newPoint.x, newPoint.y, firstPoint.x, firstPoint.y) < 10) {
                polyline.setClosed(true);
                refreshCurrentShape(polyline, true);
                this.panel.setCurrentShape(null);
            } else {
                polyline.addPoint(newPoint);
                refreshCurrentShape(polyline, false);
            }
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
