package ca.utoronto.utm.assignment2.paint;

/**
 * The AddLayerCommand class implements the {@link Command} interface
 * and represents an action to add a new layer to the {@link PaintModel}.
 * It supports execution and undo functionality.
 */
public class AddLayerCommand implements Command {
    final PaintLayer layer;
    final PaintModel model;
    final CommandHistory history;

    /**
     * Constructs an AddLayerCommand with the specified model, layer, and history.
     *
     * @param m the paint model to which the layer will be added
     * @param l the layer to add to the model
     * @param h the command history for managing undo and redo operations
     */
    public AddLayerCommand(PaintModel m, PaintLayer l, CommandHistory h) {
        this.layer = l;
        this.model = m;
        this.history = h;
    }

    /**
     * Adds the layer to the model and sets it as the currently selected layer.
     */
    @Override
    public void execute() {
        this.model.getLayers().add(layer);
        this.model.setSelectedLayer(layer);
    }

    /**
     * Undoes the add layer action, removing the layer from the model if possible.
     * Reverts to the last command if removal is not feasible.
     */
    @Override
    public void undo() {
        if (this.model.getLayers().size() > 1) {
            this.model.getLayers().remove(this.layer);
        } else {
            history.popLastRedoCommand();
            history.undo();
        }
    }

    /**
     * Returns a string representation of the AddLayerCommand instance, including the width
     * and height of the layer being added.
     *
     * @return a string that describes the AddLayerCommand instance
     */
    @Override
    public String toString() {
        return "AddLayer#" + this.layer.getWidth() + "," + this.layer.getHeight();
    }
}
