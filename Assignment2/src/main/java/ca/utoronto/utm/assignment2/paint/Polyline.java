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
 * Polylines are represented using Points.
 */
public class Polyline implements Shape {
    private final ArrayList<Point> points;
    private Color color;
    private String fillStyle;
    private boolean isClosed;
    private double lineThickness;
    private ArrayList<Point> log;

    /**
     * Constructs a default black polyline with no points.
     * The line thickness is determined by the provided parameters.
     *
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Polyline(String fillStyle, double lineThickness) {
        this.points = new ArrayList<>();
        this.log = new ArrayList<>();
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
        this.lineThickness = lineThickness;
        this.isClosed = false;
    }

    /**
     * Gets the number points in the user's Polyline drawing.
     *
     * @return the number of Polyline points
     */
    public int getSize() {
        return points.size();
    }

    /**
     * Gets the first point of the user's Polyline drawing.
     *
     * @return the first point of the Polyline
     */
    public Point getFirst() {
        return this.points.getFirst();
    }

    /**
     * Gets the last point of the user's Polyline drawing.
     *
     * @return the last point of the Polyline
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
        this.log.add(p.copy());
    }

    /**
     * Removes the last point in the user's Polyline drawing if
     * it is not the only point in the user's Polyline drawing.
     */
    public void popPoint() {
        if (this.points.size() > 1) {
            this.points.removeLast();
            this.log.removeLast();
        }
    }

    /**
     * Sets the closed status of the Polyline.
     *
     * @param closed closed status of Polyline
     */
    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }

    /**
     * Returns the color of Polyline.
     *
     * @return the color of Polyline
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Polyline.
     *
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
        if (this.fillStyle.equals("Outline")) {
            for (int i = 0; i < this.points.size() - 1; i++) {
                if (checkBetweenPoints(points.get(i), points.get(i + 1), tool)) {
                    return true;
                }
            }
        }
        GeneralPath polygon1 = new GeneralPath();
        polygon1.moveTo(this.points.getFirst().x, this.points.getFirst().y);
        for (int i = 1; i < this.points.size(); i++) {
            polygon1.lineTo(this.points.get(i).x, this.points.get(i).y);
        }
        polygon1.closePath();

        float lineThickness = (float) this.lineThickness;
        BasicStroke thickStroke = new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        java.awt.Shape thickPolygonShape = thickStroke.createStrokedShape(polygon1);
        Area thickPolygonArea = new Area(thickPolygonShape);
        Area polylineArea = new Area(polygon1);

        double leftX = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double rightX = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double topY = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double bottomY = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);

        GeneralPath polygon2 = new GeneralPath();
        polygon2.moveTo(leftX, topY);
        polygon2.lineTo(rightX, topY);
        polygon2.lineTo(rightX, bottomY);
        polygon2.lineTo(leftX, bottomY);
        Area area2 = new Area(polygon2);

        thickPolygonArea.intersect(area2);
        return !thickPolygonArea.isEmpty() || polylineArea.contains(area2.getBounds2D());
    }

    /**
     * Checks if a line between two points intersects with a given tool's bounding box.
     * The line is sampled in small steps to determine if any point along it lies
     * within the tool's bounds, considering the tool's dimensions and the line thickness.
     *
     * @param p1 The first point defining the start of the line.
     * @param p2 The second point defining the end of the line.
     * @param tool The tool whose bounding box is checked for intersection with the line.
     * @return true if the line intersects the tool's bounding box, false otherwise.
     */
    private boolean checkBetweenPoints(Point p1, Point p2, Tool tool) {
        double leftX = tool.getTopLeft().x-(tool.getDimensionX()/2.0);
        double rightX = tool.getTopLeft().x+(tool.getDimensionX()/2.0);
        double topY = tool.getTopLeft().y-(tool.getDimensionY()/2.0);
        double bottomY = tool.getTopLeft().y+(tool.getDimensionY()/2.0);

        double thickness = (this.lineThickness / 2.0);

        int steps = 1000;
        double x = (p2.x - p1.x)/steps;
        double y = (p2.y - p1.y)/steps;

        for (int i = 0; i < steps; i++){
            double currX = p1.x + i*x;
            double currY = p1.y + i*y;

            if (leftX <= currX + thickness && currX - thickness <= rightX && topY <= currY+ thickness && currY - thickness <= bottomY){
                return true;
            }
        }
        return false;
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
        Polyline p = new Polyline(fillStyle, lineThickness);
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
     * @param g2d the GraphicsContext for the current layer used to draw the Polyline
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
            if (this.fillStyle.equals("Solid")) {
                g2d.setFill(this.color);
                g2d.fillPolygon(xPoints, yPoints, this.points.size());
            }
            else if (this.fillStyle.equals("Outline")) {
                g2d.setStroke(this.color);
                g2d.setLineWidth(this.lineThickness);
                g2d.strokePolygon(xPoints, yPoints, this.points.size());
            }
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
     * Sets the properties of the Polyline based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - whether the Polyline is closed or not
     *             data[1] - line thickness of the Polyline
     *             data[2] - color of the Polyline in web format
     *             data[3 and onwards] - Points of the Polyline as pairs of x and y coordinates
     */
    @Override
    public void setShape(String[] data) {
        if (!this.isClosed) {
            this.isClosed = Boolean.parseBoolean(data[0]);
        }
        this.lineThickness = Double.parseDouble(data[1]);
        this.color = Color.web(data[2]);
    }

    /**
     * Returns a string representation of the Polyline instance,
     * including whether the polyline is closed, line thickness, color,
     * and the list of points in the format "x,y", for each point.
     *
     * @return a string representation of the Polyline instance
     */
    public String toString() {
        StringBuilder log = new StringBuilder();
        for (Point p : this.log) {
            log.append(p.x).append(",").append(p.y).append(",");
        }
        return "Polyline{" + this.isClosed + "," + this.lineThickness + "," + this.color.toString() + ",;" + log + "}";
    }
}
