package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;
import java.util.Optional;

/**
 * The FileHandlePopup class provides user interface dialogs for handling file operations
 * such as opening and saving files, and creating new layers or files in the paint application.
 */
public class FileHandlePopup {

    private PaintPanel panel;
    private PaintModel model;
    private FileHandler handler;

    /**
     * Constructs a FileHandlePopup instance associated with a specified PaintPanel object.
     *
     * @param paintPanel The PaintPanel object used to initialize the FileHandlePopup, from which
     *                   the model is obtained and used to interact with the file handler.
     */
    public FileHandlePopup(PaintPanel paintPanel) {
        this.panel = paintPanel;
        this.model = paintPanel.getModel();
        this.handler = new FileHandler(this.panel);
    }

    /**
     * Opens a dialog for the user to choose a file type they would like to open.
     *
     * This method displays a dialog with options to open either a .png file or a .paint file:
     * 1. If the user selects a .png file, the image is loaded using the handler's openImage method.
     * 2. If the user selects a .paint file, a new file is created after confirming, and the
     * existing .paint file is loaded using the handler's openPaint method.
     *
     * The dialog also handles user actions for closing the dialog without selection.
     */
    public void openFile() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choosing file type");
        dialog.setOnCloseRequest(event -> dialog.close());

        VBox dialogContent = new VBox();
        dialogContent.setSpacing(10);
        dialogContent.getChildren().add(new Text("Please choose a type of file that you want to open."));

        dialog.getDialogPane().setContent(dialogContent);

        // set buttons
        ButtonType buttonPNG = new ButtonType(".png");
        ButtonType buttonPAINT = new ButtonType(".paint");
        dialog.getDialogPane().getButtonTypes().addAll(buttonPNG, buttonPAINT);

        // set close request
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> dialog.hide());

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonPNG) {
                this.handler.openImage();
            } else if (result.get() == buttonPAINT) {
                if (this.newFile()) {
                    this.handler.openPaint();
                }
            }
        }
    }

    /**
     * Opens a dialog allowing the user to choose a file type to save.
     *
     * The method presents a dialog with options to save the file as a .png or .paint file.
     * Depending on the user's choice, it invokes either the saveImage or savePaint method
     * from the handler to perform the appropriate save operation.
     *
     * Dialog management includes setting the title, content, and buttons, as well as
     * handling the close request to properly hide the dialog.
     */
    public void saveFile() {
        if (this.panel.getCurrentShape() != null) {
            // prevent unfinished polyline
            this.model.addShapeFinal(this.panel.getCurrentShape());
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choosing file type");

        VBox dialogContent = new VBox();
        dialogContent.setSpacing(10);
        dialogContent.getChildren().add(new Text("Please choose a type of file that you want to save."));

        dialog.getDialogPane().setContent(dialogContent);

        // set buttons
        ButtonType buttonPNG = new ButtonType(".png");
        ButtonType buttonPAINT = new ButtonType(".paint");
        dialog.getDialogPane().getButtonTypes().addAll(buttonPNG, buttonPAINT);

        // set close request
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> dialog.hide());

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonPNG) {
                this.handler.saveImage();

            } else if (result.get() == buttonPAINT) {
                this.handler.savePaint();
            }
        }
    }

    /**
     * Displays a confirmation dialog to the user about creating a new file.
     * If the user confirms, the current progress will be lost,
     * and a new file will be initialized within the model.
     *
     * @return true if the user confirms the action and the new file is created; false otherwise.
     */
    public boolean newFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New File");
        alert.setHeaderText("Your current progress will be lost!");
        alert.setContentText("Do you want to proceed?");

        // set buttons
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        // set close request
        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> alert.hide());

        // Waits for user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            this.panel.resetCurrentShape();
            this.model.resetAll();
            return true;
        }
        return false;
    }
}
