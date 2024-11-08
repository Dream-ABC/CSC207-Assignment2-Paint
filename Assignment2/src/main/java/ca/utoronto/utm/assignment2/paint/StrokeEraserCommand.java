package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class StrokeEraserCommand implements Command {
    private final PaintLayer layer;
    private final CommandHistory history;
    private ArrayList<Shape> removedShapes;
    private boolean changed = false;
    public StrokeEraserCommand(PaintLayer l, CommandHistory h) {
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
}
