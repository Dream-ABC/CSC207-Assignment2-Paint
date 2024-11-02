package ca.utoronto.utm.assignment2.paint;

import javafx.scene.layout.BorderPane;

public class ResizeableCanvas extends BorderPane {
    private PaintPanel panel;

    public ResizeableCanvas(PaintPanel panel) {
        setPrefSize(panel.getWidth()-10, panel.getHeight()+10);
        setCenter(panel);
    }


}
