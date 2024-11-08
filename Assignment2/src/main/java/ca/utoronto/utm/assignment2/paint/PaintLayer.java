package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PaintLayer extends Canvas {

    private final String status;  // "changed", "unchanged", "removed"
    private ArrayList<Shape> shapes;
    private StrokeEraser strokeEraser;
    private SelectionTool selectionTool;

    public PaintLayer() {
        super(300, 300);  // default size
        this.shapes = new ArrayList<>();
        this.setVisible(true);
        this.status = "changed";
    }

    public PaintLayer(int width, int height) {
        super(width, height);
        this.shapes = new ArrayList<>();
        this.setVisible(true);
        this.status = "changed";
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public void addShapeFirst(Shape shape) {
        this.shapes.addFirst(shape);
    }

    public void removeShape(Shape shape) {
        this.shapes.remove(shape);
    }

    public void addStrokeEraser(StrokeEraser strokeEraser) {this.strokeEraser = strokeEraser;}
    public void removeStrokeEraser() {this.strokeEraser = null;}

    public void addSelectionTool(SelectionTool selectionTool) {this.selectionTool = selectionTool;}
    public void removeSelectionTool() {this.selectionTool = null;}

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void display(GraphicsContext g2d) {
        // background
        g2d.setFill(Color.TRANSPARENT);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // shapes on this layer
        for (Shape shape : this.shapes) {
            g2d.setFill(shape.getColor());
            shape.display(g2d);
        }
        if (this.strokeEraser != null){
            strokeEraser.display(g2d);
        }
        if (this.selectionTool != null){
            selectionTool.display(g2d);
        }
    }
}
