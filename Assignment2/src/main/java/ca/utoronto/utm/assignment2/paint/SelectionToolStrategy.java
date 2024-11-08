package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class SelectionToolStrategy implements ShapeStrategy{
    private final PaintPanel panel;

    public SelectionToolStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        SelectionTool selection = new SelectionTool();
        Point origin = new Point(mouseEvent.getX(), mouseEvent.getY());
        selection.setTopLeft(origin);
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
        selection.setTopLeft(new Point(x + width/2, y + height/2));
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
        selectShapes();

        System.out.println(this.panel.getSelectionTool().getSelectedShapes().size());
        //doubt I need this
    }

}
