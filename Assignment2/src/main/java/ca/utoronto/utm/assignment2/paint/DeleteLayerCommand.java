package ca.utoronto.utm.assignment2.paint;

/**
 * The DeleteLayerCommand class implements the {@link Command} interface
 * and represents an action to delete a {@link PaintLayer} from the {@link PaintModel}.
 * This command supports execution and undo functionality to manage the removal of layers.
 */
public class DeleteLayerCommand implements Command {
    final PaintLayer layer;
    final PaintModel model;
    final CommandHistory history;
    private int index;

    /**
     * Constructs a DeleteLayerCommand with the specified paint model, layer, and command history.
     * The command targets a specific layer for deletion within the model.
     *
     * @param m the paint model containing the layers
     * @param l the layer to be deleted
     * @param h the command history to manage undo and redo actions
     */
    public DeleteLayerCommand(PaintModel m, PaintLayer l, CommandHistory h) {
        this.layer = l;
        this.index = m.getLayers().indexOf(this.layer);
        this.model = m;
        this.history = h;
    }

    /**
     * Executes the command by deleting the specified layer from the model. If there is only one layer
     * remaining, pops the last command from the Undo Stack. The selected layer is updated to the previous
     * layer, or the next one if it's the first layer.
     */
    @Override
    public void execute() {
        if (this.model.getLayers().size() > 1) {
            this.model.getLayers().remove(this.layer);
            if (this.index == 0) {
                this.model.setSelectedLayer(this.model.getLayers().get(this.index));
            } else {
                this.model.setSelectedLayer(this.model.getLayers().get(this.index - 1));
            }
        } else {
            history.popLastCommand();
        }
    }

    /**
     * Undoes the deletion by restoring the deleted layer back to the model.
     * The restored layer is selected after undoing.
     */
    @Override
    public void undo() {
        this.model.getLayers().add(this.layer);
        this.model.setSelectedLayer(this.layer);
    }

    /**
     * Returns a string representation of the DeleteLayerCommand instance, including the index of
     * the layer being deleted.
     *
     * @return a string representing the DeleteLayerCommand instance
     */
    @Override
    public String toString() {
        return "DeleteLayer#" + this.index;
    }
}
