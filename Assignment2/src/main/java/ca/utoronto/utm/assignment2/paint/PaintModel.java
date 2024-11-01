package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
        private ArrayList<Shape> allShapes = new ArrayList<>();
        private String mode = "";

        public void notifyUpdate() {
                this.setChanged();
                this.notifyObservers();
        }

        public void addShape(Shape shape) {
                this.allShapes.add(shape);
                this.setChanged();
                this.notifyObservers();
        }

        public void setMode(String mode) {
                this.mode = mode;
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Shape> getAllShapes(){
                return allShapes;
        }
        public String getMode(){return mode;}
}
