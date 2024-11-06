package ca.utoronto.utm.assignment2.paint;

public class DeleteLayerCommand implements Command {
    PaintLayer layer;
    PaintModel model;
    CommandHistory history;

    public DeleteLayerCommand(PaintModel m, PaintLayer l, CommandHistory h) {
        this.layer = l;
        this.model = m;
        this.history = h;
    }

    public void execute() {
        if (this.model.getLayers().size() > 1) {
            this.model.getLayers().remove(this.layer);
        } else{
            history.popLastCommand();
        }
    }

    public void undo() {
        this.model.getLayers().add(this.layer);
    }

    public String toString() {
        return "DeleteLayer#" + this.model.getLayers().indexOf(this.layer) + "\n";
    }
}
