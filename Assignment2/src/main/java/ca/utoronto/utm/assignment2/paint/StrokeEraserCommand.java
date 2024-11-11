package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * StrokeEraserCommand represents a command to remove shapes from a PaintLayer
 * using the StrokeEraser tool. It supports undo and redo functionality through
 * the CommandHistory.
 */
public class StrokeEraserCommand implements Command {
    private final PaintLayer layer;
    private final CommandHistory history;
    private ArrayList<Shape> removedShapes;
    private boolean changed = false;
    private ArrayList<Integer> indices;
    private ArrayList<Shape> originalShapes;

    /**
     * Constructs a StrokeEraserCommand with the specified PaintLayer and CommandHistory.
     *
     * @param l the PaintLayer on which the command operates
     * @param h the CommandHistory for tracking command states
     */
    public StrokeEraserCommand(PaintLayer l, CommandHistory h) {
        layer = l;
        history = h;
        indices = new ArrayList<>();
        originalShapes = (ArrayList<Shape>) l.getShapes().clone();
    }

    /**
     * Executes the stroke eraser command, removing shapes from the PaintLayer and
     * storing the current state in the history for undo functionality.
     */
    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        if (changed) {
            for (Shape s: removedShapes) {
                layer.removeShape(s);
            }
            changed = false;
        }
    }

    /**
     * Undoes the stroke eraser command by reverting the PaintLayer to its previous state.
     */
    public void undo() {
        this.layer.setShapes(history.revertState());
        changed = true;
    }

    /**
     * Adds the removed shapes to the command, recording their indices in the original list.
     *
     * @param shapes the list of shapes that were removed
     */
    public void addRemovedShapes(ArrayList<Shape> shapes) {
        removedShapes = shapes;
        for (Shape s: shapes) {
            indices.add(originalShapes.indexOf(s));
        }
    }

    /**
     * Returns a string representation of the StrokeEraserCommand instance,
     * including the indices of the shapes associated with this command.
     *
     * @return a string representation of the StrokeEraserCommand instance
     */
    public String toString() {

        StringBuilder shapes = new StringBuilder();

        for (int index : this.indices) {
            shapes.append(index);
            shapes.append(",");
        }
        return "EraserStroke#" + shapes;
    }
}
