package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PaintLayer extends Canvas {

    private String status;  // "changed", "unchanged", "removed"
    private Color color;
    private ArrayList<Shape> shapes;
    private Eraser eraser;

    public PaintLayer() {
        super(300, 300);  // default size
        this.shapes = new ArrayList<>();
        this.setVisible(true);
        this.status = "changed";
        this.color = Color.TRANSPARENT;
    }

    public PaintLayer(int width, int height) {
        super(width, height);
        this.shapes = new ArrayList<>();
        this.setVisible(true);
        this.status = "changed";
        this.color = Color.TRANSPARENT;
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    public void addShapeFirst(Shape shape) {
        this.shapes.add(0, shape);
    }

    public void removeShape(Shape shape) {
        this.shapes.remove(shape);
    }

    public void addEraser(Eraser eraser) {this.eraser = eraser;}
    public void removeEraser() {this.eraser = null;}

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void display(GraphicsContext g2d) {
        // background
        g2d.setFill(this.color);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // shapes on this layer
        for (Shape shape : this.shapes) {
            g2d.setFill(shape.getColor());
            shape.display(g2d);
        }
        if (this.eraser != null){
            eraser.display(g2d);
        }
    }
}
