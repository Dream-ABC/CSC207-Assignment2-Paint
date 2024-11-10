package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import java.util.Observable;
import java.util.Observer;

public class MenuPanel extends MenuBar implements Observer {
    private MenuController menuController;

    public MenuPanel(PaintModel model) {
        this.menuController = new MenuController(model);

        setStyle("-fx-background-color: #f8f1f0; -fx-font-size: 14px;");

        Menu fileMenu = new Menu("File");

        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(menuController);

        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(menuController);

        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(menuController);

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(menuController);

        fileMenu.getItems().addAll(newFile, openFile, saveFile, new SeparatorMenuItem(), exit);
        getMenus().add(fileMenu);

        Menu editMenu = new Menu("Edit");

        MenuItem cutEdit = new MenuItem("Cut");
        cutEdit.setOnAction(menuController);

        MenuItem copyEdit = new MenuItem("Copy");
        copyEdit.setOnAction(menuController);

        MenuItem pasteEdit = new MenuItem("Paste");
        pasteEdit.setOnAction(menuController);

        MenuItem undoEdit = new MenuItem("Undo");
        undoEdit.setOnAction(menuController);

        MenuItem redoEdit = new MenuItem("Redo");
        redoEdit.setOnAction(menuController);

        editMenu.getItems().addAll(cutEdit, copyEdit, pasteEdit, new SeparatorMenuItem(), undoEdit, redoEdit);
        getMenus().add(editMenu);

        Menu viewMenu = new Menu("View");
        getMenus().add(viewMenu);
    }

    @Override
    public void update(Observable o, Object arg) {
        PaintModel model = (PaintModel) o;
    }

}
