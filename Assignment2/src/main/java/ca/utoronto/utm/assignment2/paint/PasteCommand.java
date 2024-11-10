package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class PasteCommand implements Command {
    private PaintLayer layer;
    private CommandHistory history;
    private ArrayList<Shape> copiedShapes;
    private SelectionTool selectionTool;
    private PaintModel model;

    public PasteCommand(PaintLayer l, CommandHistory h, ArrayList<Shape> c, SelectionTool s, PaintModel m) {
        layer = l;
        history = h;
        copiedShapes = new ArrayList<Shape>(c);
        selectionTool = s;
        model = m;
    }

    public void execute() {
        history.addState(new ArrayList<Shape>(layer.getShapes()));
        SelectionTool selection = selectionTool.copy();
        for (Shape s : copiedShapes) {
            Shape copy = s.copy();
            layer.addShape(copy);
            selection.addSelectedShape(copy);
        }
        selection.shift(100, 100);
        model.addSelectionTool(selection);
    }
    public void undo() {
        this.layer.setShapes(history.revertState());

    }
    public String toString() {
        return "PasteCommand";
    }
}
