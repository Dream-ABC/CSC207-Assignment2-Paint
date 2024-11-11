package ca.utoronto.utm.assignment2.paint;

/**
 * The ChangeLayerCommand class implements the {@link Command} interface
 * and represents an action to change the selected layer in the {@link PaintModel}.
 * This command supports execution and undo functionality to allow toggling between layers.
 */
public class ChangeLayerCommand implements Command {
    final PaintLayer oldLayer;
    final PaintLayer newLayer;
    final PaintModel model;
    private int index;

    /**
     * Constructs a ChangeLayerCommand with the specified previous layer, new layer, and model.
     * The command switches the selected layer in the model from the old layer to the new layer.
     *
     * @param o the previously selected layer
     * @param n the new layer to be selected
     * @param m the paint model containing the layers
     */
    public ChangeLayerCommand(PaintLayer o, PaintLayer n, PaintModel m) {
        oldLayer = o;
        newLayer = n;
        model = m;
        index = m.getLayers().indexOf(this.newLayer);
    }

    /**
     * Switches the selected layer in the model to the new layer.
     */
    @Override
    public void execute() {
        this.model.setSelectedLayer(newLayer);
    }

    /**
     * Reverts the selection to the previous layer in the model.
     */
    @Override
    public void undo() {
        this.model.setSelectedLayer(oldLayer);
    }

    /**
     * Returns a string representation of the ChangeLayerCommand instance including
     * the index of the layer to be switched to.
     *
     * @return a string representation of the ChangeLayerCommand instance
     */
    @Override
    public String toString() {
        return "ChangeLayer#" + this.index;
    }
}
