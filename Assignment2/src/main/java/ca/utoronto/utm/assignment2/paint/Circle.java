package ca.utoronto.utm.assignment2.paint;


public class Circle {
        private Point centre;
        private double radius;

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

}
