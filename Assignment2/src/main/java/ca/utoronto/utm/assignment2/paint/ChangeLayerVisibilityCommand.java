package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerVisibilityCommand implements Command {

    private PaintLayer layer;
    private PaintModel model;

    public ChangeLayerVisibilityCommand(PaintLayer l, PaintModel m) {
        this.layer = l;
        this.model = m;
    }

    public void execute() {
        layer.setVisible(!layer.isVisible());
    }

    public void undo() {
        layer.setVisible(!layer.isVisible());
    }

    public String toString() {
        return "ChangeVisibility#" + this.model.getLayers().indexOf(this.layer) + "\n";
    }
}
