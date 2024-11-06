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
         * Constructs a default black circle with a diameter of 0.
         */
        public Circle() {
                this.diameter = 0;
                this.color = Color.BLACK;
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
        public void setTopLeft(Point topLeft) { this.topLeft = topLeft; }

        /**
         * @return the centre of the Circle.
         */
        public Point getCentre() { return this.centre; }

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
         * @param eraser the Eraser instance which is currently erasing drawings
         * @return True if the Eraser should erase this Circle, False otherwise
         */
        @Override
        public boolean overlaps(Tool eraser) {
                double leftX = eraser.getCentre().x-(eraser.getDimensionX()/2.0);
                double rightX = eraser.getCentre().x+(eraser.getDimensionX()/2.0);
                double topY = eraser.getCentre().y-(eraser.getDimensionY()/2.0);
                double bottomY = eraser.getCentre().y+(eraser.getDimensionY()/2.0);
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
