package ca.utoronto.utm.assignment2.paint;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

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

    private final CommandHistory history = new CommandHistory();

    public PaintModel() {
        this.selectedShape = null;
        this.mode = "";
        this.canvasWidth = 700;
        this.canvasHeight = 400;
        this.fillStyle = "Solid";
        this.thickness = 1.0;
        this.zoomFactor = 100;
    }

    public String getFillStyle() {
        return this.fillStyle;
    }

    public void setFillStyle(String fillStyle) {
        this.fillStyle = fillStyle;
        notifyChange();
    }

    public double getLineThickness() {
        return this.thickness;
    }

    public void setLineThickness(double thickness) {
        this.thickness = thickness;
    }

    public boolean selectLayer(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        if (layerIndex == layers.indexOf(this.selectedLayer)) {
            return false;  // nothing changed
        }
        history.execute(new ChangeLayerCommand(this.selectedLayer, layers.get(layerIndex), this));
        notifyChange();
        return true;
    }

    public void addLayer() {
        PaintLayer layer = new PaintLayer();
        history.execute(new AddLayerCommand(this, layer, history));
        this.selectedLayer = layer;
        notifyChange();
    }

    public void removeLayer() {
        removeLayer(this.selectedLayer);
    }

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

    public void switchLayerVisible(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        history.execute(new ChangeLayerVisibilityCommand(layers.get(layerIndex), this));
        notifyChange();
    }

    public ArrayList<PaintLayer> getLayers() {
        return layers;
    }

    public PaintLayer getSelectedLayer() {
        return selectedLayer;
    }

    void setSelectedLayer(PaintLayer selectedLayer) {
        this.selectedLayer = selectedLayer;
        notifyChange();
    }

    public void addShape(Shape shape) {
        this.selectedLayer.addShape(shape);
        notifyChange();
    }

    public void addShapeFinal(Shape shape) {
        history.execute(new AddShapeCommand(shape, this.getSelectedLayer(), history, this));
        notifyChange();
    }

    public void removeShape(Shape shape) {
        this.selectedLayer.removeShape(shape);
        notifyChange();
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
        notifyChange();
    }

    public Shape getSelectedShape() {
        return this.selectedShape;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
        notifyChange();
    }

    public void storeState() {
        history.execute(new StrokeEraserCommand(this.selectedLayer, history));
    }

    public void addStrokeEraser(StrokeEraser strokeEraser) {
        this.selectedLayer.addStrokeEraser(strokeEraser);
        notifyChange();
    }

    public void removeStrokeEraser() {
        this.selectedLayer.removeStrokeEraser();
        notifyChange();
    }

    public void addSelectionTool(SelectionTool selectionTool) {
        this.selectedLayer.addSelectionTool(selectionTool);
        notifyChange();
    }

    public void removeSelectionTool() {
        this.selectedLayer.removeSelectionTool();
        notifyChange();
    }

    public SelectionTool getSelectionTool() {
        return this.selectedLayer.getSelectionTool();
    }

    public void undo() {
        history.undo();
        removeSelectionTool();
        notifyChange();
    }

    public void redo() {
        history.redo();
        notifyChange();
    }

    public void copy() {
        if (getSelectionTool() != null) {
            this.copiedShapes = new ArrayList<>();
            for (Shape shape : getSelectionTool().getSelectedShapes()) {
                this.copiedShapes.add(shape.copy());
            }
            copiedSelectionTool = getSelectionTool().copy();
        }
    }

    public void paste() {
        if (copiedSelectionTool != null) {
            history.execute(new PasteCommand(this.selectedLayer, history, copiedShapes, copiedSelectionTool, this));
        }
        notifyChange();
    }

    public void cut() {
        copy();
        delete();
        notifyChange();
    }

    public void delete() {
        if (getSelectionTool() != null) {
        history.execute(new DeleteSelectedCommand(this.selectedLayer, history, getSelectionTool().getSelectedShapes()));
        this.removeSelectionTool();
        }
        notifyChange();
    }


    public double getCanvasX() {
        return canvasX;
    }

    public double getCanvasY() {
        return canvasY;
    }

    public void setCanvasPosition(double x, double y) {
        this.canvasX = x;
        this.canvasY = y;
        notifyChange();
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public void setCanvasSize(double width, double height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        notifyChange();
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMousePosition(double x, double y) {
        this.mouseX = x;
        this.mouseY = y;
        notifyChange();
    }

    public int getZoomFactor() {
        return this.zoomFactor;
    }

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

    public void notifyChange() {
        this.setChanged();
        this.notifyObservers();
    }

    public void setView(View view) {
        this.view = view;
    }

    public CommandHistory getHistory() {
        return history;
    }
}
