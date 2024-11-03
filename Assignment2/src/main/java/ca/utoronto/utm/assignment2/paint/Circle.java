package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape{
        private Point centre;
        private double radius;
        private Color color;
        private double opaqueness;

        /*
        Constructor with no parameter. This constructor
        creates a default green circle with a radius of 0.
         */
        public Circle() {
                this.radius = 0;
                this.color = Color.BLACK;
                this.opaqueness = 1.0;
        }

        /*
        Constructor that created a circle with user defined
        centre and radius.
         */
        public Circle(Point centre, double radius){
                this.centre = centre;
                this.radius = radius;
                this.color = Color.BLACK;
                this.opaqueness = 1.0;
        }

        public Point getCentre() {
                return centre;
        }

        public void setCentre(Point centre) {
                this.centre = centre;
        }

        public double getRadius() {
                return radius;
        }

        public void setRadius(double radius) {
                this.radius = radius;
        }

        @Override
        public Color getColor() {
                return this.color;
        }

        @Override
        public void setColor(Color color) {
                this.color = color;
        }

        @Override
        public int getThickness() {
                return -1;
        }

        @Override
        public void setOpaqueness(int opaque) {
                this.opaqueness = opaque / 100.0;
        }

        @Override
        public double getOpaqueness() {
                return this.opaqueness;
        }

        @Override
        public String getShape() {
                return "Circle";
        }

        @Override
        public void display(GraphicsContext g2d) {
                g2d.setGlobalAlpha(this.opaqueness);
                g2d.setFill(this.color);
                g2d.fillOval(this.centre.x, this.centre.y,
                        this.radius, this.radius);
        }
}
