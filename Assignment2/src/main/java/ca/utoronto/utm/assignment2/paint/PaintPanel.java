package ca.utoronto.utm.assignment2.paint;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private String mode = "Circle";
    private PaintModel model;

    private Shape shape;
    private ShapeStrategy strategy;
    private ShapeFactory shapeFactory;
    private StrategyFactory strategyFactory;

    public PaintPanel(PaintModel model) {
        super(300, 300);

        this.shapeFactory = new ShapeFactory();
        this.strategyFactory = new StrategyFactory();

        this.model = model;
        this.model.addObserver(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
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

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public ShapeFactory getShapeFactory() {
        return shapeFactory;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        this.strategy = strategyFactory.getStrategy(this.mode, this);
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            this.strategy.mousePressed(mouseEvent);

        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.strategy.mouseDragged(mouseEvent);

        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            this.strategy.mouseReleased(mouseEvent);
        }  // add more actions here
    }
    // Later when we learn about inner classes...
    // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

    @Override
    public void update(Observable o, Object arg) {

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Shape> allShapes = this.model.getAllShapes();

        for (Shape shape : allShapes) {
            g2d.setFill(shape.getColor());

            shape.display(g2d);
        }
    }
}
