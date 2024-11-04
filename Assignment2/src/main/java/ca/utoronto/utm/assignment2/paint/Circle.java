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

        /**
         * Constructs a default black circle with a diameter of 0 and full opaqueness.
         */
        public Circle() {
                this.diameter = 0;
                this.color = Color.BLACK;
        }

        /**
         * Constructs a circle user defined centre and diameter.
         * The default state of a Circle is black with full opaqueness.
         */
        public Circle(Point centre, double diameter){
                this.centre = centre;
                this.topLeft = centre;
                this.diameter = diameter;
                this.color = Color.BLACK;
        }

        /**
         * Sets the centre of the Circle.
         * @param centre
         */
        public void setCentre(Point centre) {
                this.centre = centre;
        }

        /**
         * Sets the top left point of the Circle.
         * @param topLeft
         */
        public void setTopLeft(Point topLeft) { this.topLeft = topLeft; }

        /**
         * @return the centre of the Circle.
         */
        public Point getCentre() { return this.centre; }

        /**
         * @return the diameter of the Circle.
         */
        public double getDiameter() {
                return this.diameter;
        }

        /**
         * Sets the diameter of the Circle.
         * @param diameter
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
         * Sets the color of the Circle.
         * @param color
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
         * Check if the Eraser is overlapping the Circle.
         * If it is, then the Eraser will erase the Circle.
         * @param eraser the Eraser instance which is currently erasing drawings
         * @return True if the Eraser should erase this Circle, False otherwise
         */
        @Override
        public boolean overlaps(Eraser eraser) {
                double leftX = eraser.getCentre().x-(eraser.getDimension()/2.0);
                double rightX = eraser.getCentre().x+(eraser.getDimension()/2.0);
                double topY = eraser.getCentre().y-(eraser.getDimension()/2.0);
                double bottomY = eraser.getCentre().y+(eraser.getDimension()/2.0);
                double Px, Py;
                if (Math.abs(this.centre.x - leftX) < Math.abs(this.centre.x - rightX)){
                        Px = leftX;
                }
                else{
                        Px = rightX;
                }

                if (Math.abs(this.centre.y - topY) < Math.abs(this.centre.y - bottomY)){
                        Py = topY;
                }
                else{
                        Py = bottomY;
                }
                double distance = Math.sqrt(Math.pow(this.centre.x - Px, 2) + Math.pow(this.centre.y - Py, 2));
                return distance <= (this.diameter/2.0);
        }

        /**
         * Displays the Circle with user-created color and size.
         * @param g2d GraphicsContext
         */
        @Override
        public void display(GraphicsContext g2d) {
                g2d.setFill(this.color);
                g2d.fillOval(this.topLeft.x, this.topLeft.y,
                        this.diameter, this.diameter);
        }
}
