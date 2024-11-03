package ca.utoronto.utm.assignment2.paint;

public class EraserStrokeCommand implements Command {
    private int removedShapes;
    private CommandHistory history;
    public EraserStrokeCommand(int m, CommandHistory h) {
        removedShapes = m;
        history = h;
    }
    public void execute() {
        for (int i = 0; i < removedShapes; i++) {
            history.redo();
        }
    }
    public void undo() {
        System.out.println(removedShapes);
        for (int i = 0; i < removedShapes; i++) {
            history.undo();
        }
    }
}
