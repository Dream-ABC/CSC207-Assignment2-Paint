package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.*;

public class ResizingButton extends Button implements Observer {
    public ResizingButton() {
        super("a");
//        setPrefSize(10,10);
//        setMinSize(10,10);
//        setMaxSize(10,10);
    }

    public void update(Observable o, Object arg) {

    }

    public void handle(ActionEvent event) {
        System.out.println("bananas");
    }
}
