package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class EraserStrokeCommand implements Command {

    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> removedShapes;
    private boolean changed = false;

    public EraserStrokeCommand(PaintLayer l, CommandHistory h) {
        layer = l;
        history = h;
    }

    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        if (changed) {
            for (Shape s: removedShapes) {
                layer.removeShape(s);
            }
            changed = false;
        }
    }

    public void undo() {
        this.layer.setShapes(history.revertState());
        changed = true;
    }

    public void addRemovedShapes(ArrayList<Shape> shapes) {
        removedShapes = shapes;
    }

    public String toString() {

        StringBuilder shapes = new StringBuilder();

        for (Shape shape : this.removedShapes) {
            shapes.append(this.layer.getShapes().indexOf(shape));
            shapes.append(",");
        }

        return "EraserStroke#" + shapes + "\n";
    }
}
