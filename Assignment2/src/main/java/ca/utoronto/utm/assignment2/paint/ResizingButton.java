package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;

public class ResizingButton extends Button {
    public ResizingButton() {
        super("");
        setPrefSize(10,10);
        setMinSize(10,10);
        setMaxSize(10,10);
    }
}
