package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerVisibilityCommand implements Command {
    private final PaintLayer layer;
    public ChangeLayerVisibilityCommand(PaintLayer l) {
        this.layer = l;
    }
    public void execute() {
        layer.setVisible(!layer.isVisible());
    }
    public void undo() {
        layer.setVisible(!layer.isVisible());
    }
}
