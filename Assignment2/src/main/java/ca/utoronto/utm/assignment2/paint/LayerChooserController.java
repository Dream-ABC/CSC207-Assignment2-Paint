package ca.utoronto.utm.assignment2.paint;

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
        this.model.removeSelectionTool();
        if (layerName.equals("Layer+")) {
            // layer added
            this.model.addLayer();
        } else if (layerName.equals("Layer-")) {
            // layer removed
            this.model.removeLayer();
        } else if (!this.model.selectLayer(layerName)) {
            // layer hided/showed
            this.model.switchLayerVisible(layerName);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.panel.updateAllLayers();
    }
}
