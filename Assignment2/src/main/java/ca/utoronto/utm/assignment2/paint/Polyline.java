package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class Polyline implements Shape {
    private ArrayList<Point> points;
    private Color color;
    private boolean isClosed;
    private double lineThickness;

    /**
     * Constructs a default black polyline with no points.
     */
    public Polyline(double lineThickness) {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.isClosed = false;
        this.lineThickness = lineThickness;
    }

    /**
     * Gets the first point of the user's Polyline drawing
     * @return the first point of the polyline
     */
    public Point getFirst() {return this.points.getFirst();}

    /**
     * Gets the last point of the user's Polyline drawing
     * @return the last point of the polyline
     */
    public Point getLast() {return this.points.getLast();}

    /**
     * Adds a new point to the user's Polyline drawing.
     * @param p new point in Polyline
     */
    public void addPoint(Point p) {
        this.points.add(p);
    }

    /**
     * Removes the last point in the user's Polyline drawing if
     * it is not the only point in the user's Polyline drawing.
     */
    public void popPoint() {if (this.points.size() > 1) this.points.removeLast();}

    /**
     * @param closed closed status of Polyline
     */
    public void setClosed(boolean closed) {this.isClosed = closed;}

    /**
     * @return the color of the Polyline
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Polyline
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     */
    @Override
    public void setLineThickness(double lineThickness) {
        this.lineThickness = lineThickness;
    }

    /**
     * @return 'Polyline' as a string
     */
    @Override
    public String getShape() {
        return "Polyline";
    }

    /**
     * Checks if the Tool is overlapping the Polyline.
     * If it is, then the Tool will erase the entire Polyline.
     * @param tool the Tool instance which is currently erasing drawings
     * @return True if the Tool should erase this Polyline, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        double leftX = tool.getTopLeft().x-(tool.getDimensionX()/2.0);
        double rightX = tool.getTopLeft().x+(tool.getDimensionX()/2.0);
        double topY = tool.getTopLeft().y-(tool.getDimensionY()/2.0);
        double bottomY = tool.getTopLeft().y+(tool.getDimensionY()/2.0);
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.add(new Point(leftX, topY));
        allPoints.add(new Point(leftX, bottomY));
        allPoints.add(new Point(rightX, topY));
        allPoints.add(new Point(rightX, bottomY));
        allPoints.add(tool.getTopLeft());

        GeneralPath polygon1 = new GeneralPath();  // Create a new empty path (polygon)
        polygon1.moveTo(this.points.getFirst().x, this.points.getFirst().y);  // Move to the starting point (0, 0)
        for (int i = 1; i < this.points.size(); i++) {
            polygon1.lineTo(this.points.get(i).x, this.points.get(i).y);
        }
        polygon1.closePath();

        GeneralPath polygon2 = new GeneralPath();
        polygon2.moveTo(leftX, topY);
        polygon2.lineTo(rightX, topY);
        polygon2.lineTo(rightX, bottomY);
        polygon2.lineTo(leftX, bottomY);

        // Create Area objects for both polygons
        Area area1 = new Area(polygon1);
        Area area2 = new Area(polygon2);

        // Check if the polygons intersect
        area1.intersect(area2);

        // If the resulting area is not empty, the polygons intersect
        return !area1.isEmpty();
    }

    /**
     * Displays the Polyline with user-created color and points they drew.
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        if (this.isClosed) {
            double[] xPoints = new double[this.points.size()];
            double[] yPoints = new double[this.points.size()];
            for (int i = 0; i < this.points.size(); i++) {
                xPoints[i] = this.points.get(i).x;
                yPoints[i] = this.points.get(i).y;
            }
            g2d.setFill(this.color);
            g2d.setLineWidth(this.lineThickness);
            g2d.strokePolygon(xPoints, yPoints, this.points.size());
            g2d.fillPolygon(xPoints, yPoints, this.points.size());
        } else {
            for (int i = 0; i < this.points.size() - 1; i++) {
                Point p1 = this.points.get(i);
                Point p2 = this.points.get(i + 1);
                g2d.setStroke(this.color);
                g2d.setLineWidth(this.lineThickness);
                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}
