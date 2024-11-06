package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class View implements EventHandler<ActionEvent> {

    private Stage stage;
    private PaintModel paintModel;
    private PaintPanel paintPanel;
    private ToolbarPanel toolbarPanel;
    private StatusbarPanel statusbarPanel;
    private ZoomPanel zoomPanel;
    private ShapeChooserPanel shapeChooserPanel;
    private ColorPickerPopup colorPickerPopup;
    private LayerChooserPanel layerChooserPanel;
    private LayerChooserController layerChooserController;
    private ResizableCanvas canvas;

    private BorderPane root;
    private VBox topPanel;
    private HBox bottomPanel;
    private GridPane grid;

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

        this.canvas = new ResizableCanvas(1500, 700, this.paintPanel);

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
        //grid.setAlignment(Pos.CENTER);
        //grid.setPadding(new Insets(stage.getHeight()/2 - grid.getHeight()/2, 10, 0, 0));
        root.setCenter(grid);

        Scene scene = new Scene(root);

        FileInputStream inputIcon = new FileInputStream(iconImageFile);
        Image iconImage = new Image(inputIcon);

        stage.getIcons().add(iconImage);
        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();
        stage.setWidth(1800);
        stage.setHeight(1000);
        canvas.setUpPositions();
        centerCanvas();

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
//        System.out.println(shapeChooserPanel.getWidth());
//        System.out.println(layerChooserPanel.getWidth());
//        System.out.println(stage.getHeight());
//        System.out.println(topPanel.getHeight());
//        System.out.println(bottomPanel.getHeight());


        // Calculate the available width and height
        double availableWidth = stage.getWidth() - shapeChooserPanel.getWidth() - layerChooserPanel.getWidth();
        double availableHeight = stage.getHeight() - topPanel.getHeight() - bottomPanel.getHeight();

        // Calculate the padding needed to center the canvas within the available area
        double paddingTop = (availableHeight - canvas.getHeight()) / 2;
        double paddingLeft = (availableWidth - canvas.getWidth()) / 2;

        // Set the padding only if the calculated values are valid and non-negative
        paddingTop = Math.max(paddingTop, 0);
        paddingLeft = Math.max(paddingLeft, 0);

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

        // Another menu for Options

        menu = new Menu("View");


        menuItem = new MenuItem("Colors");
        menuItem.setOnAction(this); // Show the color popup
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);
        menuBar.setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 14px;");

        return menuBar;
    }

    @Override
    public void handle(ActionEvent event) {
//        System.out.println(((MenuItem) event.getSource()).getText());
        String command = ((MenuItem) event.getSource()).getText();
//        System.out.println(command);
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