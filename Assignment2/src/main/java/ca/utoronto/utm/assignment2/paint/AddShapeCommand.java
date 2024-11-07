package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class AddShapeCommand implements Command {
    private Shape shape;
    private PaintLayer layer;
    private CommandHistory history;
    private int index;

    public AddShapeCommand(Shape s, PaintLayer l, CommandHistory h, PaintModel m) {
        this.shape = s;
        this.layer = l;
        this.history = h;
        this.index = m.getLayers().indexOf(l);
    }

    public void execute() {
        history.addState(new ArrayList<Shape>(this.layer.getShapes()));
        this.layer.addShape(this.shape);
    }

    public void undo() {
        this.layer.setShapes(history.revertState());
    }

    public String toString() {
        return "AddShape#" + this.index + "&" + this.shape.toString();
    }
}
