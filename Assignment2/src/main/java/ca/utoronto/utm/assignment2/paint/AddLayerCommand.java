package ca.utoronto.utm.assignment2.paint;

public class AddLayerCommand implements Command {
    PaintLayer layer;
    PaintModel model;

    public AddLayerCommand(PaintModel m, PaintLayer l) {
        this.layer = l;
        this.model = m;
    }

    public void execute() {
        if (!(this.model.getLayers().contains(layer))) {
            this.model.getLayers().add(layer);
        }
    }
    public void undo() {
        this.model.removeLayer(this.layer);
    }
}
