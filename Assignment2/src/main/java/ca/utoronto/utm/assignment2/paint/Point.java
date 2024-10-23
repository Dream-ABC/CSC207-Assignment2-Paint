package ca.utoronto.utm.assignment2.paint;


import javafx.scene.paint.Color;

public class Point implements Shape {
        double x, y; // Available to our package
        Point(double x, double y){
                this.x=x; this.y=y;
        }

        @Override
        public Color getColour() {
                return null;
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
