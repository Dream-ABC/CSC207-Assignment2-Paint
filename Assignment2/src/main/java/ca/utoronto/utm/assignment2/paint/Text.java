package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Text extends javafx.scene.text.Text implements Shape {

    private TextField textField;
    private Point topLeft;
    private Color color;

    public Text() {
        this.textField = new TextField();
        this.textField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.textField.setText(newValue);
        });
        this.color = Color.BLACK;
    }

    public Text(Point topLeft, Color color) {
        this.textField = new TextField();
        this.textField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.textField.setText(newValue);
        });
        this.topLeft = topLeft;
        this.color = color;
    }

    public void setWidth(double width) {
        this.textField.setPrefWidth(width);
    }

    public void setHeight(double height) {
        this.textField.setPrefHeight(height);
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public int getThickness() {
        return -1;
    }

    @Override
    public String getShape() {
        return "Text";
    }

    @Override
    public boolean overlaps(Eraser eraser) {
        double left = topLeft.x;
        double right = left + this.textField.getWidth();
        double top = topLeft.y;
        double bottom = top + this.textField.getHeight();

        // Check if eraser is in the correct range of x
        if (eraser.getCentre().x < left || eraser.getCentre().x > right) {
            return false;
        }
        // Check if eraser is in the correct range of y
        return !(eraser.getCentre().y < top) && !(eraser.getCentre().y > bottom);
    }

    @Override
    public void display(GraphicsContext g2d) {
        g2d.setFont(this.textField.getFont());
        g2d.setFill(this.color);
        g2d.fillText(this.textField.getText(), this.getTopLeft().x, this.getTopLeft().y);
    }
}
