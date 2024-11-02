package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;

import java.util.*;

public class ResizingButton extends Button implements Observer {
    public ResizingButton() {
        super("");
        setPrefSize(10,10);
        setMinSize(10,10);
        setMaxSize(10,10);
    }

    public void update(Observable o, Object arg) {

    }
}
