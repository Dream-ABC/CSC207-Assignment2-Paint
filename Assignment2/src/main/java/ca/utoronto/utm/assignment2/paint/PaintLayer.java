package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class represents a layer on a canvas where shapes can be drawn, erased, and selected.
 * It supports adding and removing shapes, background images, a stroke eraser, and a selection tool.
 */
public class PaintLayer extends Canvas {

    private final String status;  // "changed", "unchanged", "removed"
    private ArrayList<Shape> shapes;
    private StrokeEraser strokeEraser;
    private SelectionTool selectionTool;
    private Image background;

    /**
     * Instantiates a new PaintLayer with default dimensions of 300x300.
     * The layer is set to be visible, marked as 'changed', and initialized
     * with an empty list of shapes and no background image.
     */
    public PaintLayer() {
        super(300, 300);  // default size
        this.shapes = new ArrayList<>();
        this.setVisible(true);
        this.status = "changed";
        this.background = null;
    }

    /**
     * Creates a new PaintLayer with specified width and height.
     *
     * @param width  the width of the layer
     * @param height the height of the layer
     */
    public PaintLayer(double width, double height) {
        super(width, height);
        this.shapes = new ArrayList<>();
        this.setVisible(true);
        this.status = "changed";
        this.background = null;
    }

    /**
     * Adds a specified shape to the current list of shapes in the PaintLayer.
     *
     * @param shape the shape to be added to this layer
     */
    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }

    /**
     * Removes a specified shape from the list of shapes in the PaintLayer.
     *
     * @param shape the shape to be removed from the layer
     */
    public void removeShape(Shape shape) {
        this.shapes.remove(shape);
    }

    /**
     * Assigns the given StrokeEraser to the PaintLayer.
     *
     * @param strokeEraser the StrokeEraser instance to be assigned to the PaintLayer
     */
    public void addStrokeEraser(StrokeEraser strokeEraser) {
        this.strokeEraser = strokeEraser;
    }

    /**
     * Removes the currently assigned StrokeEraser from the PaintLayer, effectively
     * disabling any currently active stroke eraser functionality in this layer.
     */
    public void removeStrokeEraser() {
        this.strokeEraser = null;
    }

    /**
     * Adds a selection tool to the PaintLayer, allowing for shape selection and
     * manipulation functionality.
     *
     * @param selectionTool the SelectionTool instance to be assigned to the PaintLayer
     */
    public void addSelectionTool(SelectionTool selectionTool) {
        this.selectionTool = selectionTool;
    }

    /**
     * Removes the currently assigned selection tool from the PaintLayer,
     * effectively disabling any shape selection and manipulation functionalities.
     */
    public void removeSelectionTool() {
        this.selectionTool = null;
    }

    /**
     * Returns the current SelectionTool assigned to this PaintLayer.
     *
     * @return the SelectionTool instance currently being used for shape selection
     * and manipulation
     */
    public SelectionTool getSelectionTool() {
        return selectionTool;
    }

    /**
     * Retrieves the list of shapes in the current PaintLayer.
     *
     * @return an ArrayList containing all shapes in this PaintLayer
     */
    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    /**
     * Sets the list of shapes in this PaintLayer.
     *
     * @param shapes the new list of shapes to be set
     */
    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    /**
     * Sets the background image for this PaintLayer.
     *
     * @param img the Image to be set as the background
     */
    public void setBackground(Image img) {
        this.background = img;
    }

    /**
     * Displays the PaintLayer by first clearing the background and then drawing
     * all shapes, stroke eraser, and selection tool if they are present.
     *
     * @param g2d the GraphicsContext used for rendering onto the PaintLayer
     */
    public void display(GraphicsContext g2d) {
        // background
        g2d.setFill(Color.TRANSPARENT);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (this.background != null) {
            g2d.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight());
        }

        // shapes on this layer
        for (Shape shape : this.shapes) {
            g2d.setFill(shape.getColor());
            shape.display(g2d);
        }
        if (this.strokeEraser != null) {
            strokeEraser.display(g2d);
        }
        if (this.selectionTool != null) {
            selectionTool.display(g2d);
        }
    }
}
