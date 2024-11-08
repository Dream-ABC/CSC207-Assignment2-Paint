package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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

    private final BorderPane root;
    private final VBox topPanel;
    private final HBox bottomPanel;
    private final GridPane grid;

    public View(PaintModel model, Stage stage) throws FileNotFoundException {
        this.paintModel = model;
        this.stage = stage;

        this.paintPanel = new PaintPanel(this.paintModel);
        this.toolbarPanel = new ToolbarPanel();
        this.statusbarPanel = new StatusbarPanel(this.paintPanel);
        this.zoomPanel = new ZoomPanel();
        this.shapeChooserPanel = new ShapeChooserPanel(this.paintModel);
        this.layerChooserPanel = new LayerChooserPanel(this);
        this.layerChooserController = new LayerChooserController(this.layerChooserPanel, this.paintModel);

        this.canvas = new ResizableCanvas(700, 400, this.paintPanel);

        String iconImageFile = "src/main/java/ca/utoronto/utm/assignment2/Assets/PaintAppIcon.png";

        topPanel = new VBox();
        topPanel.getChildren().addAll(createMenuBar(), this.toolbarPanel);

        Region spacer = new Region();
        spacer.setStyle("-fx-background-color: #f8f1f0");

        bottomPanel = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Let the spacer expand
        bottomPanel.getChildren().addAll(this.statusbarPanel, spacer, this.zoomPanel);

        root = new BorderPane();

        root.setTop(topPanel);
        root.setBottom(bottomPanel);
        this.colorPickerPopup = new ColorPickerPopup(this.paintPanel, this);
        root.setLeft(this.shapeChooserPanel);
        ScrollPane layerPane = new ScrollPane(this.layerChooserPanel);
        root.setRight(layerPane);

        grid = new GridPane();
        //grid.setAlignment(Pos.CENTER);
        grid.getChildren().add(this.canvas);
        root.setCenter(grid);

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
        centerCanvas();
        this.paintModel.setView(this);
        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {centerCanvas();});
        stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {centerCanvas();});

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

    public void centerCanvas() {
        double availableWidth = stage.getWidth() - shapeChooserPanel.getWidth() - layerChooserPanel.getWidth();
        double availableHeight = stage.getHeight() - topPanel.getHeight() - bottomPanel.getHeight();

        double paddingTop = (availableHeight - canvas.getHeight()) / 2;
        double paddingLeft = (availableWidth - canvas.getWidth()) / 2;

        paddingTop = Math.max(paddingTop, 0)/2;
        paddingLeft = Math.max(paddingLeft, 0)/2;

        // Apply padding to center the canvas within the grid
        grid.setPadding(new Insets(paddingTop, 0, 0, paddingLeft));
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

        menuBar.getMenus().add(menu);

        // Another menu for View

        menu = new Menu("View");

        menuItem = new MenuItem("Colors");
        menuItem.setOnAction(this); // Show the color popup
        menu.getItems().add(menuItem);
        menuBar.setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 14px;");

        menuItem = new MenuItem("Line Thickness");
        this.lineThicknessSlider = new LineThicknessSlider(this.paintPanel);
        menuItem.setOnAction(event -> this.lineThicknessSlider.show()); // Show the slider popup
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
        } else if (command.equals("Undo")){
            this.paintModel.undo();
        } else if (command.equals("Redo")){
            this.paintModel.redo();
        }

        this.paintModel.notifyChange();
    }
}