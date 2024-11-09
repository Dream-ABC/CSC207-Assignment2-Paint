package ca.utoronto.utm.assignment2.paint;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

public class PaintModel extends Observable {
    private ArrayList<PaintLayer> layers = new ArrayList<>();
    private PaintLayer selectedLayer;
    private String mode = "";

    private CommandHistory history = new CommandHistory();

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
        PaintLayer layer;
        if (this.selectedLayer == null) {
            layer = new PaintLayer();
        } else {
            layer = new PaintLayer(this.selectedLayer.getWidth(), this.selectedLayer.getHeight());
        }
        history.execute(new AddLayerCommand(this, layer, history));
        notifyChange();
    }

    public void removeLayer() {
        removeLayer(this.selectedLayer);
    }

    public void removeLayer(PaintLayer layer) {
        if (this.layers.size() > 1) {
            // when there is only one layer, the user cannot remove it

//            this.layers.remove(this.selectedLayer);
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

    public void setMode(String mode) {
        this.mode = mode;
        notifyChange();
    }

    public void storeState() {
        history.execute(new EraserStrokeCommand(this.selectedLayer, history));
    }

    public void addEraser(Eraser eraser) {
        this.selectedLayer.addEraser(eraser);
        notifyChange();
    }

    public void removeEraser() {
        //history.execute(new EraserStrokeCommand(removedShapes, history));
        this.selectedLayer.removeEraser();
        notifyChange();
    }

    public void undo() {
        history.undo();
        notifyChange();
    }

    public void redo() {
        history.redo();
        notifyChange();
    }

    /**
     * Resets the paint model to its initial state.
     *
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

        // remove init AddLayerCommand, since it is autometically executed when starting the program
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

    public CommandHistory getHistory() {
        return history;
    }

    public String getMode() {
        return mode;
    }
}
