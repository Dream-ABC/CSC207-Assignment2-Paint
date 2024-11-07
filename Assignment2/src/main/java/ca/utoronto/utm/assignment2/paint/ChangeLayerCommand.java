package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerCommand implements Command {
    private PaintLayer oldLayer;
    private PaintLayer newLayer;
    private PaintModel model;
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
