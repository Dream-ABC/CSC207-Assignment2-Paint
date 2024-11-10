package ca.utoronto.utm.assignment2.paint;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * The FileHandler class is responsible for handling file operations such as saving and opening images
 * and custom paint commands in a graphical application.
 * It interacts with a PaintPanel object to perform these operations.
 */
public class FileHandler {

    private PaintPanel panel;

    /**
     * Constructs a FileHandler object with the specified PaintPanel.
     *
     * @param panel the PaintPanel to be associated with this FileHandler
     */
    public FileHandler(PaintPanel panel) {
        this.panel = panel;
    }

    /**
     * Saves the currently selected layer of the panel as a PNG file.
     * Opens a file chooser dialog to specify the save location and
     * converts the selected layer's content to a PNG image.
     * If the save operation is successful, a confirmation message is printed to the console.
     * If the save operation fails, an error message is printed to the console.
     */
    public void saveImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        fileChooser.setTitle("Save as .png File");
        File filePath = fileChooser.showSaveDialog(null);

        if (filePath != null) {
            int width = (int) panel.getModel().getSelectedLayer().getWidth();
            int height = (int) panel.getModel().getSelectedLayer().getHeight();

            WritableImage writableImage = new WritableImage(width, height);
            panel.snapshot(new SnapshotParameters(), writableImage);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            PixelReader pixelReader = writableImage.getPixelReader();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = pixelReader.getArgb(x, y);
                    bufferedImage.setRGB(x, y, argb);
                }
            }

            File savedFile = new File(filePath.getPath());
            try {
                ImageIO.write(bufferedImage, "png", savedFile);
                System.out.println("Image saved successfully: " + filePath.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Failed to save image: " + e.getMessage());
            }
        }
    }

    /**
     * Opens a dialog to select and load a PNG image.
     * <p>
     * This method uses a FileChooser to open a dialog that filters for PNG files.
     * Once an image file is selected, it attempts to load the image and sets it
     * as the background of the currently selected layer in the panel's model.
     * <p>
     * If the image is loaded successfully, a success message is printed to the console.
     * If there is a failure in loading the image, an error message is printed to the console.
     */
    public void openImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        fileChooser.setTitle("Open .png File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                System.out.println("Image loaded successfully: " + file.getAbsolutePath());
                this.panel.getModel().setBackground(image);

            } catch (Exception e) {
                System.out.println("Failed to load image: " + e.getMessage());
            }
        }
    }

    /**
     * Saves the currently selected layer of the PaintPanel as a custom .paint file.
     * <p>
     * This method opens a file chooser dialog to specify the save location and
     * configures it to save files with the .paint extension. It writes the
     * width and height information of the selected layer, followed by all the
     * drawing commands executed, into the file. If the save operation is
     * successful, a confirmation message is printed to the console. If it fails,
     * an error message is printed instead.
     */
    public void savePaint() {

        // add file chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Custom Format Files (*.paint)", "*.paint");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save as .paint File");
        File filePath = fileChooser.showSaveDialog(null);

        if (filePath != null) {
            try {
                File savedFile = new File(filePath.getPath());
                BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile));

                // write in all commands
                writer.write("WIDTH#" + this.panel.getModel().getSelectedLayer().getWidth() + "\n");
                writer.write("HEIGHT#" + this.panel.getModel().getSelectedLayer().getHeight() + "\n");

                String allCommands = this.panel.getModel().saveCommands();
                writer.write(allCommands);
                writer.close();

                System.out.println("Paint saved successfully: " + filePath.getAbsolutePath());

            } catch (Exception e) {
                System.out.println("Failed to save paint: " + e.getMessage());
            }
        }
    }

    /**
     * Opens a file chooser dialog to select and load a .paint file.
     * <p>
     * This method uses a FileChooser to open a dialog that filters for files with the .paint extension.
     * Once a file is selected, it reads each line of the file, parses it into commands using PatternParser,
     * and executes these commands to replicate the drawing state stored in the file.
     * <p>
     * If the file is loaded successfully, a success message with the file path is printed to the console.
     * If there is a failure in loading the file, an error message is printed to the console.
     */
    public void openPaint() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Custom Format Files (*.paint)", "*.paint");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open .paint File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                while (line != null) {
                    Command command = PatternParser.parseLine(line, this.panel);
                    if (command != null) {
                        this.panel.getModel().executeCommand(command);
                    }
                    line = reader.readLine();
                }
                reader.close();
                System.out.println("Paint loaded successfully: " + file.getAbsolutePath());

            } catch (Exception e) {
                System.out.println("Failed to load paint: " + e.getMessage());
            }
        }
    }
}
