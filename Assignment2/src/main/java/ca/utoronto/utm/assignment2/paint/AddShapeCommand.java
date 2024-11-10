package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * The AddShapeCommand class implements the {@link Command} interface
 * and represents an action to add a {@link Shape} to a specified {@link PaintLayer}.
 * This command supports execution and undo functionality to enable shape manipulation within a layer.
 */
public class AddShapeCommand implements Command {
    private final Shape shape;
    private final PaintLayer layer;
    private final CommandHistory history;
    private int index;

    /**
     * Constructs an AddShapeCommand with the specified shape, layer, history, and model.
     * The command targets a specific layer within the model to which the shape will be added.
     *
     * @param s the shape to add
     * @param l the layer where the shape will be added
     * @param h the command history to manage undo and redo actions
     * @param m the paint model containing the layer
     */
    public AddShapeCommand(Shape s, PaintLayer l, CommandHistory h, PaintModel m) {
        this.shape = s;
        this.layer = l;
        this.history = h;
        this.index = m.getLayers().indexOf(l);
    }

    /**
     * Adds the shape to the specified layer.
     */
    @Override
    public void execute() {
        history.addState(new ArrayList<Shape>(this.layer.getShapes()));
        this.layer.addShape(this.shape);
    }

    /**
     * Undoes the add shape action, restoring the layer to its previous state.
     */
    @Override
    public void undo() {
        this.layer.setShapes(history.revertState());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "AddShape#" + this.index + "&" + this.shape.toString();
    }
}
