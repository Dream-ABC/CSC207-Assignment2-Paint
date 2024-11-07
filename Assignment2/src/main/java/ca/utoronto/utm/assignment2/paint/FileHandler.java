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

    public void openImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        fileChooser.setTitle("Open .png File");
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
                writer.close();

                System.out.println("Paint saved successfully: " + filePath.getAbsolutePath());

            } catch (Exception e) {
                System.out.println("Failed to save paint: " + e.getMessage());
            }
        }
    }

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
                        this.panel.getModel().openPaint(command);
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
