package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text implements Shape {

    private javafx.scene.text.Text textNode;
    private Color color;
    private boolean isStrikethrough;
    private boolean isUnderlined;
    private Point topLeft;

    public Text() {
        this.color = Color.BLACK;
        this.textNode = new javafx.scene.text.Text();
        this.textNode.setFont(Font.font(Font.getFamilies().getFirst(), 12));
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setFont(Font font) {
        this.textNode.setFont(font);
        System.out.println("font style:" + font.getStyle());
        System.out.println("style from node:"+this.textNode.getFont().getStyle());
        System.out.println("set font:" + this.textNode.getFont());
    }

    public void setStrikethrough(boolean strikethrough) {
        this.isStrikethrough = strikethrough;
    }

    public void setUnderline(boolean underlined) {
        this.isUnderlined = underlined;
    }

    public void setText(String text) {
        this.textNode.setText(text);
    }

    public String getText() {
        return this.textNode.getText();
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
        double right = left + this.textNode.getLayoutBounds().getWidth();
        double top = topLeft.y;
        double bottom = top + this.textNode.getLayoutBounds().getHeight();

        // Check if eraser is in the correct range of x
        if (eraser.getCentre().x < left || eraser.getCentre().x > right) {
            return false;
        }
        // Check if eraser is in the correct range of y
        return !(eraser.getCentre().y < top) && !(eraser.getCentre().y > bottom);
    }

    @Override
    public void display(GraphicsContext g2d) {
        // init text
        g2d.setFont(this.textNode.getFont());
        g2d.setFill(this.color);

        // init stroke style (for underline and strikethrough)
        g2d.setLineWidth(this.textNode.getFont().getSize() * 0.08);
        g2d.setStroke(this.color);

        // Calculate the width and height of the textNode
        double textWidth = this.textNode.getLayoutBounds().getWidth();
        double textHeight = this.textNode.getLayoutBounds().getHeight();

        // Draw the textNode
        System.out.println(this.textNode.getText());
        g2d.fillText(textNode.getText(), this.topLeft.x, this.topLeft.y);

        // Draw underline and strikethrough
        if (isUnderlined) {
            // Draw a line 2 pixels below the baseline
            g2d.strokeLine(this.topLeft.x,
                    this.topLeft.y + 2,
                    this.topLeft.x + textWidth,
                    this.topLeft.y + 2);
        }
        if (isStrikethrough) {
            // Draw a line through the center of the textNode
            g2d.strokeLine(this.topLeft.x,
                    this.topLeft.y - textHeight / 4,
                    this.topLeft.x + textWidth,
                    this.topLeft.y - textHeight / 4);
        }
    }

    public String toString() {
        return "Text{" + this.textNode.getText() + ","
                + this.textNode.getFont().getName() + "," + this.textNode.getFont().getSize() + ","
                + this.topLeft.x + "," + this.topLeft.y + ","
                + this.textNode.getFont().getStyle().contains("Bold") + ","
                + this.textNode.getFont().getStyle().contains("Italic") +","
                + this.isStrikethrough + "," + this.isUnderlined + ","
                + this.color.toString() + "}";
    }
}
