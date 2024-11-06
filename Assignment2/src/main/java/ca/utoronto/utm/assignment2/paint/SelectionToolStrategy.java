package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SelectionToolStrategy implements ShapeStrategy{
    private PaintPanel panel;

    public SelectionToolStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        SelectionTool selection = new SelectionTool();
        Point origin = new Point(mouseEvent.getX(), mouseEvent.getY())
        selection.setCentre(origin);
        selection.setOrigin(origin);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Point origin = selection.getOrigin();

        double width = Math.abs(origin.x - mouseEvent.getX());
        double height = Math.abs(origin.y - mouseEvent.getY());
        double x = Math.min(origin.x, mouseEvent.getX());
        double y = Math.min(origin.y, mouseEvent.getY());
        selection.setTopLeft(new Point(x, y));
        selection.setDimensionX(width);
        selection.setDimensionY(height);

        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(selection);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Rectangle selection = (Rectangle) panel.getCurrentShape();
        Point origin = selection.getOrigin();

        if (selection != null) {
            double width = Math.abs(origin.x - mouseEvent.getX());
            double height = Math.abs(origin.y - mouseEvent.getY());
            double x = Math.min(selection.getTopLeft().x, mouseEvent.getX());
            double y = Math.min(selection.getTopLeft().y, mouseEvent.getY());
            selection.setTopLeft(new Point(x, y));
            selection.setWidth(width);
            selection.setHeight(height);

            Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
            this.panel.getModel().getSelectedLayer().removeShape(shape);
            this.panel.getModel().addShapeFinal(selection);
            System.out.println("Added Rectangle");
            this.panel.setCurrentShape(null);
        }
    }

}
