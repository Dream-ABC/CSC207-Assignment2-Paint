package ca.utoronto.utm.assignment2.paint;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Stack;

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
            System.out.println("Image saved as: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save image.");
        }
    }

    public void loadImage(PaintLayer layer, File file) {
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                if (image.isError()) {
                    System.out.println("Failed to load image, please check the file path: " + file.getAbsolutePath());
                    return;
                }

                layer.setBackground(image);
                System.out.println("Image loaded successfully: " + file.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Failed to load image: " + e.getMessage());
            }
        }
    }

    public void saveCommands() throws IOException {

        CommandHistory commandHistory = this.panel.getModel().getHistory();
        StringBuilder allCommands = new StringBuilder();
        for (Command command : commandHistory.getUndoStack()) {
            allCommands.append(command.toString());
            allCommands.append("\n");
        }

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Custom Format Files (*.paint)", "*.paint");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Save panel Image");
        File filePath = fileChooser.showSaveDialog(null);

        File savedFile = new File(filePath.getPath());

        BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile));

        writer.write("WIDTH#" + this.panel.getModel().getSelectedLayer().getWidth() + "\n");
        writer.write("HEIGHT#" + this.panel.getModel().getSelectedLayer().getHeight() + "\n");
        writer.write(allCommands.toString());
    }

    public void loadCommands(PaintLayer layer, File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
               Command command = PatternParser.parseLine(line, this.panel);
               this.panel.getModel().getHistory().execute(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
