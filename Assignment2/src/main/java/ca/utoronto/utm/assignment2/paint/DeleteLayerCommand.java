package ca.utoronto.utm.assignment2.paint;

public class DeleteLayerCommand implements Command {
    PaintLayer layer;
    PaintModel model;

    public DeleteLayerCommand(PaintModel m, PaintLayer l) {
        this.layer = l;
        this.model = m;
    }

    public void execute() {
        this.model.getLayers().remove(this.layer);
    }
    public void undo() {
        this.model.getLayers().add(this.layer);
    }
}
