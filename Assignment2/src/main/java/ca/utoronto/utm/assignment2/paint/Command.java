package ca.utoronto.utm.assignment2.paint;

/**
 * An interface for all types of commands.
 */
public interface Command {
    void execute();
    void undo();
    String toString();
}
