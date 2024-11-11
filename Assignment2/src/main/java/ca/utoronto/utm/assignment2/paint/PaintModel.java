package ca.utoronto.utm.assignment2.paint;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

/**
 * PaintModel represents the core data model of the painting application.
 * It manages layers, shapes, canvas state, zooming, and selection tools,
 * and supports undo/redo functionality through command history.
 */
public class PaintModel extends Observable {
    private final ArrayList<PaintLayer> layers = new ArrayList<>();
    private PaintLayer selectedLayer;
    private Shape selectedShape;
    private String mode;
    private View view;
    private int zoomFactor;
    private String fillStyle;
    private double thickness;
    private double canvasX, canvasY, canvasWidth, canvasHeight;
    private double mouseX, mouseY;
    private ArrayList<Shape> copiedShapes;
    private SelectionTool copiedSelectionTool;
    private DragCommand dragCommand;
    private final CommandHistory history = new CommandHistory();

    /**
     * Constructs a new PaintModel instance.
     * Initializes default values for the model state.
     */
    public PaintModel() {
        this.selectedShape = null;
        this.mode = "";
        this.canvasWidth = 700;
        this.canvasHeight = 400;
        this.fillStyle = "Solid";
        this.thickness = 1.0;
        this.zoomFactor = 100;
    }

    /**
     * Gets the current fill style for the shapes in the model.
     *
     * @return the current fill style
     */
    public String getFillStyle() {
        return this.fillStyle;
    }

    /**
     * Sets the fill style for the shapes in the model.
     *
     * @param fillStyle the fill style to set
     */
    public void setFillStyle(String fillStyle) {
        this.fillStyle = fillStyle;
        notifyChange();
    }

    /**
     * Gets the current line thickness used for drawing shapes.
     *
     * @return the current line thickness
     */
    public double getLineThickness() {
        return this.thickness;
    }

    /**
     * Sets the line thickness used for drawing shapes.
     *
     * @param thickness the line thickness to set
     */
    public void setLineThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * Selects the layer by name.
     *
     * @param layerName the name of the layer to select
     * @return true if the layer was successfully selected, false otherwise
     */
    public boolean selectLayer(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        if (layerIndex == layers.indexOf(this.selectedLayer)) {
            return false;  // nothing changed
        }
        history.execute(new ChangeLayerCommand(this.selectedLayer, layers.get(layerIndex), this));
        notifyChange();
        return true;
    }

    /**
     * Adds a new layer to the model.
     */
    public void addLayer() {
        PaintLayer layer = new PaintLayer();
        history.execute(new AddLayerCommand(this, layer, history));
        this.selectedLayer = layer;
        notifyChange();
    }

    /**
     * Removes the currently selected layer.
     */
    public void removeLayer() {
        removeLayer(this.selectedLayer);
    }

    /**
     * Removes the specified layer.
     *
     * @param layer the layer to remove
     */
    public void removeLayer(PaintLayer layer) {
        if (this.layers.size() > 1) {
            // when there is only one layer, the user cannot remove it
            int currIndex = this.layers.indexOf(selectedLayer);
            history.execute(new DeleteLayerCommand(this, layer, history));
            if (this.selectedLayer == layer) {
                if (currIndex == 0) {
                    this.selectedLayer = this.layers.get(currIndex);
                } else {
                    // when the last layer is removed
                    this.selectedLayer = this.layers.get(currIndex - 1);
                }
            }
        }
        notifyChange();
    }

    /**
     * Toggles the visibility of the specified layer.
     *
     * @param layerName the name of the layer to toggle visibility
     */
    public void switchLayerVisible(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        history.execute(new ChangeLayerVisibilityCommand(layers.get(layerIndex), this));
        notifyChange();
    }

    /**
     * Gets all layers in the model.
     *
     * @return a ArrayList of all layers
     */
    public ArrayList<PaintLayer> getLayers() {
        return layers;
    }

    /**
     * Gets the currently selected layer.
     *
     * @return the selected layer
     */
    public PaintLayer getSelectedLayer() {
        return selectedLayer;
    }

