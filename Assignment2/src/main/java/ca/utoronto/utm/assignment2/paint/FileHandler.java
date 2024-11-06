package ca.utoronto.utm.assignment2.paint;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private PaintPanel panel;

    public FileHandler(PaintPanel panel) {
        this.panel = panel;
    }

    public void saveImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        fileChooser.setTitle("Save panel Image");
        File filePath = fileChooser.showSaveDialog(null);

        int width = (int) panel.getModel().getSelectedLayer().getWidth();
        int height = (int) panel.getModel().getSelectedLayer().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        panel.snapshot(new SnapshotParameters(), writableImage);

        BufferedImage bufferedImage = new BufferedImage((int) panel.getWidth(), (int) panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        PixelReader pixelReader = writableImage.getPixelReader();

        for (int y = 0; y < (int) panel.getHeight(); y++) {
            for (int x = 0; x < (int) panel.getWidth(); x++) {
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

    public void openImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                System.out.println("Image loaded successfully: " + file.getAbsolutePath());
                this.panel.getModel().openImage(image);

            } catch (Exception e) {
                System.out.println("Failed to load image: " + e.getMessage());
            }
        }
    }

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

                String allCommands = this.panel.getModel().savePaint();
                writer.write(allCommands);

                System.out.println("Paint saved successfully: " + filePath.getAbsolutePath());

            } catch (Exception e) {
                System.out.println("Failed to save paint: " + e.getMessage());
            }
        }
    }

    public void openCommands() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Custom Format Files (*.paint)", "*.paint");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open .paint File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                ArrayList<Command> commands = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    Command command = PatternParser.parseLine(line, this.panel);
                    if (command != null) {
                        commands.add(command);
                    }

                    System.out.println("Paint loaded successfully: " + file.getAbsolutePath());
                    this.panel.getModel().openPaint(commands);
                }
            } catch (Exception e) {
                System.out.println("Failed to load paint: " + e.getMessage());
            }
        }
    }
}
