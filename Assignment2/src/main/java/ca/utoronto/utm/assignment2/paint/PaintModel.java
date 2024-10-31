package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
    private ArrayList<PaintLayer> layers = new ArrayList<>();
    // private ArrayList<Shape> allShapes = new ArrayList<>();
    private PaintLayer selectedLayer;

    public void selectLayer(PaintLayer layer) {
        this.selectedLayer = layer;
        this.setChanged();
        this.notifyObservers();
    }

    public void addLayer(PaintLayer layer) {
        this.layers.add(layer);
        this.selectedLayer = layer;
        this.setChanged();
        this.notifyObservers();
    }

    public void removeLayer(PaintLayer layer) {
        if (this.layers.size() <= 1) {
            // when there is only one layer, the user cannot remove it
            return;
        }
        this.layers.remove(layer);
        if (layer.equals(this.selectedLayer)) {
            // if the current layer is removed, automatically switch to the next layer
            this.selectedLayer = this.layers.getFirst();
        }
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
