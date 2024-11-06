package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

public class FileHandlePopup {

    private PaintPanel panel;
    private PaintModel model;
    private FileHandler handler;

    public FileHandlePopup(PaintPanel paintPanel) {
        this.panel = paintPanel;
        this.model = paintPanel.getModel();
        this.handler = new FileHandler(this.panel);
    }

    public void openFile() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choosing file type");

        VBox dialogContent = new VBox();
        dialogContent.setSpacing(10);
        dialogContent.getChildren().add(new Text("Please choose a type of file that you want to open."));

        dialog.getDialogPane().setContent(dialogContent);

        ButtonType buttonPNG = new ButtonType(".png");
        ButtonType buttonPAINT = new ButtonType(".paint");
        dialog.getDialogPane().getButtonTypes().addAll(buttonPNG, buttonPAINT);

        // 显示对话框，并等待用户选择
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonPNG) {
                this.handler.openImage();

            } else if (result.get() == buttonPAINT) {
                if (this.newFile()) {
                    this.handler.openCommands();
                }
            }
        }
    }

    public void saveFile() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choosing file type");

        VBox dialogContent = new VBox();
        dialogContent.setSpacing(10);
        dialogContent.getChildren().add(new Text("Please choose a type of file that you want to save."));

        dialog.getDialogPane().setContent(dialogContent);

        ButtonType buttonPNG = new ButtonType(".png");
        ButtonType buttonPAINT = new ButtonType(".paint");
        dialog.getDialogPane().getButtonTypes().addAll(buttonPNG, buttonPAINT);

        // 显示对话框，并等待用户选择
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonPNG) {
                this.handler.saveImage();

            } else if (result.get() == buttonPAINT) {
                this.handler.savePaint();
            }
        }
    }

    public boolean newFile() {
        this.panel.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New File");
        alert.setHeaderText("Your current progress will be lost!");
        alert.setContentText("Do you want to proceed?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            this.model.newFile();
            this.panel.setDisable(false);
            return true;
        }
        this.panel.setDisable(false);
        return false;
    }
}
