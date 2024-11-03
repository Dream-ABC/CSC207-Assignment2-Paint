package ca.utoronto.utm.assignment2.paint;

public class AddShapeCommand implements Command {
    private Shape shape;
    private PaintLayer layer;
    public AddShapeCommand(Shape s, PaintLayer l) {
        this.shape = s;
        this.layer = l;
    }

    public void execute() {
        this.layer.addShape(this.shape);
    }
    public void undo() {
        this.layer.removeShape(this.shape);
    }
}
