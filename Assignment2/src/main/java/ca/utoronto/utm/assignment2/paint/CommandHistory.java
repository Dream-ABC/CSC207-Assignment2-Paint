package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The CommandHistory class manages the execution history of commands,
 * supporting undo and redo operations. It stores previous states for
 * shapes, allowing actions to be reverted or reapplied as needed.
 */
public class CommandHistory {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private final Stack<ArrayList<Shape>> stateStack = new Stack<>();

    /**
     * Executes the specified command, adding it to the undo stack and
     * clearing the redo stack.
     *
     * @param command the command to execute
     */
    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undoes the last executed command if possible, adding it to the redo stack.
     */
    public void undo() {
        if (!(undoStack.isEmpty())) {
            Command command = undoStack.pop();
            redoStack.push(command);
            command.undo();
        }
    }

    /**
     * Redoes the last undone command if possible, adding it back to the undo stack.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    /**
     * Adds the specified state to the history stack for shape states.
     *
     * @param state the state to save in the history
     */
    public void addState(ArrayList<Shape> state) {
        stateStack.push(state);
    }

    /**
     * Reverts to the last saved shape state by popping it from the state stack.
     *
     * @return the last saved shape state
     */
    public ArrayList<Shape> revertState() {
        return stateStack.pop();
    }

    /**
     * Adds the specified state to the last command on the undo stack,
     * allowing it to manage removed shapes.
     *
     * @param state the state to add to the last command
     */
    public void addToLast(ArrayList<Shape> state) {
        Command store = undoStack.peek();
        ((StrokeEraserCommand) store).addRemovedShapes(state);
    }

    /**
     * Removes the last command from the undo stack.
     */
    public void popLastCommand() {
        this.undoStack.pop();
    }

    /**
     * Removes the last command from the redo stack.
     */
    public void popLastRedoCommand() {
        this.redoStack.pop();
    }

    /**
     * Returns the current undo stack, primarily for inspection.
     *
     * @return the undo stack of commands
     */
    public Stack<Command> getUndoStack() {
        return this.undoStack;
    }

    /**
     * Clears all command and state history, resetting undo, redo, and state stacks.
     */
    public void reset() {
        this.undoStack.clear();
        this.redoStack.clear();
        this.stateStack.clear();
    }
}
