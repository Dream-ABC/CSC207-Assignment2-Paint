package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.function.Consumer;

public class PaintPanel extends Pane implements EventHandler<MouseEvent>, Observer {
    private String mode = "Circle";
    private PaintModel model;
    private Shape shape;
    private ShapeStrategy strategy;
    private ShapeFactory shapeFactory;
    private StrategyFactory strategyFactory;
    Map<EventType<MouseEvent>, Consumer<MouseEvent>> eventHandlers;

    public PaintPanel(PaintModel model) {
        // super(300, 300);

        this.shapeFactory = new ShapeFactory();
        this.strategyFactory = new StrategyFactory();

        this.model = model;
        this.model.addObserver(this);

        PaintLayer initLayer = new PaintLayer(300, 300);
        this.model.addLayer(initLayer);
        this.model.selectLayer(initLayer);

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

    public void switchLayer(PaintLayer layer) {
        this.model.selectLayer(layer);
    }

    public void addLayer(PaintLayer layer) {
        this.model.addLayer(layer);
    }

    public void removeLayer(PaintLayer layer) {
        this.model.removeLayer(layer);
    }

    public Shape getCurrentShape() {
        return shape;
    }

    public void setCurrentShape(Shape shape) {
        this.shape = shape;
    }

    public ShapeFactory getShapeFactory() {
        return shapeFactory;
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
        System.out.println("ready to update");
        this.getChildren().setAll(this.model.getLayers());

//        GraphicsContext g2d = this.getGraphicsContext2D();
//        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

//        ArrayList<Shape> allShapes = this.model.getAllShapes();
//
//        for (Shape shape : allShapes) {
//            g2d.setFill(shape.getColor());
//            shape.display(g2d);
//        }

        System.out.println("layers:"+this.model.getLayers());

        for (PaintLayer layer : this.model.getLayers()) {
            layer.display();
        }
    }
}
