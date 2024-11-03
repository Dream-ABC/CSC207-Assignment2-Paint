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
        this.setChanged();
        this.notifyObservers();
        return true;
    }

    public void addLayer(boolean init) {
        PaintLayer layer = new PaintLayer();
        if (init) {
            this.layers.add(layer);
        } else {
            history.execute(new AddLayerCommand(this, layer));
        }
        this.selectedLayer = layer;
        this.setChanged();
        this.notifyObservers();
    }

    public void removeLayer() {
        if (this.layers.size() > 1) {
            // when there is only one layer, the user cannot remove it

            this.selectedLayer.setStatus("removed");
//            this.layers.remove(this.selectedLayer);
//            history.execute(new DeleteLayerCommand(this, this.selectedLayer));

            int currIndex = this.layers.indexOf(this.selectedLayer);

            if (currIndex == 0) {
                this.selectedLayer = this.layers.get(currIndex + 1);
            } else {
                // when the last layer is removed
                this.selectedLayer = this.layers.get(currIndex - 1);
            }
        }
        this.setChanged();
        this.notifyObservers();
    }




    public void switchLayerVisible(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        history.execute(new ChangeLayerVisibilityCommand(layers.get(layerIndex)));
        this.setChanged();
        this.notifyObservers();
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
        this.setChanged();
        this.notifyObservers();
    }
    public void addShapeFinal(Shape shape) {
        history.execute(new AddShapeCommand(shape, this.getSelectedLayer(), history));
        this.setChanged();
        this.notifyObservers();
    }

    public void removeShape(Shape shape) {
        //history.execute(new DeleteShapeCommand(shape, this.getSelectedLayer(), history));
        this.selectedLayer.removeShape(shape);
        this.setChanged();
        this.notifyObservers();
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.setChanged();
        this.notifyObservers();
    }

    public void storeState(){
        history.execute(new EraserStrokeCommand(this.selectedLayer, history));
    }

    public void addEraser(Eraser eraser) {
        this.selectedLayer.addEraser(eraser);
        this.setChanged();
        this.notifyObservers();
    }

    public void removeEraser() {
        //history.execute(new EraserStrokeCommand(removedShapes, history));
        this.selectedLayer.removeEraser();
        this.setChanged();
        this.notifyObservers();
    }

    public void undo(){
        history.undo();
        this.setChanged();
        this.notifyObservers();
    }

    public void redo(){
        history.redo();
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
