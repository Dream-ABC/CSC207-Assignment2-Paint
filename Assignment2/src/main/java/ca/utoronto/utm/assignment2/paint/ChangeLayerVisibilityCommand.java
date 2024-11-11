package ca.utoronto.utm.assignment2.paint;

/**
 * Represents a command to toggle the visibility of a specified {@link PaintLayer}.
 * This command allows visibility changes to be executed and undone.
 */
public class ChangeLayerVisibilityCommand implements Command {
    private final PaintLayer layer;
    private PaintModel model;
    private int index;

    /**
     * Creates a command to toggle the visibility of a layer within the paint model.
     *
     * @param l the layer whose visibility is to be changed
     * @param m the paint model containing the layer
     */
    public ChangeLayerVisibilityCommand(PaintLayer l, PaintModel m) {
        this.layer = l;
        this.model = m;
        this.index = m.getLayers().indexOf(this.layer);
    }

    /**
     * Executes the visibility toggle action on the target layer.
     */
    @Override
    public void execute() {
        layer.setVisible(!layer.isVisible());
    }

    /**
     * Reverts the visibility of the layer to its previous state.
     */
    @Override
    public void undo() {
        layer.setVisible(!layer.isVisible());
    }

    /**
     * Returns a string representation of the ChangeLayerVisibilityCommand instance,
     * including the index of the target layer.
     *
     * @return a string representation of the ChangeLayerVisibilityCommand instance
     */
    @Override
    public String toString() {
        return "ChangeVisibility#" + this.index;
    }
}
