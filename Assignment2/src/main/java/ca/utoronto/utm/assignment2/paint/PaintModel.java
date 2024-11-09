package ca.utoronto.utm.assignment2.paint;

import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
    private final ArrayList<PaintLayer> layers = new ArrayList<>();
    private PaintLayer selectedLayer;
    private PaintPanel canvas;
    private String mode = "";
    private View view;
    private int zoomFactor;
    private String fillStyle = "Solid";
    private double thickness = 1.0;

    private final CommandHistory history = new CommandHistory();

    public PaintModel() {
        this.fillStyle = "Solid";
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

    public void setLineThickness(double thickness){
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

    public void removeLayer(){
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
        this.selectedLayer.removeShape(shape);
        notifyChange();
    }

    public void setMode(String mode) {
        this.mode = mode;
        notifyChange();
    }

    public void storeState(){
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

    public void undo(){
        history.undo();
        notifyChange();
    }

    public void redo(){
        history.redo();
        notifyChange();
    }

    public void setZoomFactor(int zoomFactor, ResizableCanvas canvas) {
        this.zoomFactor = zoomFactor;
        canvas.scaleCanvas(zoomFactor);
        notifyChange();
    }

    public int getZoomFactor(){
        return this.zoomFactor;
    }

    public void notifyChange(){
        this.setChanged();
        this.notifyObservers();
    }

    public void setView(View view){
        this.view = view;
    }

    public CommandHistory getHistory() {
        return history;
    }


    public String getMode() {
        return mode;
    }
}