    /**
     * Sets the currently selected layer.
     *
     * @param selectedLayer the layer to set as selected
     */
    void setSelectedLayer(PaintLayer selectedLayer) {
        this.selectedLayer = selectedLayer;
        notifyChange();
    }

    /**
     * Adds a shape to the selected layer.
     *
     * @param shape the shape to add
     */
    public void addShape(Shape shape) {
        this.selectedLayer.addShape(shape);
        notifyChange();
    }

    /**
     * Adds a shape to the selected layer and records it for undo functionality.
     *
     * @param shape the shape to add
     */
    public void addShapeFinal(Shape shape) {
        history.execute(new AddShapeCommand(shape, this.getSelectedLayer(), history, this));
        notifyChange();
    }

    /**
     * Removes the specified shape from the selected layer.
     *
     * @param shape the shape to remove
     */
    public void removeShape(Shape shape) {
        this.selectedLayer.removeShape(shape);
        notifyChange();
    }

    /**
     * Sets the currently selected shape.
     *
     * @param selectedShape the shape to set as selected
     */
    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
        notifyChange();
    }

    /**
     * Gets the currently selected shape.
     *
     * @return the selected shape
     */
    public Shape getSelectedShape() {
        return this.selectedShape;
    }

    /**
     * Gets the current mode of the application.
     *
     * @return the current mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the current mode of the application.
     *
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
        notifyChange();
    }

    /**
     * Stores the current state in the command history for stroke eraser.
     */
    public void storeState() {
        history.execute(new StrokeEraserCommand(this.selectedLayer, history));
    }

    /**
     * Adds a stroke eraser to the selected layer.
     *
     * @param strokeEraser the stroke eraser to add
     */
    public void addStrokeEraser(StrokeEraser strokeEraser) {
        this.selectedLayer.addStrokeEraser(strokeEraser);
        notifyChange();
    }

    /**
     * Removes the stroke eraser from the selected layer.
     */
    public void removeStrokeEraser() {
        this.selectedLayer.removeStrokeEraser();
        notifyChange();
    }

    /**
     * Adds a selection tool to the selected layer.
     *
     * @param selectionTool the selection tool to add
     */
    public void addSelectionTool(SelectionTool selectionTool) {
        this.selectedLayer.addSelectionTool(selectionTool);
        notifyChange();
    }

    /**
     * Removes the selection tool from the selected layer.
     */
    public void removeSelectionTool() {
        this.selectedLayer.removeSelectionTool();
        notifyChange();
    }

    /**
     * Gets the selection tool associated with the selected layer.
     *
     * @return the selection tool
     */
    public SelectionTool getSelectionTool() {
        return this.selectedLayer.getSelectionTool();
    }

    /**
     * Starts a drag operation for the selected shapes.
     *
     * @param x the initial x-coordinate
     * @param y the initial y-coordinate
     */
    public void shiftStart(double x, double y) {
        dragCommand = new DragCommand(getSelectionTool().getSelectedShapes(), x, y, this.selectedLayer);
        history.execute(dragCommand);
    }

    /**
     * Adds a shift operation to the current drag command.
     *
     * @param x the x shift
     * @param y the y shift
     */
    public void addShift(double x, double y) {
        dragCommand.addShift(x, y);
    }

    /**
     * Undoes the last command.
     */
    public void undo() {
        history.undo();
        removeSelectionTool();
        notifyChange();
    }

    /**
     * Redoes the last undone command.
     */
    public void redo() {
        history.redo();
        notifyChange();
    }

    /**
     * Copies the selected shapes and selection tool to the clipboard.
     */
    public void copy() {
        if (getSelectionTool() != null) {
            this.copiedShapes = new ArrayList<>();
            for (Shape shape : getSelectionTool().getSelectedShapes()) {
                this.copiedShapes.add(shape.copy());
            }
            copiedSelectionTool = getSelectionTool().copy();
        }
    }

    /**
     * Pastes the copied shapes and selection tool into the selected layer.
     */
    public void paste() {
        if (copiedSelectionTool != null) {
            history.execute(new PasteCommand(this.selectedLayer, history, copiedShapes, copiedSelectionTool, this));
        }
        notifyChange();
    }

    /**
     * Cuts the selected shapes by copying and then deleting them.
     */
    public void cut() {
        copy();
        delete();
        notifyChange();
    }

    /**
     * Deletes the currently selected shapes.
     */
    public void delete() {
        if (getSelectionTool() != null) {
        history.execute(new DeleteSelectedCommand(this.selectedLayer, history, getSelectionTool().getSelectedShapes()));
        this.removeSelectionTool();
        }
        notifyChange();
    }

    /**
     * Gets the X coordinate of the canvas.
     */
    public double getCanvasX() {
        return canvasX;
    }

    /**
     * Gets the Y coordinate of the canvas.
     */
    public double getCanvasY() {
        return canvasY;
    }

    /**
     * Sets the canvas position.
     */
    public void setCanvasPosition(double x, double y) {
        this.canvasX = x;
        this.canvasY = y;
        notifyChange();
    }

    /**
     * Gets the width of the canvas.
     */
    public double getCanvasWidth() {
        return canvasWidth;
    }

    /**
     * Gets the height of the canvas.
     */
    public double getCanvasHeight() {
        return canvasHeight;
    }

    /**
     * Sets the canvas size.
     */
    public void setCanvasSize(double width, double height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        notifyChange();
    }

    /**
     * Gets the X coordinate of the mouse position.
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * Gets the Y coordinate of the mouse position.
     */
    public double getMouseY() {
        return mouseY;
    }

    /**
     * Sets the mouse position.
     */
    public void setMousePosition(double x, double y) {
        this.mouseX = x;
        this.mouseY = y;
        notifyChange();
    }

    /**
     * Gets the zoom factor.
     */
    public int getZoomFactor() {
        return this.zoomFactor;
    }

    /**
     * Sets the zoom factor and scales the canvas.
     */
    public void setZoomFactor(int zoomFactor, ResizableCanvas canvas) {
        this.zoomFactor = zoomFactor;
        canvas.scaleCanvas();
        notifyChange();
    }

    /**
     * Resets the paint model to its initial state.
     * <p>
     * This method performs the following actions:
     * - Clears all existing layers.
     * - Creates and adds a new default layer.
     * - Sets the newly created layer as the selected layer.
     * - Resets the history stack.
     * - Notifies observers about the change in the model.
     */
    public void resetAll() {
        this.layers.clear();
        PaintLayer layer = new PaintLayer();
        this.layers.add(layer);
        this.selectedLayer = layer;
        history.reset();
        notifyChange();
    }

    /**
     * Sets the background image for the currently selected layer.
     * If the provided image is not null, it sets the image as the background
     * of the selected layer and notifies observers of the change.
     *
     * @param image the background image to set for the selected layer
     */
    public void setBackground(Image image) {
        if (image != null) {
            this.selectedLayer.setBackground(image);
            notifyChange();
        }
    }

    /**
     * Executes a given command and notifies observers of any changes.
     *
     * @param command the command to execute
     */
    public void executeCommand(Command command) {
        this.history.execute(command);
        notifyChange();
    }

    /**
     * Aggregates all commands in the undo stack, excluding the initial AddLayerCommand, into a
     * single string with each command on a new line.
     *
     * @return A string representation of all commands in the undo stack, each command separated by a newline.
     */
    public String saveCommands() {
        // convert all commands to string
        StringBuilder allCommands = new StringBuilder();
        Stack<Command> undoStack = this.history.getUndoStack();

        // remove init AddLayerCommand, since it is automatically executed when starting the program
        for (Command command : undoStack.subList(1, undoStack.size())) {
            allCommands.append(command.toString());
            allCommands.append("\n");
        }
        return allCommands.toString();
    }

    /**
     * Notifies all observers of a change in the model.
     */
    public void notifyChange() {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the view associated with the model.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Gets the command history.
     */
    public CommandHistory getHistory() {
        return history;
    }
}
