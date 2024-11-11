package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.*;
import java.util.function.Consumer;

/**
 * PaintPanel is a custom JavaFX pane that serves as the primary drawing surface for the paint application.
 * It manages event handling for mouse actions, updates the canvas based on changes to the PaintModel,
 * and uses different strategies to draw or manipulate shapes according to the current mode.
 */
public class PaintPanel extends Pane implements EventHandler<MouseEvent>, Observer {
    private String mode;
    private final PaintModel model;
    private Shape shape;

    private ShapeStrategy strategy;
    private final ShapeFactory shapeFactory;
    private final StrategyFactory strategyFactory;
    final Map<EventType<MouseEvent>, Consumer<MouseEvent>> eventHandlers;
    private StrokeEraser strokeEraser;

    private Color color;

    /**
     * Constructs a PaintPanel with the specified PaintModel and initializes mouse event handling.
     *
     * @param model the PaintModel associated with this panel
     */
    public PaintPanel(PaintModel model) {

        setMinSize(100, 100);

        this.shapeFactory = new ShapeFactory();
        this.strategyFactory = new StrategyFactory();

        this.model = model;
        this.model.addObserver(this);

        // init layer
        this.model.addLayer();

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);

        this.eventHandlers = new HashMap<>();
        eventHandlers.put(MouseEvent.MOUSE_PRESSED, event -> strategy.mousePressed(event));
        eventHandlers.put(MouseEvent.MOUSE_DRAGGED, event -> strategy.mouseDragged(event));
        eventHandlers.put(MouseEvent.MOUSE_RELEASED, event -> strategy.mouseReleased(event));
    }

    /**
     * Returns the current drawing mode of the PaintPanel.
     *
     * @return the current mode as a String
     */
    public String getMode() {
        return mode;
    }

    /**
     * Returns the current fill style from the PaintModel.
     *
     * @return the fill style as a String
     */
    public String getFillStyle(){
        return model.getFillStyle();
    }

    /**
     * Gets the current line thickness for drawing shapes.
     *
     * @return the line thickness as a double
     */
    public double getLineThickness(){
        return model.getLineThickness();
    }

    /**
     * Sets the line thickness for shapes in the PaintModel.
     *
     * @param thickness the desired line thickness
     */
    public void setLineThickness(double thickness){
        model.setLineThickness(thickness);
    }

    /**
     * Returns the PaintModel associated with this PaintPanel.
     *
     * @return the PaintModel
     */
    public PaintModel getModel() {
        return model;
    }

    /**
     * Returns the currently active shape being drawn or manipulated.
     *
     * @return the current Shape object
     */
    public Shape getCurrentShape() {
        return shape;
    }

    /**
     * Resets the current shape to null, clearing any active shape in progress.
     */
    public void resetCurrentShape() {
        this.shape = null;
    }

    /**
     * Sets the current shape to the specified Shape.
     *
     * @param shape the Shape to set as the current shape
     */
    public void setCurrentShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Returns the current StrokeEraser tool.
     *
     * @return the StrokeEraser
     */
    public StrokeEraser getStrokeEraser() { return strokeEraser; }

    /**
     * Sets the StrokeEraser tool.
     *
     * @param strokeEraser the StrokeEraser to set
     */
    public void setStrokeEraser(StrokeEraser strokeEraser) { this.strokeEraser = strokeEraser; }

    /**
     * Returns the ShapeFactory instance for creating shapes.
     *
     * @return the ShapeFactory
     */
    public ShapeFactory getShapeFactory() {
        return shapeFactory;
    }

    /**
     * Returns the StrategyFactory instance for creating shape drawing strategies.
     *
     * @return the StrategyFactory
     */
    public StrategyFactory getStrategyFactory() {
        return strategyFactory;
    }

    /**
     * Sets the current color used for drawing shapes.
     *
     * @param color the Color to set for drawing
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the current color used for drawing shapes.
     *
     * @return the Color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Handles mouse events on the PaintPanel by delegating to the appropriate strategy
     * based on the event type and the current drawing mode.
     *
     * @param mouseEvent the MouseEvent to handle
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        this.strategy = strategyFactory.getStrategy(this.mode, this);

        if (this.strategy != null) {
            EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
            Consumer<MouseEvent> handler = eventHandlers.get(mouseEventType);

            if (handler != null) {
                handler.accept(mouseEvent);
            }

            model.setSelectedShape(shape);
        }
    }

    // Later when we learn about inner classes...
    // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

    /**
     * Updates the panel based on changes in the PaintModel, including redrawing layers.
     *
     * @param o the Observable object (PaintModel)
     * @param arg additional argument passed by the Observable
     */
    @Override
    public void update(Observable o, Object arg) {

        for (PaintLayer layer : this.model.getLayers()){
            if (layer.getWidth() < 100){layer.setWidth(100);}
            if (layer.getHeight() < 100){layer.setHeight(100);}
        }

        this.getChildren().setAll(this.model.getLayers());
        PaintModel model = (PaintModel) o;

        if (!Objects.equals(model.getMode(), this.mode)) {
            this.shape = null;  // finish previous shape
        }
        this.mode = model.getMode();
        this.color = model.getColor();

        for (PaintLayer layer : this.model.getLayers()) {
            GraphicsContext g2d = layer.getGraphicsContext2D();
            g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
            if (layer.isVisible()) {
                layer.display(g2d);
            }
        }
    }
}
