package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class to represent drawing circles.
 * Circle implements the Shape interface.
 */
public class Circle implements Shape{
        private Point topLeft;
        private Point centre;
        private double diameter;
        private Color color;
        private final String fillStyle;

        /**
         * Constructs a default black circle with a diameter of 0.
         */
        public Circle(String fillStyle) {
                this.diameter = 0;
                this.color = Color.BLACK;
                this.fillStyle = fillStyle;
        }

    /**
     * @param centre centre of Circle
     */
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    /**
     * @param topLeft top left corner of Circle
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the centre of the Circle.
     */
    public Point getCentre() {
        return this.centre;
    }

    /**
     * @param diameter diameter of Circle
     */
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    /**
     * @return the color of the Circle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Circle
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the stroke thickness of the Circle
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Circle' as a string
     */
    @Override
    public String getShape() {
        return "Circle";
    }

        /**
         * Checks if the Eraser is overlapping the Circle.
         * If it is, then the Eraser will erase the Circle.
         * @param tool the tool instance which is currently checking for overlaps
         * @return True if the tool should find an overlap, False otherwise
         */
        @Override
        public boolean overlaps(Tool tool) {
                double centerX = topLeft.x + (diameter / 2);
                double centerY = topLeft.y + (diameter / 2);
                double radius = diameter / 2;

                double rectLeft = tool.getCentre().x - (tool.getDimensionX() / 2);
                double rectRight = tool.getCentre().x + (tool.getDimensionX() / 2);
                double rectTop = tool.getCentre().y - (tool.getDimensionY() / 2);
                double rectBottom = tool.getCentre().y + (tool.getDimensionY() / 2);

                double closestX = clamp(centerX, rectLeft, rectRight);
                double closestY = clamp(centerY, rectTop, rectBottom);

                double distanceX = (centerX - closestX) / radius;
                double distanceY = (centerY - closestY) / radius;
                double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

                return distanceSquared <= 1;
        }
        private double clamp(double value, double min, double max) {
                return Math.max(min, Math.min(max, value));
        }

        /**
         * Displays the Circle with user-created color and size.
         * @param g2d GraphicsContext
         */
        @Override
        public void display(GraphicsContext g2d) {
                if (this.fillStyle.equals("Solid")){
                        g2d.setFill(this.color);
                        g2d.fillOval(this.topLeft.x, this.topLeft.y,
                                this.diameter, this.diameter);
                }
                else if (this.fillStyle.equals("Outline")){
                        g2d.setStroke(this.color); // Set the color for the outline
//                        g2d.setLineWidth(2); // Set the thickness of the outline (optional)
                        g2d.strokeOval(this.topLeft.x, this.topLeft.y, this.diameter, this.diameter);
                }
        }
}
