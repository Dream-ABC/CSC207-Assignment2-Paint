package ca.utoronto.utm.assignment2.paint;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
        private ArrayList<Point> points=new ArrayList<Point>();
        private ArrayList<Circle> circles=new ArrayList<Circle>();
        private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        private ArrayList<Squiggle> paths=new ArrayList<>();
        private ArrayList<Square> squares = new ArrayList<Square>();
        private ArrayList<Oval> ovals=new ArrayList<>();

        private ArrayList<Shape> allShapes = new ArrayList<>();

        public void addPoint(Point p){
                this.points.add(p);
                this.setChanged();
                this.notifyObservers();
        }
        public ArrayList<Point> getPoints(){
                return points;
        }

        public void addCircle(Circle c){
                this.circles.add(c);
                this.setChanged();
                this.notifyObservers();
        }
        public ArrayList<Circle> getCircles(){
                return circles;
        }

        public void addRectangle(Rectangle r){
                this.rectangles.add(r);
                this.setChanged();
                this.notifyObservers();
        }
        public ArrayList<Rectangle> getRectangles(){
                return rectangles;
        }

        public void addPath(Squiggle p){
                this.paths.add(p);
                this.setChanged();
                this.notifyObservers();
        }

        public void updatePath() {
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Squiggle> getPaths(){
                return paths;
        }

        public void addSquare(Square s){
                this.squares.add(s);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Square> getSquares(){
                return squares;
        }

        public void addOval(Oval o){
                this.ovals.add(o);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Oval> getOvals() { return ovals;}

        public void addShape(Shape shape) {
                this.allShapes.add(shape);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Shape> getAllShapes(){
                return allShapes;
        }
}
