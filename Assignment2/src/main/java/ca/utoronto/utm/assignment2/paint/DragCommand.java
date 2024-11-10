package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * The DragCommand class implements the {@link Command} interface
 * and represents an action to drag (move) selected {@link Shape} objects within a {@link PaintLayer}.
 * This command supports execution, undo, and shifting functionality to move shapes.
 */
public class DragCommand implements Command {
    private ArrayList<Shape> selectedShapes;
    private double shiftX, shiftY;
    private double totalShiftX, totalShiftY;
    private PaintLayer layer;
    private ArrayList<Integer> selectedShapesIndex;

    /**
     * Constructs a DragCommand with the specified selected shapes, shift values, and layer.
     * The command targets specific shapes in the layer to move them by the given shift amounts.
     *
     * @param s the list of shapes to drag
     * @param x the amount to shift the shapes along the x-axis
     * @param y the amount to shift the shapes along the y-axis
     * @param l the paint layer containing the shapes to be moved
     */
    public DragCommand(ArrayList<Shape> s, double x, double y, PaintLayer l) {
        selectedShapes = s;
        shiftX = x;
        shiftY = y;
        totalShiftX = 0;
        totalShiftY = 0;

        layer = l;
        selectedShapesIndex = new ArrayList<>();
        for (Shape shape : selectedShapes) {
            selectedShapesIndex.add(l.getShapes().indexOf(shape));
        }
    }

    /**
     * Executes the drag command by shifting the selected shapes by the specified amounts.
     */
    @Override
    public void execute() {
        System.out.println("execute:" + shiftX + "," + shiftY);
        for (Shape shape : selectedShapes) {
            shape.shift(shiftX, shiftY);
        }
    }

    /**
     * Undoes the drag action by shifting the selected shapes back by the inverse of the original shift amounts.
     */
    @Override
    public void undo() {
        for (Shape shape : selectedShapes) {
            shape.shift(-shiftX, -shiftY);
        }
    }

    /**
     * Adds additional shift values to the current movement of selected shapes.
     * Updates the total shift amounts and applies the new shift to all selected shapes.
     *
     * @param x the additional shift along the x-axis
     * @param y the additional shift along the y-axis
     */
    public void addShift(double x, double y) {
        for (Shape shape : selectedShapes) {
            shape.shift(x, y);
        }
        shiftX += x;
        shiftY += y;
        totalShiftX += x;
        totalShiftY += y;
        System.out.println("totalShift:" + totalShiftX + "," + totalShiftY);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder shapes = new StringBuilder();
        for (Shape shape : selectedShapes) {
            shapes.append(layer.getShapes().indexOf(shape)).append(",");
        }
        System.out.println("store:" + shiftX + "," + shiftY);

        return "Drag#" + totalShiftX + "," + totalShiftY + "&" + shapes;
    }
}
