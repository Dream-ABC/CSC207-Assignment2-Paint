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
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            if (panel.getModel().getSelectionTool() != null && panel.getModel().getSelectionTool().inBounds(mouseEvent.getX(), mouseEvent.getY())) {
                System.out.println("a");
                panel.getModel().getSelectionTool().setOldLocation(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
            else {
                SelectionTool selection = new SelectionTool();
                Point origin = new Point(mouseEvent.getX(), mouseEvent.getY());
                selection.setTopLeft(origin);
                selection.setOrigin(origin);
                this.panel.getModel().addSelectionTool(selection);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (panel.getModel().getSelectionTool() != null) {
            if (panel.getModel().getSelectionTool().inBounds(mouseEvent.getX(), mouseEvent.getY()) && this.panel.getModel().getSelectionTool().getOldLocation() != null) {
                double x = mouseEvent.getX() - panel.getModel().getSelectionTool().getOldLocation().x;
                double y = mouseEvent.getY() - panel.getModel().getSelectionTool().getOldLocation().y;

                panel.getModel().getSelectionTool().shift(x, y);
                panel.getModel().getSelectionTool().setOldLocation(new Point(mouseEvent.getX(), mouseEvent.getY()));
                this.panel.getModel().notifyChange();
            }
            else {

                SelectionTool selection = panel.getModel().getSelectionTool();

                Point origin = selection.getOrigin();

                double width = Math.abs(origin.x - mouseEvent.getX());
                double height = Math.abs(origin.y - mouseEvent.getY());
                double x = Math.min(origin.x, mouseEvent.getX());
                double y = Math.min(origin.y, mouseEvent.getY());
                selection.setTopLeft(new Point(x + width / 2, y + height / 2));
                selection.setDimensionX(width);
                selection.setDimensionY(height);

                panel.getModel().getSelectionTool().clearSelectedShapes();
                selectShapes();
            }
        }
    }

    private void selectShapes(){
        ArrayList<Shape> currLayer = new ArrayList<>(this.panel.getModel().getSelectedLayer().getShapes());
        for (Shape shape : currLayer) {
            if (shape.overlaps(this.panel.getModel().getSelectionTool())) {
                this.panel.getModel().getSelectionTool().addSelectedShape(shape);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {

            System.out.println(this.panel.getModel().getSelectionTool().getSelectedShapes().size());
            //doubt I need this
        }
    }

}
