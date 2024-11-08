package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Polyline implements Shape {
    private ArrayList<Point> points;
    private Color color;
    private boolean isClosed;

    /**
     * Constructs a default black polyline with no points.
     */
    public Polyline() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.isClosed = false;
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
     * @return the stroke thickness of the Polyline
     */
    @Override
    public int getThickness() {
        return -1;
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
        int size = this.isClosed ? this.points.size() : this.points.size() - 1;
        for (int i = 0; i < size; i++) {
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i + 1);
            // finish this
            return true;
        }
        return false;
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
            g2d.fillPolygon(xPoints, yPoints, this.points.size());
        } else {
            for (int i = 0; i < this.points.size() - 1; i++) {
                Point p1 = this.points.get(i);
                Point p2 = this.points.get(i + 1);
                g2d.setStroke(this.color);
                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}
