package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class EraserStrokeCommand implements Command {

    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> removedShapes;
    private boolean changed = false;
    private ArrayList<Integer> indices;

    public EraserStrokeCommand(PaintLayer l, CommandHistory h) {
        layer = l;
        history = h;
        indices = new ArrayList<>();
    }

    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        if (changed) {
            for (Shape s: removedShapes) {
                indices.add(layer.getShapes().indexOf(s));
                System.out.println("index added to this.indices in eraser command:"+layer.getShapes().indexOf(s));
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

        for (int index : this.indices) {
            shapes.append(index);
            shapes.append(",");
        }

        return "EraserStroke#" + shapes;
    }
}
