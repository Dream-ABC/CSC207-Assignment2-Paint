package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 * A class to represent drawing polylines.
 * Polyline implements the Shape interface.
 */
public class Polyline implements Shape {
    private final ArrayList<Point> points;
    private Color color;
    private boolean isClosed;
    private double lineThickness;

    /**
     * Constructs a default black polyline with no points.
     * The line thickness is determined by the provided parameters.
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Polyline(double lineThickness) {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.isClosed = false;
        this.lineThickness = lineThickness;
    }

    /**
     * Gets the number points in the user's Polyline drawing
     *
     * @return number of polyline points
     */
    public int getSize() {
        return points.size();
    }

    /**
     * Gets the first point of the user's Polyline drawing
     *
     * @return the first point of the polyline
     */
    public Point getFirst() {
        return this.points.getFirst();
    }

    /**
     * Gets the last point of the user's Polyline drawing
     *
     * @return the last point of the polyline
     */
    public Point getLast() {
        return this.points.getLast();
    }

    /**
     * Adds a new point to the user's Polyline drawing.
     *
     * @param p new point in Polyline
     */
    public void addPoint(Point p) {
        this.points.add(p);
    }

    /**
     * Removes the last point in the user's Polyline drawing if
     * it is not the only point in the user's Polyline drawing.
     */
    public void popPoint() {
        if (this.points.size() > 1) this.points.removeLast();
    }

    /**
     * @param closed closed status of Polyline
     */
    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }

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
     * Checks if the Tool is overlapping the Polyline.
     *
     * @param tool the Tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        double leftX = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double rightX = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double topY = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double bottomY = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);

        // Create a polygon based on the polyline
        GeneralPath polygon1 = new GeneralPath();
        polygon1.moveTo(this.points.getFirst().x, this.points.getFirst().y);  // Move to the starting point (0, 0)
        for (int i = 1; i < this.points.size(); i++) {
            polygon1.lineTo(this.points.get(i).x, this.points.get(i).y);
        }
        polygon1.closePath();

        // Create an accurate version of the polygon shape with the line thickness needed
        float lineThickness = (float) this.lineThickness;
        BasicStroke thickStroke = new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        java.awt.Shape thickPolygonShape = thickStroke.createStrokedShape(polygon1);
        Area thickPolygonArea = new Area(thickPolygonShape);

        // Create a polygon based on the eraser
        GeneralPath polygon2 = new GeneralPath();
        polygon2.moveTo(leftX, topY);
        polygon2.lineTo(rightX, topY);
        polygon2.lineTo(rightX, bottomY);
        polygon2.lineTo(leftX, bottomY);
        Area area2 = new Area(polygon2);

        // If the resulting area is not empty, the polygons intersect
        thickPolygonArea.intersect(area2);
        return !thickPolygonArea.isEmpty();
    }

    /**
     * Shifts all points of the Polyline by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        for (Point p : this.points) {
            p.shift(x, y);
        }
    }

    /**
     * Creates a copy of the Polyline instance.
     *
     * @return a copy of the Polyline instance
     */
    @Override
    public Polyline copy() {
        Polyline p = new Polyline(lineThickness);
        p.setColor(this.color);
        p.setClosed(this.isClosed);
        for (Point p1 : this.points) {
            p.addPoint(p1.copy());
        }
        return p;
    }

    /**
     * Displays the Polyline with user-created color, line thickness, and points they drew.
     *
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
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

    /**
     * Sets the properties of the Oval shape based on the provided data array.
     *
     * @param data an array should contain the following elements in order:
     *             <p>data[0] - whether the Polyline is closed or not</p>
     *             <p>data[1] - line thickness of the Polyline</p>
     *             <p>data[2] - color of the Polyline in web format</p>
     *             <p>data[i, i+1] - the x and y coordinates of a Point in the Polyline</p>
     */
    @Override
    public void setShape(String[] data) {
        this.isClosed = Boolean.parseBoolean(data[0]);
        this.lineThickness = Double.parseDouble(data[1]);
        this.color = Color.web(data[2]);

        // set points
        for (int i = 3; i < data.length; i += 2) {
            double x = Double.parseDouble(data[i]);
            double y = Double.parseDouble(data[i + 1]);
            this.points.add(new Point(x, y));
        }
    }

    /**
     * Returns a string representation of a polyline.
     * @return a string representation of the polyline
     */
    public String toString() {
        // get all points
        StringBuilder points = new StringBuilder();
        for (Point p : this.points) {
            points.append(p.x + "," + p.y + ",");
        }

        return "Polyline{" + this.isClosed + "," + this.lineThickness + ","
                + this.color.toString() + "," + points + "}";
    }
}
