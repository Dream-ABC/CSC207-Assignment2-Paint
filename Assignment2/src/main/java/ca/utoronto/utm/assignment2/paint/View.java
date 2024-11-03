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
import javafx.stage.Popup;
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
    private OpaquenessSlider opaquenessSlider;

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

        String iconImageFile = "src/main/java/ca/utoronto/utm/assignment2/Assets/PaintAppIcon.png";

        VBox topPanel = new VBox();
        topPanel.getChildren().addAll(createMenuBar(), this.toolbarPanel);

        Region spacer = new Region();
        spacer.setStyle("-fx-background-color: #f8f1f0");

        HBox bottomPanel = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Let the spacer expand
        bottomPanel.getChildren().addAll(this.statusbarPanel, spacer, this.zoomPanel);

        BorderPane root = new BorderPane();
        root.setTop(topPanel);
        root.setBottom(bottomPanel);
        this.colorPickerPopup = new ColorPickerPopup(this.paintPanel, this);
        root.setTop(createMenuBar());
        root.setCenter(this.paintPanel);
        root.setLeft(this.shapeChooserPanel);
        ScrollPane layerPane = new ScrollPane(this.layerChooserPanel);
        root.setRight(layerPane);
        Scene scene = new Scene(root);

        FileInputStream inputIcon = new FileInputStream(iconImageFile);
        Image iconImage = new Image(inputIcon);

        stage.getIcons().add(iconImage);
        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();
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

        menuBar.getMenus().add(menu);

        // Another menu for Options

        menu = new Menu("View");

        menuItem = new MenuItem("Opaqueness");
        this.opaquenessSlider = new OpaquenessSlider(this.paintPanel);
        menuItem.setOnAction(event -> this.opaquenessSlider.show()); // Show the slider popup
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Colors");
        menuItem.setOnAction(this); // Show the color popup
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);
        menuBar.setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 14px;");

        return menuBar;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println(((MenuItem) event.getSource()).getText());
        String command = ((MenuItem) event.getSource()).getText();
        System.out.println(command);
        if (command.equals("Exit")) {
            Platform.exit();
        } else if (command.equals("Colors")) {
            this.colorPickerPopup.display();
        }
    }
}