package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

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
        PaintLayer layer = new PaintLayer();
        history.execute(new AddLayerCommand(this, layer, history));
        this.selectedLayer = layer;
        notifyChange();
    }

    public void removeLayer(){
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
        history.execute(new ChangeLayerVisibilityCommand(layers.get(layerIndex)));
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
    }

    public void addShape(Shape shape) {
        this.selectedLayer.addShape(shape);
        notifyChange();
    }
    public void addShapeFinal(Shape shape) {
        history.execute(new AddShapeCommand(shape, this.getSelectedLayer(), history));
        notifyChange();
    }

    public void removeShape(Shape shape) {
        //history.execute(new DeleteShapeCommand(shape, this.getSelectedLayer(), history));
        this.selectedLayer.removeShape(shape);
        notifyChange();
    }

    public void setMode(String mode) {
        this.mode = mode;
        notifyChange();
    }

    public void storeState(){
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

    public void undo(){
        history.undo();
        notifyChange();
    }

    public void redo(){
        history.redo();
        notifyChange();
    }

    public void notifyChange(){
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
