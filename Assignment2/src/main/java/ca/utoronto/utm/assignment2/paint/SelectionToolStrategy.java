package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SelectionToolStrategy implements ShapeStrategy{
    private PaintPanel panel;

    public SelectionToolStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        SelectionTool selection = new SelectionTool();
        Point origin = new Point(mouseEvent.getX(), mouseEvent.getY());
        selection.setCentre(origin);
        selection.setOrigin(origin);
        this.panel.setSelectionTool(selection);
        this.panel.getModel().addSelectionTool(selection);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        SelectionTool selection = panel.getSelectionTool();

        Point origin = selection.getOrigin();

        double width = Math.abs(origin.x - mouseEvent.getX());
        double height = Math.abs(origin.y - mouseEvent.getY());
        double x = Math.min(origin.x, mouseEvent.getX());
        double y = Math.min(origin.y, mouseEvent.getY());
        selection.setCentre(new Point(x, y));
        selection.setDimensionX(width);
        selection.setDimensionY(height);

        this.panel.getModel().addSelectionTool(selection);
    }

    private void selectShapes(){
        ArrayList<Shape> currLayer = new ArrayList<>(this.panel.getModel().getSelectedLayer().getShapes());
        for (Shape shape : currLayer) {
            if (shape.overlaps(this.panel.getSelectionTool())) {
                this.panel.getSelectionTool().addSelectedShape(shape);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
//        this.panel.getModel().removeSelectionTool();
//        this.panel.setSelectionTool(null);
        selectShapes();

        System.out.println(this.panel.getSelectionTool().getSelectedShapes().size());
        //doubt I need this
    }

}
