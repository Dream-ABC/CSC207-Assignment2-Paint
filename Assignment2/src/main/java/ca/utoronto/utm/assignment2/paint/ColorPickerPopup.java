package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.util.Locale;

public class ColorPickerPopup extends Popup {

    private PaintPanel paintPanel;
    private View view;
    private ColorPicker colorPicker;

    public ColorPickerPopup(PaintPanel panel, View view) {
        Locale.setDefault(Locale.ENGLISH);

        this.paintPanel = panel;
        this.view = view;
        this.paintPanel.setColor(Color.BLACK);

        // Set default color
        this.colorPicker = new ColorPicker();
        this.colorPicker.setValue(this.paintPanel.getColor());

        // Add listener
        this.colorPicker.setOnAction(event -> {
            Color selectedColor = this.colorPicker.getValue();
            this.paintPanel.setColor(selectedColor);
            this.hide();  // hide popup after selecting the color
        });
    }

    public void display() {
        // Prevent unexpected actions on canvas
        this.paintPanel.setDisable(true);

        // Add the color picker to the popup
        this.getContent().clear();
        this.getContent().add(this.colorPicker);

        // when the popup event is ended (popup closed), enable actions on canvas again
        this.setOnHidden(event -> {
            this.paintPanel.setDisable(false);
            this.view.getStage().requestFocus();
        });
        this.show(this.paintPanel.getScene().getWindow());
    }
}