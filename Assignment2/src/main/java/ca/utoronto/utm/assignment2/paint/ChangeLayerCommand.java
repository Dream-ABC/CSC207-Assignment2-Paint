package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerCommand implements Command {
    PaintLayer oldLayer;
    PaintLayer newLayer;

    public ChangeLayerCommand(PaintLayer o, PaintLayer n) {
        oldLayer = o;
        newLayer = n;
    }

    public void execute() {

    }
    public void undo() {

    }
}
