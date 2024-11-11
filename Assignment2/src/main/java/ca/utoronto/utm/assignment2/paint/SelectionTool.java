package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Represents a selection tool that allows for selecting and dragging shapes
 * within a specific rectangular area in a paint layer.
 */
public class SelectionTool implements Tool {

    private Point topLeft;
    private Point origin;
    private double dimensionX;
    private double dimensionY;
    private final ArrayList<Shape> selectedShapes;
    private Point oldLocation;
    private boolean dragging;
    private PaintLayer layer;

    /**
     * Constructs a default selection tool with a width and height of 0,
     * and an empty selection list.
     *
     * @param layer The paint layer where the selection tool operates.
     */
    public SelectionTool(PaintLayer layer) {
        this.dimensionX = 0;
        this.dimensionY = 0;
        selectedShapes = new ArrayList<>();
        dragging = false;
        this.layer = layer;
    }

    /**
     * Gets the top-left point of the selection rectangle.
     *
     * @return The top-left point of the selection rectangle.
     */
    @Override
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * Sets the top-left point of the selection rectangle.
     *
     * @param topLeft The top-left point to set.
     */
    @Override
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Gets the origin point of the selection.
     * The origin point refers to where the selection tool began drawing.
     *
     * @return The origin point of the selection.
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * Sets the origin point of the selection.
     * The origin point refers to where the selection tool began drawing.
     *
     * @param origin The origin point to set.
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Gets the width (dimensionX) of the selection rectangle.
     *
     * @return The width of the selection rectangle.
     */
    @Override
    public double getDimensionX() {
        return this.dimensionX;
    }

    /**
     * Sets the width (dimensionX) of the selection rectangle.
     *
     * @param dimensionX The width of the selection rectangle.
     */
    public void setDimensionX(double dimensionX) {
        this.dimensionX = dimensionX;
    }

    /**
     * Gets the height (dimensionY) of the selection rectangle.
     *
     * @return The height of the selection rectangle.
     */
    @Override
    public double getDimensionY() {
        return this.dimensionY;
    }

    /**
     * Sets the height (dimensionY) of the selection rectangle.
     *
     * @param dimensionY The height of the selection rectangle.
     */
    public void setDimensionY(double dimensionY) {
        this.dimensionY = dimensionY;
    }

    /**
     * Gets the list of selected shapes.
     *
     * @return A list of selected shapes.
     */
    public ArrayList<Shape> getSelectedShapes() {
        return selectedShapes;
    }

    /**
     * Adds a shape to the selection.
     *
     * @param shape The shape to add to the selection.
     */
    public void addSelectedShape(Shape shape) {
        this.selectedShapes.add(shape);
    }

    /**
     * Gets the old location of the selection before a drag operation.
     *
     * @return The old location of the selection.
     */
    public Point getOldLocation() {
        return oldLocation;
    }

    /**
     * Sets the old location of the selection before a drag operation.
     *
     * @param oldLocation The old location to set.
     */
    public void setOldLocation(Point oldLocation) {
        this.oldLocation = oldLocation;
    }

    /**
     * Shifts the selection by a specified x and y distance.
     *
     * @param x The distance to shift the selection along the x-axis.
     * @param y The distance to shift the selection along the y-axis.
     */
    public void shift(double x, double y) {
        this.topLeft.shift(x, y);
    }

    /**
     * Checks if a given point (x, y) is within the bounds of the selection rectangle.
     *
     * @param x The x-coordinate of the point to check.
     * @param y The y-coordinate of the point to check.
     * @return True if the point is within bounds, false otherwise.
     */
    public boolean inBounds(double x, double y) {
        boolean a = this.topLeft.x - dimensionX / 2.0 <= x && x <= this.topLeft.x + dimensionX / 2.0;
        boolean b = this.topLeft.y - dimensionY / 2.0 <= y && y <= this.topLeft.y + dimensionY / 2.0;
        return a && b;
    }

    /**
     * Clears all selected shapes.
     */
    public void clearSelectedShapes() {
        this.selectedShapes.clear();
    }

    /**
     * Sets whether the selection is being dragged.
     *
     * @param dragging True if the selection is being dragged, false otherwise.
     */
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    /**
     * Gets whether the selection is being dragged.
     *
     * @return True if the selection is being dragged, false otherwise.
     */
    public boolean getDragging() {
        return this.dragging;
    }

    /**
     * Creates a copy of the current selection tool with the same properties.
     *
     * @return A new SelectionTool object that is a copy of this one.
     */
    public SelectionTool copy() {
        SelectionTool s = new SelectionTool(layer);
        s.setTopLeft(this.topLeft.copy());
        s.setDimensionX(this.dimensionX);
        s.setDimensionY(this.dimensionY);
        return s;
    }

    /**
     * Displays the Rectangle with user-created color and size.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineWidth(1);
        g2d.setStroke(Color.BLACK);
        g2d.setLineDashes(5, 3);
        g2d.strokeRect(this.topLeft.x - dimensionX / 2.0, this.topLeft.y - dimensionY / 2.0,
                this.dimensionX, this.dimensionY);
    }

    /**
     * Sets the properties of the SelectionTool based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - x-coordinate of top-left point
     *             data[1] - y-coordinate of top-left point
     *             data[2] - x-coordinate of origin point
     *             data[3] - y-coordinate of origin point
     *             data[4] - dimension in X direction
     *             data[5] - dimension in Y direction
     *             data[6] - x-coordinate of the old location
     *             data[7] - y-coordinate of the old location
     *             data[8] - dragging status (true or false)
     *             data[9 and onwards] - indices of selected shapes in the layer's shape list
     */
    public void setTool(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        this.origin = new Point(Double.parseDouble(data[2]), Double.parseDouble(data[3]));
        this.dimensionX = Double.parseDouble(data[4]);
        this.dimensionY = Double.parseDouble(data[5]);
        this.oldLocation = new Point(Double.parseDouble(data[6]), Double.parseDouble(data[7]));
        this.dragging = Boolean.parseBoolean(data[8]);
        for (int i = 9; i < data.length; i++) {
            int index = Integer.parseInt(data[i]);
            this.selectedShapes.add(this.layer.getShapes().get(index));
        }
    }

    /**
     * Returns a string representation of the SelectionTool instance, including
     * the top-left coordinates, the origin, dimensions, old location, dragging status,
     * and indices of the selected shapes.
     *
     * @return a string representation of the SelectionTool instance
     */
    public String toString() {
        StringBuilder shapes = new StringBuilder();
        for (Shape s : this.selectedShapes) {
            shapes.append(this.layer.getShapes().indexOf(s)).append(",");
        }

        return "Selection Tool{" + this.topLeft.x + "," + this.topLeft.y + ","
                + this.origin.x + "," + this.origin.y + "," + this.dimensionX + "," + this.dimensionY + ","
                + this.oldLocation.x + "," + this.oldLocation.y + "," + this.dragging + "," + shapes + "}";
    }
}
