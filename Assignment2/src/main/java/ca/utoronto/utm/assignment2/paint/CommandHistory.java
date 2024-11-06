package ca.utoronto.utm.assignment2.paint;
import java.util.ArrayList;
import java.util.Stack;

public class CommandHistory {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    private Stack<ArrayList<Shape>> stateStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }
    public void undo() {
        if (!(undoStack.isEmpty())) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public void addState(ArrayList<Shape> state) {
        stateStack.push(state);
    }
    public ArrayList<Shape> revertState(){
        return stateStack.pop();
    }
    public void addToLast(ArrayList<Shape> state) {
        Command store = undoStack.peek();
        if (store instanceof EraserStrokeCommand){
            ((EraserStrokeCommand) store).addRemovedShapes(state);
        }
    }
    public void popLastCommand(){
        this.undoStack.pop();
    }

    public Stack<Command> getUndoStack() {
        return this.undoStack;
    }

    public void reset() {
        this.undoStack.clear();
        this.redoStack.clear();
        this.stateStack.clear();
    }
}
