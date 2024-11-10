package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;


public class MenuController implements EventHandler<ActionEvent> {
    private PaintModel model;

    public MenuController(PaintModel model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
        String command = ((MenuItem) event.getSource()).getText();
        if (command.equals("Exit")) {
            Platform.exit();
        } else if (command.equals("Undo")){
            model.undo();
        } else if (command.equals("Redo")){
            model.redo();
        }
    }
}
