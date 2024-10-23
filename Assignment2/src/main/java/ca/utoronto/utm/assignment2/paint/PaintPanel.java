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
    private String mode="Circle";
    private PaintModel model;

    public Point origin;
    public Circle circle; // This is VERY UGLY, should somehow fix this!!
    public Rectangle rectangle;
    public Oval oval;

    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model=model;
        this.model.addObserver(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }
    /**
     *  Controller aspect of this
     */
    public void setMode(String mode){
        this.mode=mode;
        System.out.println(this.mode);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        // "Circle", "Rectangle", "Square", "Squiggle", "Polyline"
        switch(this.mode){
            case "Circle":
                if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Circle");
                    Point centre = new Point(mouseEvent.getX(), mouseEvent.getY());
                    this.circle = new Circle(centre, 0);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    double dx = this.circle.getCentre().x-mouseEvent.getX();
                    double dy = this.circle.getCentre().y-mouseEvent.getY();
                    double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    Point oldCentre = this.circle.getCentre();
                    double newX = this.circle.getCentre().x-radius;
                    double newY = this.circle.getCentre().y-radius;
                    Point newCentre = new Point(newX, newY);
                    this.circle.setCentre(newCentre);
                    this.circle.setRadius(radius*2);
                    this.model.addCircle(this.circle);
                    this.circle.setCentre(oldCentre);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if(this.circle!=null){
                        // Problematic notion of radius and centre!!
                        double dx = this.circle.getCentre().x-mouseEvent.getX();
                        double dy = this.circle.getCentre().y-mouseEvent.getY();
                        double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                        double newX = this.circle.getCentre().x-radius;
                        double newY = this.circle.getCentre().y-radius;
                        Point newCentre = new Point(newX, newY);
                        this.circle.setCentre(newCentre);
                        this.circle.setRadius(radius*2);
                        this.model.addCircle(this.circle);
                        System.out.println("Added Circle");
                        this.circle=null;
                    }
                }

                break;
            case "Rectangle":
                if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Rectangle");
                    Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
                    this.origin = new Point(topLeft.x, topLeft.y);
                    this.rectangle=new Rectangle(topLeft, 0, 0);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    double width = Math.abs(this.origin.x-mouseEvent.getX());
                    double height = Math.abs(this.origin.y-mouseEvent.getY());
                    double x = Math.min(this.origin.x, mouseEvent.getX());
                    double y = Math.min(this.origin.y, mouseEvent.getY());
                    this.rectangle.setTopLeft(new Point(x, y));
                    this.rectangle.setWidth(width);
                    this.rectangle.setHeight(height);
                    this.model.addRectangle(this.rectangle);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if(this.rectangle!=null){
                        double width = Math.abs(this.origin.x-mouseEvent.getX());
                        double height = Math.abs(this.origin.y-mouseEvent.getY());
                        double x = Math.min(this.rectangle.getTopLeft().x, mouseEvent.getX());
                        double y = Math.min(this.rectangle.getTopLeft().y, mouseEvent.getY());
                        this.rectangle.setTopLeft(new Point(x, y));
                        this.rectangle.setWidth(width);
                        this.rectangle.setHeight(height);
                        this.model.addRectangle(this.rectangle);
                        System.out.println("Added Rectangle");
                        this.rectangle=null;
                    }
                }
                break;
            case "Square": break;
            case "Squiggle":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    this.model.addPath();
                    this.model.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));

                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    this.model.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    this.model.finishPath();
                }
                break;
            case "Polyline": break;
            default: break;
            case "Oval":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    Point topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
                    this.oval = new Oval(topLeft, topLeft, 0, 0);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    double newWidth = Math.abs(this.oval.getOrigin().x-mouseEvent.getX());
                    double newHeight = Math.abs(this.oval.getOrigin().y-mouseEvent.getY());
                    this.oval.setWidth(newWidth);
                    this.oval.setHeight(newHeight);
                    double x = Math.min(this.oval.getOrigin().x, mouseEvent.getX());
                    double y = Math.min(this.oval.getOrigin().y, mouseEvent.getY());
                    this.oval.setTopLeft(new Point(x, y));
                    this.model.addOval(this.oval);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    double newWidth = Math.abs(this.oval.getOrigin().x-mouseEvent.getX());
                    double newHeight = Math.abs(this.oval.getOrigin().y-mouseEvent.getY());
                    this.oval.setWidth(newWidth);
                    this.oval.setHeight(newHeight);
                    double x = Math.min(this.oval.getOrigin().x, mouseEvent.getX());
                    double y = Math.min(this.oval.getOrigin().y, mouseEvent.getY());
                    this.oval.setTopLeft(new Point(x, y));
                    this.model.addOval(this.oval);
                    this.oval=null;
                }
                break;
        }
    }
    @Override
    public void update(Observable o, Object arg) {

                GraphicsContext g2d = this.getGraphicsContext2D();
                g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
                // Draw Lines
                // ArrayList<Point> points = this.model.getPoints();
                ArrayList<ArrayList<Point>> paths = this.model.getPaths();

                for (ArrayList<Point> points : paths) {

                    g2d.setFill(Color.RED);
                    for (int i = 0; i < points.size() - 1; i++) {
                        Point p1 = points.get(i);
                        Point p2 = points.get(i + 1);
                        g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }

                // Draw Circles
                ArrayList<Circle> circles = this.model.getCircles();
                ArrayList<Rectangle> rectangles = this.model.getRectangles();
                ArrayList<Oval> ovals = this.model.getOvals();

                g2d.setFill(Color.GREEN);
                for(Circle c: this.model.getCircles()){
                        double x = c.getCentre().x;
                        double y = c.getCentre().y;
                        double radius = c.getRadius();
                        g2d.fillOval(x, y, radius, radius);
                }

                g2d.setFill(Color.BLUE);
                for(Rectangle r: this.model.getRectangles()){
                    double x = r.getTopLeft().x;
                    double y = r.getTopLeft().y;
                    double w = r.getWidth();
                    double h = r.getHeight();
                    g2d.fillRect(x, y, w, h);
                }

                g2d.setFill(Color.PURPLE);
                for(Oval oval: this.model.getOvals()){
                    double x = oval.getTopLeft().x;
                    double y = oval.getTopLeft().y;
                    double width = oval.getWidth();
                    double height = oval.getHeight();
                    g2d.fillOval(x, y, width, height);
                }
    }
}
