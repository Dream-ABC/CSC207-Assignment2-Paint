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
    private ShapeFactory shapeFactory = new ShapeFactory();
    private StrategyFactory strategyFactory = new StrategyFactory();

    public PaintPanel(PaintModel model) {
        super(300, 300);
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

    public PaintModel getModel() {
        return model;
    }

    public Shape getShape() {
        return shape;
    }

    public ShapeStrategy getStrategy() {
        return strategy;
    }

    public ShapeFactory getShapeFactory() {
        return shapeFactory;
    }

    public StrategyFactory getStrategyFactory() {
        return strategyFactory;
    }

    /**
     * Controller aspect of this
     */
    public void setMode(String mode) {
        this.mode = mode;
        System.out.println(this.mode);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setStrategy(String shapeName) {
        this.strategy = strategyFactory.getStrategy(shapeName, this);
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

            switch (shape.getShape().toLowerCase()) {  // ignore case
                case "circle":
                    Circle c = (Circle) shape;
                    double cx = c.getCentre().x;
                    double cy = c.getCentre().y;
                    double radius = c.getRadius();
                    g2d.fillOval(cx, cy, radius, radius);
                    break;

                case "rectangle":
                    Rectangle r = (Rectangle) shape;
                    double rx = r.getTopLeft().x;
                    double ry = r.getTopLeft().y;
                    double w = r.getWidth();
                    double h = r.getHeight();
                    g2d.fillRect(rx, ry, w, h);
                    break;

                case "squiggle":
                    Squiggle sq = (Squiggle) shape;
                    for (int i = 0; i < sq.getpoints().size() - 1; i++) {
                        Point p1 = sq.getpoints().get(i);
                        Point p2 = sq.getpoints().get(i + 1);
                        g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
                    }
                    break;

                case "square":
                    Square s = (Square) shape;
                    double sx = s.getTopLeft().x;
                    double sy = s.getTopLeft().y;
                    double sd = s.getSide();
                    g2d.fillRect(sx, sy, sd, sd);
                    break;

                case "oval":
                    Oval oval = (Oval) shape;
                    double ox = oval.getTopLeft().x;
                    double oy = oval.getTopLeft().y;
                    double width = oval.getWidth();
                    double height = oval.getHeight();
                    g2d.fillOval(ox, oy, width, height);
                    break;

                case "triangle":
                    Triangle triangle = (Triangle) shape;
                    ObservableList<Double> points = triangle.getPoints();
                    double[] xPoints = new double[3];
                    double[] yPoints = new double[3];
                    for (int i = 0; i < 3; i++) {
                        xPoints[i] = points.get(i);
                        yPoints[i] = points.get(i + 3);
                    }
                    g2d.fillPolygon(xPoints, yPoints, 3);
                    break;
            }
        }
    }
}
