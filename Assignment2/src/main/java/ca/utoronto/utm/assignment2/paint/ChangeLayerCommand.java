package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerCommand implements Command {
    final PaintLayer oldLayer;
    final PaintLayer newLayer;
    final PaintModel model;
    private int index;

    public ChangeLayerCommand(PaintLayer o, PaintLayer n, PaintModel m) {
        oldLayer = o;
        newLayer = n;
        model = m;
        index = m.getLayers().indexOf(this.newLayer);
    }

    public void execute() {
        this.model.setSelectedLayer(newLayer);
    }

    public void undo() {
        this.model.setSelectedLayer(oldLayer);
    }

    public String toString() {
        return "ChangeLayer#" + this.index;
    }
}
