package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class EraserStrokeCommand implements Command {

    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> removedShapes;
    private boolean changed = false;
    private ArrayList<Integer> indices;
    private ArrayList<Shape> originalShapes;

    public EraserStrokeCommand(PaintLayer l, CommandHistory h) {
        layer = l;
        history = h;
        indices = new ArrayList<>();
        originalShapes = (ArrayList<Shape>) l.getShapes().clone();
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
        for (Shape s: shapes) {
            indices.add(originalShapes.indexOf(s));
        }
    }

    public ArrayList<Shape> getRemovedShapes() {
        return removedShapes;
    }

    public String toString() {

        StringBuilder shapes = new StringBuilder();

        for (int index : this.indices) {
            shapes.append(index);
            shapes.append(",");
        }

        return "EraserStroke#" + shapes;
    }
}
