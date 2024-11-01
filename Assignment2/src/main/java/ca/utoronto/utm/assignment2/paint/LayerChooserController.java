package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;

import java.util.Observable;
import java.util.Observer;

public class LayerChooserController implements Observer {

    private PaintModel model;
    private LayerChooserPanel panel;

    public LayerChooserController(LayerChooserPanel panel, PaintModel model) {
        this.model = model;
        this.model.addObserver(this);

        this.panel = panel;
    }

    public void selectLayer(String layerName) {
        if (layerName.equals("Layer+")) {
            // layer added
            this.model.addLayer(new PaintLayer());
        } else if (!this.model.selectLayer(layerName)){
            // layer hided/showed
            this.model.switchLayerVisible(layerName);
        }
    }

    public void addLayer(PaintLayer layer) {
        this.model.addLayer(layer);
    }

    public void removeLayer(PaintLayer layer) {
        this.model.removeLayer(layer);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.panel.updateAllLayers();
    }
}
