package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class DeleteSelectedCommand implements Command {
    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> selectedShapes;
    private ArrayList<Integer> shapeIndex;

    public DeleteSelectedCommand(PaintLayer l, CommandHistory h, ArrayList<Shape> s) {
        layer = l;
        history = h;
        selectedShapes = s;

        shapeIndex = new ArrayList<>();
        for (Shape shape : selectedShapes) {
            shapeIndex.add(l.getShapes().indexOf(shape));
        }
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

    /**
     * Returns a string representation of the DeleteSelectedCommand instance,
     * including the indices of shapes that were selected for deletion.
     *
     * @return a string representation of the DeleteSelectedCommand instance
     */
    public String toString() {
        StringBuilder shapes = new StringBuilder();
        for (int i : shapeIndex) {
            shapes.append(i).append(",");
        }

        return "DeleteSelected#" + shapes;
    }
}
