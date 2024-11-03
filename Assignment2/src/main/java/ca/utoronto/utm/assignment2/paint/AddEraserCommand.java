package ca.utoronto.utm.assignment2.paint;

public class AddEraserCommand implements Command {
    private Eraser eraser;
    private PaintLayer paintLayer;
    public AddEraserCommand(Eraser e, PaintLayer l) {
        this.eraser = e;
        this.paintLayer = l;
    }
    public void execute() {
        paintLayer.addEraser(eraser);
    }
    public void undo() {
        paintLayer.removeEraser();
    }
}
