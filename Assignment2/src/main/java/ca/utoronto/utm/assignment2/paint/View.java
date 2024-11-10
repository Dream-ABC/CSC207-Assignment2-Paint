package ca.utoronto.utm.assignment2.paint;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class View {

    private final Stage stage;
    private final PaintModel paintModel;
    private final PaintPanel paintPanel;
    private final MenuPanel menuPanel;
    private final ToolbarPanel toolbarPanel;
    private final StatusbarPanel statusbarPanel;
    private final ZoomPanel zoomPanel;
    private final ShapeChooserPanel shapeChooserPanel;
    private final LayerChooserPanel layerChooserPanel;
    private final LayerChooserController layerChooserController;
    private final ResizableCanvas canvas;
    private LineThicknessSlider lineThicknessSlider;

    private BorderPane root;
    private VBox topPanel;
    private HBox bottomPanel;
    private ScrollPane canvasHolder;

    public View(PaintModel model, Stage stage) throws FileNotFoundException {
        this.paintModel = model;
        this.stage = stage;

        this.paintPanel = new PaintPanel(paintModel);
        this.canvas = new ResizableCanvas(700, 400, paintModel, paintPanel);

        this.menuPanel = new MenuPanel(paintModel);
        paintModel.addObserver(menuPanel);

        this.toolbarPanel = new ToolbarPanel();
        paintModel.addObserver(toolbarPanel);

        this.statusbarPanel = new StatusbarPanel();
        paintModel.addObserver(statusbarPanel);

        this.zoomPanel = new ZoomPanel(paintModel, canvas);
        paintModel.addObserver(zoomPanel);

        this.shapeChooserPanel = new ShapeChooserPanel(paintModel);
        this.layerChooserPanel = new LayerChooserPanel(this);
        this.layerChooserController = new LayerChooserController(layerChooserPanel, paintModel);

        String iconImageFile = "src/main/java/ca/utoronto/utm/assignment2/Assets/PaintAppIcon.png";

        topPanel = new VBox();
        topPanel.getChildren().addAll(menuPanel, toolbarPanel);

        Region spacer = new Region();
        spacer.setStyle("-fx-background: #f8f1f0");

        bottomPanel = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Let the spacer expand
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
//        root.setRight(layerPane);
        root.setTop(topPanel);
        root.setBottom(bottomPanel);

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
}