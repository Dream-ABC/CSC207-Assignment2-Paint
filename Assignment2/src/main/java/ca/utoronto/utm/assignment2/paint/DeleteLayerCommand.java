package ca.utoronto.utm.assignment2.paint;

public class DeleteLayerCommand implements Command {
    private PaintLayer layer;
    private PaintModel model;
    private int index;
    private CommandHistory history;

    public DeleteLayerCommand(PaintModel m, PaintLayer l, CommandHistory h) {
        this.layer = l;
        this.index = m.getLayers().indexOf(this.layer);
        this.model = m;
        this.history = h;
    }

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

    public void undo() {
        this.model.getLayers().add(this.layer);
        this.model.setSelectedLayer(this.layer);
    }

    public String toString() {
        return "DeleteLayer#" + this.index;
    }
}
