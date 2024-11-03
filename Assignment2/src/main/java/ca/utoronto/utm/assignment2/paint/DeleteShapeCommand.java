package ca.utoronto.utm.assignment2.paint;

public class DeleteShapeCommand implements Command {
    private Shape shape;
    private PaintLayer layer;

    public DeleteShapeCommand(Shape s, PaintLayer l) {
        this.shape = s;
        this.layer = l;
    }

    public void execute() {
        this.layer.removeShape(this.shape);
    }
    public void undo() {
        this.layer.addShapeFirst(this.shape);
    }
}
