package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape{
        private Point topLeft;
        private Point centre;
        private double diameter;
        private Color color;
        private double opaqueness;

        /*
        Constructor with no parameter. This constructor
        creates a default green circle with a radius of 0.
         */
        public Circle() {
                this.diameter = 0;
                this.color = Color.GREEN;
                this.opaqueness = 1.0;
        }

        /*
        Constructor that created a circle with user defined
        centre and radius.
         */
        public Circle(Point centre, double diameter){
                this.centre = centre;
                this.topLeft = centre;
                this.diameter = diameter;
                this.color = Color.BLACK;
                this.opaqueness = 1.0;
        }

        public void setCentre(Point centre) {
                this.centre = centre;
        }

        public void setTopLeft(Point topLeft) { this.topLeft = topLeft; }

        public Point getCentre() { return this.centre; }

        public double getDiameter() {
                return this.diameter;
        }

        public void setDiameter(double diameter) {
                this.diameter = diameter;
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

        @Override
        public void display(GraphicsContext g2d) {
                g2d.setGlobalAlpha(this.opaqueness);
                g2d.setFill(this.color);
                g2d.fillOval(this.topLeft.x, this.topLeft.y,
                        this.diameter, this.diameter);
        }
}
