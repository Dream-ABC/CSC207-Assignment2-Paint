package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class DragCommand implements Command {
    private ArrayList<Shape> selectedShapes;
    private double shiftX, shiftY;

    public DragCommand(ArrayList<Shape> s, double x, double y) {
        selectedShapes = s;
        shiftX = x;
        shiftY = y;
    }
    public void execute() {
        for (Shape shape : selectedShapes) {
            shape.shift(shiftX, shiftY);
        }
    }
    public void undo() {
        for (Shape shape : selectedShapes) {
            shape.shift(-shiftX, -shiftY);
        }
    }

    public void addShift(double x, double y) {
        for (Shape shape : selectedShapes) {
            shape.shift(x, y);
        }
        shiftX += x;
        shiftY += y;
    }

    @Override
    public String toString() {
        return "DragCommand";
    }
}
