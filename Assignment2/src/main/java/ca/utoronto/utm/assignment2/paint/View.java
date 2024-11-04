package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private PaintPanel paintPanel;
        private ShapeChooserPanel shapeChooserPanel;
        private ResizableCanvas canvas;

        public View(PaintModel model, Stage stage) throws FileNotFoundException {
            this.paintModel = model;
            this.paintPanel = new PaintPanel(this.paintModel);
            canvas = new ResizableCanvas(400, 300, paintPanel);
            this.shapeChooserPanel = new ShapeChooserPanel(this);

//            Pane root = new Pane();
//            MenuBar menuBar = createMenuBar();
//            this.shapeChooserPanel.setLayoutX(0);
//            this.shapeChooserPanel.setLayoutY(menuBar.getHeight());
//            this.paintPanel.setLayoutX(menuBar.getHeight());
//            this.paintPanel.setLayoutY(shapeChooserPanel.getWidth());
//            root.getChildren().addAll(menuBar, shapeChooserPanel, paintPanel);

            BorderPane root = new BorderPane();
            root.setTop(createMenuBar());
            root.setLeft(this.shapeChooserPanel);
            root.setCenter(this.canvas);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Paint");
            this.paintPanel.update(this.paintModel, null);
            stage.show();

//            stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
//                paintPanel.setWidth(newWidth.doubleValue());
//                paintPanel.update(this.paintModel, null);
//            });
//            stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
//                paintPanel.setHeight(newHeight.doubleValue());
//                paintPanel.update(this.paintModel, null);
//            });
            root.requestFocus();
        }

        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        // ugly way to do this?
        public void setMode(String mode){
            this.paintPanel.setMode(mode);
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
