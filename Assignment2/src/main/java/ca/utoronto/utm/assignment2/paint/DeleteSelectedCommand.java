package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * The DeleteSelectedCommand class implements the {@link Command} interface
 * and represents an action to delete selected {@link Shape} objects from a {@link PaintLayer}.
 * This command supports execution and undo functionality to remove selected shapes.
 */
public class DeleteSelectedCommand implements Command {
    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> selectedShapes;
    private ArrayList<Integer> shapeIndex;

    /**
     * Constructs a DeleteSelectedCommand with the specified layer, history, and selected shapes.
     * The command targets specific shapes in the layer for deletion.
     *
     * @param l the paint layer containing the shapes to delete
     * @param h the command history to manage undo and redo actions
     * @param s the list of selected shapes to delete
     */
    public DeleteSelectedCommand(PaintLayer l, CommandHistory h, ArrayList<Shape> s) {
        layer = l;
        history = h;
        selectedShapes = s;

        shapeIndex = new ArrayList<>();
        for (Shape shape : selectedShapes) {
            shapeIndex.add(l.getShapes().indexOf(shape));
        }
    }

    /**
     * Executes the command by deleting the selected shapes from the layer.
     */
    @Override
    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        for (Shape s : selectedShapes) {
            layer.removeShape(s);
        }
    }

    /**
     * Undoes the deletion by restoring the previous state of shapes in the layer.
     */
    public void undo() {
        this.layer.setShapes(history.revertState());
    }

    /**
     * Returns a string representation of the DeleteSelectedCommand instance,
     * including the indices of shapes that were selected for deletion.
     *
     * @return a string representation of the DeleteSelectedCommand instance
     */
    public String toString() {
        StringBuilder shapes = new StringBuilder();
        for (int i : shapeIndex) {
            shapes.append(i).append(",");
        }

        return "DeleteSelected#" + shapes;
    }
}
