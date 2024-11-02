package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
    private ArrayList<PaintLayer> layers = new ArrayList<>();
    // private ArrayList<Shape> allShapes = new ArrayList<>();
    private PaintLayer selectedLayer;

    public boolean selectLayer(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        if (layerIndex == layers.indexOf(this.selectedLayer)) {
            return false;  // nothing changed
        }
        this.selectedLayer = layers.get(layerIndex);
        this.setChanged();
        this.notifyObservers();
        return true;
    }

    public void addLayer(PaintLayer layer) {
        this.layers.add(layer);
        this.selectedLayer = layer;
        this.setChanged();
        this.notifyObservers();
    }

    public void removeLayer() {
        if (this.layers.size() <= 1) {
            // when there is only one layer, the user cannot remove it
            return;
        }
        int currIndex = this.layers.indexOf(this.selectedLayer);
        this.layers.remove(this.selectedLayer);

        if (currIndex <= this.layers.size() - 1) {
            this.selectedLayer = this.layers.get(currIndex);
        } else {
            // when the last layer is removed
            this.selectedLayer = this.layers.get(currIndex - 1);
        }

        this.setChanged();
        this.notifyObservers();
    }

    public void switchLayerVisible(String layerName) {
        int layerIndex = Integer.parseInt(layerName.substring(5));
        layers.get(layerIndex).switchVisible();
        this.setChanged();
        this.notifyObservers();
    }

    public void notifyUpdate() {
        this.setChanged();
        this.notifyObservers();
    }

    public ArrayList<PaintLayer> getLayers() {
        return layers;
    }

    public PaintLayer getSelectedLayer() {
        return selectedLayer;
    }

    public void addShape(Shape shape) {
        // this.allShapes.add(shape);
        this.selectedLayer.addShape(shape);
        this.setChanged();
        this.notifyObservers();
    }

//        public ArrayList<Shape> getAllShapes(){
//                return allShapes;
//        }
}
