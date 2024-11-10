package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class DeleteSelectedCommand implements Command {
    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> selectedShapes;

    public DeleteSelectedCommand(PaintLayer l, CommandHistory h, ArrayList<Shape> s) {
        layer = l;
        history = h;
        selectedShapes = s;
    }
    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        for (Shape s : selectedShapes) {
            layer.removeShape(s);
        }
    }
    public void undo() {
        this.layer.setShapes(history.revertState());
    }
    public String toString() {
        return "";
    }
}
