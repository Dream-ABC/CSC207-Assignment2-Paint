package ca.utoronto.utm.assignment2.paint;


import javafx.scene.paint.Color;

public class Circle implements Shape{
        private Point centre;
        private double radius;
        private Color color;

        /*
        Constructor with no parameter. This constructor
        creates a default green circle with a radius of 0.
         */
        public Circle() {
                this.radius = 0;
                this.color = Color.GREEN;
        }

        /*
        Constructor that created a circle with user defined
        centre and radius.
         */
        public Circle(Point centre, double radius){
                this.centre = centre;
                this.radius = radius;
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
        public int getThickness() {
                return -1;
        }

        @Override
        public String getShape() {
                return "Circle";
        }
}
