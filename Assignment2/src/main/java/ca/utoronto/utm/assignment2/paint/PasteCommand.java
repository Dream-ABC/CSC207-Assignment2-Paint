package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * Command to paste previously copied shapes into the current layer, creating new copies of the shapes.
 * The command stores the original state of the layer, adds the copied shapes to the layer, and moves them
 * by a fixed offset. It also manages the selection tool for the pasted shapes.
 */
public class PasteCommand implements Command {
    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> copiedShapes;
    private SelectionTool selectionTool;
    private PaintModel model;
    private ArrayList<Integer> shapeIndex;

    /**
     * Constructs a PasteCommand to paste previously copied shapes.
     *
     * @param l the paint layer to which shapes will be pasted
     * @param h the command history for state management
     * @param c the list of copied shapes to paste
     * @param s the selection tool managing the selected shapes
     * @param m the paint model managing the layers and selection
     */
    public PasteCommand(PaintLayer l, CommandHistory h, ArrayList<Shape> c, SelectionTool s, PaintModel m) {
        layer = l;
        history = h;
        copiedShapes = new ArrayList<Shape>(c);
        selectionTool = s;
        model = m;

        shapeIndex = new ArrayList<>();
        for (Shape shape : copiedShapes) {
            shapeIndex.add(l.getShapes().indexOf(shape));
        }
    }

    /**
     * Executes the paste operation by adding copies of the selected shapes
     * to the paint layer and shifting them for visibility.
     */
    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        SelectionTool selection = selectionTool.copy();
        for (Shape s : copiedShapes) {
            Shape copy = s.copy();
            layer.addShape(copy);
            selection.addSelectedShape(copy);
        }
        selection.shift(100, 100);
        model.addSelectionTool(selection);
    }

    /**
     * Undoes the paste operation by reverting the paint layer to its previous state.
     */
    public void undo() {
        this.layer.setShapes(history.revertState());
    }

    /**
     * Returns a string representation of the PasteCommand instance, including the
     * selection tool information and the indices of the shapes associated with this command.
     *
     * @return a string representation of the PasteCommand instance
     */
    public String toString() {
        StringBuilder shapes = new StringBuilder();
        for (int i : shapeIndex) {
            shapes.append(i).append(",");
        }

        return "Paste#" + this.selectionTool.toString() + "," + shapeIndex.toString();
    }
}
