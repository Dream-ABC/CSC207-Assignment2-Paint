package ca.utoronto.utm.assignment2.paint;

public class AddLayerCommand implements Command {
    PaintLayer layer;
    PaintModel model;
    CommandHistory history;

    public AddLayerCommand(PaintModel m, PaintLayer l, CommandHistory h) {
        this.layer = l;
        this.model = m;
        this.history = h;
    }

    public void execute() {
        this.model.getLayers().add(layer);
    }
    public void undo() {
        if (this.model.getLayers().size() > 1) {
            this.model.getLayers().remove(this.layer);
        } else {
            history.undo();
        }
    }

    public String toString() {
        return "AddLayer#" + "\n";
    }
}
