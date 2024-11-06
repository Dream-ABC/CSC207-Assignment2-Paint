package ca.utoronto.utm.assignment2.paint;

public class TextStrategy implements ShapeStrategy {

    private PaintPanel panel;

    public TextStrategy(PaintPanel p) {
        this.panel = p;
    }

    @Override
    public void mousePressed(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("Started Text");
        Point firstClick = new Point(mouseEvent.getX(), mouseEvent.getY());
        // Create a text using factory
        ShapeFactory shapeFactory = panel.getShapeFactory();
        Text text = (Text) shapeFactory.getShape(panel.getMode());
        this.panel.setCurrentShape(text);

        // Set info of text
        text.setTopLeft(firstClick);
        text.setColor(this.panel.getColor());
        this.panel.getModel().addShape(text);
    }

    @Override
    public void mouseDragged(javafx.scene.input.MouseEvent mouseEvent) {
        Text text = (Text) this.panel.getCurrentShape();

        double newWidth = Math.abs(text.getTopLeft().x-mouseEvent.getX());
        double newHeight = Math.abs(text.getTopLeft().y-mouseEvent.getY());
        text.setWidth(newWidth);
        text.setHeight(newHeight);

        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShape(text);
    }

    @Override
    public void mouseReleased(javafx.scene.input.MouseEvent mouseEvent) {
        Text text = (Text) this.panel.getCurrentShape();

        double newWidth = Math.abs(text.getTopLeft().x-mouseEvent.getX());
        double newHeight = Math.abs(text.getTopLeft().y-mouseEvent.getY());
        text.setWidth(newWidth);
        text.setHeight(newHeight);

        Shape shape = this.panel.getModel().getSelectedLayer().getShapes().getLast();
        this.panel.getModel().getSelectedLayer().removeShape(shape);
        this.panel.getModel().addShapeFinal(text);
        System.out.println("Added Text");
        this.panel.setCurrentShape(null);
    }
}
