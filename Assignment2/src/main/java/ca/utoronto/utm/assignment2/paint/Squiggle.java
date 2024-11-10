package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent drawing squiggles.
 * Squiggle implements the Shape interface.
 * Squiggles are represented using Points.
 */
public class Squiggle implements Shape {
    private final ArrayList<Point> points;
    private Color color;
    private double lineThickness;
    private ArrayList<Point> originalPosition;

    /**
     * Constructs a default black squiggle with no points.
     * The line thickness is determined by the provided parameters.
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Squiggle(double lineThickness) {
        this.points = new ArrayList<>();
        this.originalPosition = new ArrayList<>();
        this.color = Color.BLACK;
        this.lineThickness = lineThickness;
    }

    /**
     * Adds a new point to the user's squiggle drawing.
     *
     * @param p new point in Squiggle
     */
    public void addPoint(Point p) {
        this.points.add(p);
        this.originalPosition.add(p.copy());
    }

    /**
     * Returns the color of Squiggle.
     * @return the color of Squiggle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of Squiggle.
     * @param color color of Squiggle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks if the Tool is overlapping the Squiggle.
     *
     * @param tool the Tool instance which is currently checking for overlaps
     * @return True if the tool finds an overlap, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        for (int i = 0; i < this.points.size() - 1; i++) {
            if (checkBetweenPoints(points.get(i), points.get(i+1), tool)) {return true;}
        }
        return false;

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
     * Shifts all points of the Squiggle by the specified horizontal and vertical offsets.
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
     * Creates a copy of the Squiggle instance.
     *
     * @return a copy of the Squiggle instance
     */
    public Squiggle copy(){
        Squiggle s = new Squiggle(this.lineThickness);
        s.setColor(this.color);
        for (Point p : this.points) {
            s.addPoint(p.copy());
        }
        return s;
    }

    /**
     * Displays the Squiggle with user-created color, line thickness, and points they drew.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the Squiggle
     */
    @Override
    public void display(GraphicsContext g2d) {
        g2d.setLineDashes();
        g2d.setFill(this.color);
        g2d.setStroke(this.color);
        g2d.setLineWidth(this.lineThickness);

        Point start = this.points.getFirst();
        g2d.beginPath();
        g2d.moveTo(start.x, start.y);

        for (int i = 0; i <= this.points.size() - 4; i += 3) {
            Point control1 = this.points.get(i + 1);
            Point control2 = this.points.get(i + 2);
            Point end = this.points.get(i + 3);
            g2d.bezierCurveTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);
        }

        g2d.stroke();
        g2d.closePath();
    }

    /**
     * Sets the properties of the Squiggle based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - line thickness of the Squiggle
     *             data[1] - color of the Squiggle in web format
     *             data[2 and onwards] - points of the Squiggle as pairs of x and y coordinates
     */
    @Override
    public void setShape(String[] data) {
        this.lineThickness = Double.parseDouble(data[0]);
        this.color = Color.web(data[1]);

        for (int i = 2; i < data.length; i += 2) {
            double x = Double.parseDouble(data[i]);
            double y = Double.parseDouble(data[i + 1]);
            this.points.add(new Point(x, y));
        }
    }

    /**
     * Returns a string representation of the Squiggle instance, including its
     * line thickness, color, and coordinates of points in "x,y" format.
     *
     * @return a string representation of the Squiggle instance
     */
    public String toString() {
        StringBuilder points = new StringBuilder();
        for (Point p : this.originalPosition) {
            points.append(p.x + "," + p.y + ",");
        }
        return "Squiggle{" + this.lineThickness + "," + this.color.toString() + "," + points + "}";
    }
}
