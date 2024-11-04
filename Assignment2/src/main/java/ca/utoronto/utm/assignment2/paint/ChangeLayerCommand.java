package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerCommand implements Command {
    PaintLayer oldLayer;
    PaintLayer newLayer;
    PaintModel model;

    public ChangeLayerCommand(PaintLayer o, PaintLayer n, PaintModel m) {
        oldLayer = o;
        newLayer = n;
        model = m;
    }

    public void execute() {
        this.model.setSelectedLayer(newLayer);
    }
    public void undo() {
        this.model.setSelectedLayer(oldLayer);
    }
}
