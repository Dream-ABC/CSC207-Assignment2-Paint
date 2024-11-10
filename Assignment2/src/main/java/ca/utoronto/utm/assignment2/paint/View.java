package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class View implements EventHandler<ActionEvent> {

    private final Stage stage;
    private final PaintModel paintModel;
    private final PaintPanel paintPanel;
    private final ToolbarPanel toolbarPanel;
    private final StatusbarPanel statusbarPanel;
    private final ZoomPanel zoomPanel;
    private final ShapeChooserPanel shapeChooserPanel;
    private final ColorPickerPopup colorPickerPopup;
    private final LayerChooserPanel layerChooserPanel;
    private final LayerChooserController layerChooserController;
    private final ResizableCanvas canvas;
    private LineThicknessSlider lineThicknessSlider;
    private FileHandlePopup fileHandlePopup;

    private BorderPane root;
    private VBox topPanel;
    private HBox bottomPanel;
    private ScrollPane canvasHolder;

    public View(PaintModel model, Stage stage) throws FileNotFoundException {
        this.paintModel = model;
        this.stage = stage;

        this.paintPanel = new PaintPanel(paintModel);
        this.canvas = new ResizableCanvas(700, 400, paintModel, paintPanel);

        this.toolbarPanel = new ToolbarPanel(this.paintModel);

        this.statusbarPanel = new StatusbarPanel();
        paintModel.addObserver(statusbarPanel);

        this.zoomPanel = new ZoomPanel(paintModel, canvas);
        paintModel.addObserver(zoomPanel);

        this.shapeChooserPanel = new ShapeChooserPanel(paintModel);
        this.layerChooserPanel = new LayerChooserPanel(this);
        this.layerChooserController = new LayerChooserController(layerChooserPanel, paintModel);
        this.fileHandlePopup = new FileHandlePopup(this.paintPanel);

        String iconImageFile = "src/main/java/ca/utoronto/utm/assignment2/Assets/PaintAppIcon.png";

        topPanel = new VBox();
        topPanel.getChildren().addAll(createMenuBar(), this.toolbarPanel);

        Region spacer = new Region();
        spacer.setStyle("-fx-background: #f8f1f0");

        bottomPanel = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        bottomPanel.getChildren().addAll(this.statusbarPanel, spacer, this.zoomPanel);

        canvasHolder = new ScrollPane(this.canvas);
        canvasHolder.setStyle("-fx-background: #f8f1f0; -fx-border-color: #f8f1f0;");
        canvasHolder.setOnMouseMoved(e -> {
            double scale = paintModel.getZoomFactor() / 100.0;

            double scrollX = canvasHolder.getHvalue() * (canvasHolder.getContent().getBoundsInLocal().getWidth() - canvasHolder.getViewportBounds().getWidth());
            double scrollY = canvasHolder.getVvalue() * (canvasHolder.getContent().getBoundsInLocal().getHeight() - canvasHolder.getViewportBounds().getHeight());

            double canvasX = canvas.getBoundsInParent().getMinX() - scrollX;
            double canvasY = canvas.getBoundsInParent().getMinY() - scrollY;

            double adjustedX = (e.getX() - canvasX - 6) / scale;
            double adjustedY = (e.getY() - canvasY - 6) / scale;

            model.setMousePosition(adjustedX, adjustedY);
            model.setCanvasPosition(canvasX, canvasY);
            model.setCanvasSize(paintPanel.getWidth(), paintPanel.getHeight());
        });

        root = new BorderPane();
        root.setStyle("-fx-background: #f8f1f0");

        root.setCenter(canvasHolder);
        root.setLeft(this.shapeChooserPanel);
        root.setTop(topPanel);
        root.setBottom(bottomPanel);
        this.colorPickerPopup = new ColorPickerPopup(this.paintPanel, this);

        Scene scene = new Scene(root);

        FileInputStream inputIcon = new FileInputStream(iconImageFile);
        Image iconImage = new Image(inputIcon);

        stage.getIcons().add(iconImage);
        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();
        stage.setWidth(1000);
        stage.setHeight(700);
        canvas.setUpPositions();

        this.paintModel.setView(this);

        root.requestFocus();
    }

    public PaintModel getPaintModel() {
        return this.paintModel;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setLayer(String layerName) {
        this.layerChooserController.selectLayer(layerName);
    }

    private MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();
        Menu menu;
        MenuItem menuItem;

        // A menu for File

        menu = new Menu("File");

        menuItem = new MenuItem("New");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Open");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Save");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menu.getItems().add(new SeparatorMenuItem());

        menuItem = new MenuItem("Exit");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);

        // Another menu for Edit

        menu = new Menu("Edit");

        menuItem = new MenuItem("Cut");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Copy");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Paste");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menu.getItems().add(new SeparatorMenuItem());
        menuItem = new MenuItem("Undo");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Redo");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Delete");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);

        // Another menu for View

        menu = new Menu("View");

        menuItem = new MenuItem("Colors");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuBar.setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 14px;");

        menuItem = new MenuItem("Line Thickness");
        this.lineThicknessSlider = new LineThicknessSlider(this.paintPanel);
        menuItem.setOnAction(event -> this.lineThicknessSlider.show());
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);

        return menuBar;
    }

    @Override
    public void handle(ActionEvent event) {
        String command = ((MenuItem) event.getSource()).getText();
        if (command.equals("Exit")) {
            Platform.exit();
        } else if (command.equals("Colors")) {
            this.colorPickerPopup.display();
        } else if (command.equals("Undo")) {
            this.paintModel.undo();
        } else if (command.equals("Redo")) {
            this.paintModel.redo();
        } else if (command.equals("New")) {
            this.fileHandlePopup.newFile();
        } else if (command.equals("Open")) {
            this.fileHandlePopup.openFile();
        } else if (command.equals("Save")) {
            this.fileHandlePopup.saveFile();
        } else if (command.equals("Copy")){
            this.paintModel.copy();
        } else if (command.equals("Paste")){
            this.paintModel.paste();
        } else if (command.equals("Cut")) {
            this.paintModel.cut();
        } else if (command.equals("Delete")) {
            this.paintModel.delete();
        }

        this.paintModel.notifyChange();
    }
}