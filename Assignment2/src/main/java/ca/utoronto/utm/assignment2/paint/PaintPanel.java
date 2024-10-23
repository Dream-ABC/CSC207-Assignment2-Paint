package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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

    // public Point origin;
    // public Circle circle; // This is VERY UGLY, should somehow fix this!!
    // public Rectangle rectangle;
    // public Square square;
    // public Oval oval;

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

        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

    }

    @Override
    public void update(Observable o, Object arg) {
        // PROBLEM: since squiggles are printed before circles and circles are
        //          printed before rectangles, so rectangles will always be
        //          displayed above other shapes, no matter the order you draw it

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
//        // Draw Lines
//        // ArrayList<Point> points = this.model.getPoints();
//        ArrayList<Squiggle> squiggles = this.model.getPaths();
//
//        for (Squiggle path : squiggles) {
//            g2d.setFill(Color.RED);
//            for (int i = 0; i < path.getpoints().size() - 1; i++) {
//                Point p1 = path.getpoints().get(i);
//                Point p2 = path.getpoints().get(i + 1);
//                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
//            }
//        }
//
//        // Draw Circles
//        ArrayList<Circle> circles = this.model.getCircles();
//        ArrayList<Rectangle> rectangles = this.model.getRectangles();
//
//        g2d.setFill(Color.GREEN);
//        for (Circle c : circles) {
//            double x = c.getCentre().x;
//            double y = c.getCentre().y;
//            double radius = c.getRadius();
//            g2d.fillOval(x, y, radius, radius);
//        }
//
//        g2d.setFill(Color.BLUE);
//        for (Rectangle r : rectangles) {
//            double x = r.getTopLeft().x;
//            double y = r.getTopLeft().y;
//            double w = r.getWidth();
//            double h = r.getHeight();
//            g2d.fillRect(x, y, w, h);
//        }

        ArrayList<Shape> allShapes = this.model.getAllShapes();
        for (Shape shape : allShapes) {
            g2d.setFill(shape.getColour());
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
            }
        }
    }
}
