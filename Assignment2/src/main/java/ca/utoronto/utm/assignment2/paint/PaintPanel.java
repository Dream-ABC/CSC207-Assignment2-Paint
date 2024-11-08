package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.function.Consumer;

public class PaintPanel extends Pane implements EventHandler<MouseEvent>, Observer {
    private String mode;
    private PaintModel model;
    private Shape shape;

    private ShapeStrategy strategy;
    private ShapeFactory shapeFactory;
    private StrategyFactory strategyFactory;
    Map<EventType<MouseEvent>, Consumer<MouseEvent>> eventHandlers;
    private Eraser eraser;
    private SelectionTool selection;

    private Color color;

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
        // add more mouse events here
    }

    public String getMode() {
        return mode;
    }

    public String getFillStyle(){
        return model.getFillStyle();
    }

    /**
     * Controller aspect of this
     */
    public void setMode(String mode) {
        this.mode = mode;
        System.out.println(this.mode);
    }

    public PaintModel getModel() {
        return model;
    }

    public Shape getCurrentShape() {
        return shape;
    }

    public void setCurrentShape(Shape shape) {
        this.shape = shape;
    }

    public Eraser getEraser() { return eraser; }

    public void setEraser(Eraser eraser) { this.eraser = eraser; }

    public SelectionTool getSelectionTool() {return selection;}

    public void setSelectionTool(SelectionTool selection) {this.selection = selection;}

    public ShapeFactory getShapeFactory() {
        return shapeFactory;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        this.strategy = strategyFactory.getStrategy(this.mode, this);

        if (this.strategy != null) {
            EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

            Consumer<MouseEvent> handler = eventHandlers.get(mouseEventType);
            if (handler != null) {
                handler.accept(mouseEvent);
            }
        }
    }

    // Later when we learn about inner classes...
    // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

    @Override
    public void update(Observable o, Object arg) {
        for (PaintLayer layer : this.model.getLayers()){
            if (layer.getWidth() < 100){layer.setWidth(100);}
            if (layer.getHeight() < 100){layer.setHeight(100);}
        }

        this.getChildren().setAll(this.model.getLayers());
        PaintModel model = (PaintModel) o;
        this.mode = model.getMode();

        for (PaintLayer layer : this.model.getLayers()) {
            GraphicsContext g2d = layer.getGraphicsContext2D();
            g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
            if (layer.isVisible()) {
                layer.display(g2d);
            }
        }
    }
}
