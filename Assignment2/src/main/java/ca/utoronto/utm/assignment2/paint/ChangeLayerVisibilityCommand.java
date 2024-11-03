package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerVisibilityCommand implements Command {
    private PaintLayer layer;
    public ChangeLayerVisibilityCommand(PaintLayer l) {
        this.layer = l;
    }
    public void execute() {
        layer.switchVisible();
    }
    public void undo() {
        layer.switchVisible();
    }
}
