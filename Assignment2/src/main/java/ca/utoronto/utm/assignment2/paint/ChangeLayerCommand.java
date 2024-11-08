package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerCommand implements Command {
    final PaintLayer oldLayer;
    final PaintLayer newLayer;
    final PaintModel model;

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
