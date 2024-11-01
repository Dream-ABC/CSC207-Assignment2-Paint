package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private PaintPanel paintPanel;
        private ShapeChooserPanel shapeChooserPanel;
        private LayerChooserPanel layerChooserPanel;
        private LayerChooserController layerChooserController;

        public View(PaintModel model, Stage stage) throws FileNotFoundException {
                this.paintModel = model;

                this.paintPanel = new PaintPanel(this.paintModel);
                this.shapeChooserPanel = new ShapeChooserPanel(this.paintModel);
                this.layerChooserPanel = new LayerChooserPanel(this);
                this.layerChooserController = new LayerChooserController(this.layerChooserPanel, this.paintModel);

                BorderPane root = new BorderPane();
                root.setTop(createMenuBar());
                root.setCenter(this.paintPanel);
                root.setLeft(this.shapeChooserPanel);
                ScrollPane layerPane = new ScrollPane(this.layerChooserPanel);
                root.setRight(layerPane);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Paint");
                stage.show();
                root.requestFocus();
        }

        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        public void setLayer(String layerName){
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

                return menuBar;
        }


        @Override
        public void handle(ActionEvent event) {
                System.out.println(((MenuItem) event.getSource()).getText());
                String command = ((MenuItem) event.getSource()).getText();
                System.out.println(command);
                if (command.equals("Exit")) {
                        Platform.exit();
                }
        }

}
