package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseButton;

/**
 * The TextStrategy class is responsible for handling text input as shapes
 * within the current layer. It implements the ShapeStrategy interface
 * to provide specific behaviours for mouse events related to text shapes.
 */
public class TextStrategy implements ShapeStrategy {

    private PaintPanel panel;
    private TextEditorDialog textEditorDialog;
    private Text text;

    /**
     * Constructs a new TextStrategy object.
     *
     * @param p The PaintPanel object associated with this strategy
     */
    public TextStrategy(PaintPanel p) {
        this.panel = p;
        // create new shape
        this.text = (Text) this.panel.getShapeFactory().getShape("Text", "");
        // set text editor
        this.textEditorDialog = new TextEditorDialog(this.panel, this.text);
    }

    @Override
    public void mousePressed(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDragged(javafx.scene.input.MouseEvent mouseEvent) {
    }

    /**
     * Handles the mouse released event. This method is triggered when the user releases
     * the mouse button. Specifically, it processes the event only if the primary mouse button
     * is released. It initializes the position of a text shape, adds it to the canvas, listens
     * for user input through a text editor dialog, and based on the input, either adds or
     * removes the text shape from the canvas.
     *
     * @param mouseEvent the MouseEvent object containing details about the mouse event
     */
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

            // otherwise, add final shape and command
            if (!this.text.getText().isEmpty()) {
                this.panel.getModel().addShapeFinal(this.text);
            }
        }
    }
}
