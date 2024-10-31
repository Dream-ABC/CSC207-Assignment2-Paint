package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PaintLayer extends Canvas {

    private boolean isVisible;
    private Color color;
    private ArrayList<Shape> shapes = new ArrayList<>();

    public PaintLayer(int width, int height) {
        super(width, height);
        this.shapes = new ArrayList<>();
        this.isVisible = true;
        this.color = Color.WHITE;
    }

    public void addShape(Shape shape) {
        this.shapes.add(shape);
        System.out.println("shape added");
        System.out.println(shapes.size());
    }

    public void removeShape(Shape shape) {
        this.shapes.remove(shape);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setVisibility(boolean visible) {
        this.isVisible = visible;
        this.setVisible(this.isVisible);
    }

    public boolean getVisibility() {
        return this.isVisible;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void display() {
        GraphicsContext g2d = this.getGraphicsContext2D();
        // background
        // g2d.setFill(this.color);
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        // shapes on this layer
        for (Shape shape : this.shapes) {
            System.out.println("shape:"+shape.toString());
            g2d.setFill(shape.getColor());
            shape.display(g2d);
        }
    }
}
