package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class DragCommand implements Command {
    private ArrayList<Shape> selectedShapes;
    private double shiftX, shiftY;
    private double totalShiftX, totalShiftY;
    private PaintLayer layer;
    private ArrayList<Integer> selectedShapesIndex;

    public DragCommand(ArrayList<Shape> s, double x, double y, PaintLayer l) {
        selectedShapes = s;
        shiftX = x;
        shiftY = y;
        totalShiftX = 0;
        totalShiftY = 0;

        layer = l;
        selectedShapesIndex = new ArrayList<>();
        for (Shape shape : selectedShapes) {
            selectedShapesIndex.add(l.getShapes().indexOf(shape));
        }
    }

    public void execute() {
        System.out.println("execute:" + shiftX + "," + shiftY);
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
        totalShiftX += x;
        totalShiftY += y;
        System.out.println("totalShift:" + totalShiftX + "," + totalShiftY);
    }

    public String toString() {
        StringBuilder shapes = new StringBuilder();
        for (Shape shape : selectedShapes) {
            shapes.append(layer.getShapes().indexOf(shape)).append(",");
        }
        System.out.println("store:" + shiftX + "," + shiftY);

        return "Drag#" + totalShiftX + "," + totalShiftY + "&" + shapes;
    }
}
