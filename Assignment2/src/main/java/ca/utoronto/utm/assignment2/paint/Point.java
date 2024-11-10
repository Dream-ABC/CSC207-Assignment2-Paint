package ca.utoronto.utm.assignment2.paint;

/**
 * A class to represent a point.
 */
public class Point {
        double x, y;

        /**
         * Constructs a Point (x, y).
         * @param x x-coordinate of Point
         * @param y y-coordinate of Point
         */
        public Point(double x, double y){
                this.x=x; this.y=y;
        }

        /**
         * Shifts the Point by the specified horizontal and vertical offsets.
         *
         * @param x the horizontal offset
         * @param y the vertical offset
         */
        public void shift(double x, double y){
                this.x+=x;
                this.y+=y;
        }

        /**
         * Creates a copy of the Point instance.
         *
         * @return a copy of the Point instance
         */
        public Point copy(){
                return new Point(x, y);
        }
}
