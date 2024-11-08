package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseButton;

public class TextStrategy implements ShapeStrategy {

    private PaintPanel panel;
    private TextEditorDialog textEditorDialog;
    private Text text;

    public TextStrategy(PaintPanel p) {
        this.panel = p;
        // create new shape
        this.text = (Text) this.panel.getShapeFactory().getShape("Text");
        // set text editor
        this.textEditorDialog = new TextEditorDialog(this.panel, this.text);
    }

    @Override
    public void mousePressed(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDragged(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(javafx.scene.input.MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // initialize the position of text
            Point position = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.text.setTopLeft(position);

            // add text to shape so that it can be updated on canvas
            this.panel.getModel().addShape(text);

            // listen for user input
            this.textEditorDialog.display();  // can update panel on time

            // if there's no user input, remove the text shape
            this.panel.getModel().removeShape(this.text);

            // otherwise, add final shape
            if (!this.text.getText().isEmpty()) {
                this.panel.getModel().addShapeFinal(this.text);
            }
        }
    }

    @Override
    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }
}
