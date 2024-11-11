package ca.utoronto.utm.assignment2.paint;

import java.util.Observable;
import java.util.Observer;

/**
 * A class that is responsible for handling layer selection and modification actions
 * in the PaintModel. It observes changes to the PaintModel and updates the
 * LayerChooserPanel accordingly.
 */
public class LayerChooserController implements Observer {

    private final PaintModel model;
    private final LayerChooserPanel panel;

    /**
     * Constructs a LayerChooserController to manage the interaction between
     * the given LayerChooserPanel and PaintModel.
     *
     * @param panel the LayerChooserPanel to update based on model changes
     * @param model the PaintModel to observe for layer changes
     */
    public LayerChooserController(LayerChooserPanel panel, PaintModel model) {
        this.model = model;
        this.model.addObserver(this);

        this.panel = panel;
    }

    /**
     * Selects a layer in the PaintModel, adding, removing, or switching visibility
     * as necessary.
     *
     * @param layerName the name of the layer to select or modify ("Layer+" to add,
     *                  "Layer-" to remove, or the name of an existing layer to select
     *                  or switch visibility)
     */
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

    /**
     * Updates the LayerChooserPanel by refreshing all its layers.
     *
     * @param o   the observable object, typically the PaintModel
     * @param arg an argument passed by the notifyObservers method, currently not used
     */
    @Override
    public void update(Observable o, Object arg) {
        this.panel.updateAllLayers();
    }
}
