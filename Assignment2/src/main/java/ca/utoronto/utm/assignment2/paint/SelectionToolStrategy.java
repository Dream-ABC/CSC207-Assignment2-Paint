package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

/**
 * A class to represent the selection tool drawing strategy.
 * SelectionToolStrategy implements the {@link ShapeStrategy} interface.
 */
public class SelectionToolStrategy implements ShapeStrategy{
    private final PaintPanel panel;

    /**
     * Creates an selectionToolStrategy strategy connected to the paint panel.
     * @param p the main panel where drawing actions are managed
     */
    public SelectionToolStrategy(PaintPanel p) {
        this.panel = p;
    }

    /**
     * Handles mouse press events. When the mouse is pressed, it checks if the
     * mouse is within bounds of the existing selection tool. If so, it starts
     * dragging the selection. Otherwise, a new selection tool is created at
     * the mouse position.
     *
     * @param mouseEvent The mouse press event.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            if (panel.getModel().getSelectionTool() != null && panel.getModel().getSelectionTool().inBounds(mouseEvent.getX(), mouseEvent.getY())) {
                panel.getModel().getSelectionTool().setOldLocation(new Point(mouseEvent.getX(), mouseEvent.getY()));
                panel.getModel().shiftStart(0, 0);
                panel.getModel().getSelectionTool().setDragging(true);
            }
            else {
                SelectionTool selection = new SelectionTool(this.panel.getModel().getSelectedLayer());
                Point origin = new Point(mouseEvent.getX(), mouseEvent.getY());
                selection.setTopLeft(origin);
                selection.setOrigin(origin);
                this.panel.getModel().addSelectionTool(selection);
            }
        }
    }

    /**
     * Handles mouse drag events. If the selection tool is being dragged, the
     * selection's position is updated based on the mouse's movement. If not,
     * the dimensions of the selection area are updated to enclose the mouse's
     * drag area, and shapes within the selection area are selected.
     *
     * @param mouseEvent The mouse drag event.
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (panel.getModel().getSelectionTool() != null) {
            if (panel.getModel().getSelectionTool().getDragging()) {
                double x = mouseEvent.getX() - panel.getModel().getSelectionTool().getOldLocation().x;
                double y = mouseEvent.getY() - panel.getModel().getSelectionTool().getOldLocation().y;

                panel.getModel().getSelectionTool().shift(x, y);
                panel.getModel().addShift(x, y);
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

    /**
     * Selects all shapes within the current selection tool's bounds.
     * Shapes that overlap with the selection tool are added to the selection.
     */
    private void selectShapes(){
        ArrayList<Shape> currLayer = new ArrayList<>(this.panel.getModel().getSelectedLayer().getShapes());
        for (Shape shape : currLayer) {
            if (shape.overlaps(this.panel.getModel().getSelectionTool())) {
                this.panel.getModel().getSelectionTool().addSelectedShape(shape);
            }
        }
    }

    /**
     * Handles mouse release events. When the mouse is released, the dragging
     * state of the selection tool is reset.
     *
     * @param mouseEvent The mouse release event.
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
            panel.getModel().getSelectionTool().setDragging(false);
        }
    }

}
