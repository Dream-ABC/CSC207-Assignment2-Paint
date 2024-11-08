package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The Text class represents a text shape that can be drawn on the screen.
 * It implements the Shape interface and provides functionalities to set
 * text properties such as font, color, strikethrough, and underline.
 */
public class Text implements Shape {

    private javafx.scene.text.Text textNode;
    private Color color;
    private boolean isStrikethrough;
    private boolean isUnderlined;
    private Point topLeft;

    /**
     * Constructs a Text object with default settings.
     * This constructor initializes the text node with the default font "Arial" at size 12
     * and sets the text color to black.
     */
    public Text() {
        this.color = Color.BLACK;
        this.textNode = new javafx.scene.text.Text();
        this.textNode.setFont(Font.font("Arial", 12));
    }

    /**
     * Sets the top-left position of the Text object.
     *
     * @param topLeft the Point object representing the top-left position of the Text object
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Retrieves the top-left position of the Text object.
     *
     * @return the top-left position as a Point object
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Sets the font of the text node to the specified font.
     *
     * @param font the Font object to be set for the text node
     */
    public void setFont(Font font) {
        this.textNode.setFont(font);
    }

    /**
     * Sets the strikethrough property of the text.
     *
     * @param strikethrough a boolean indicating whether the text should have a strikethrough line.
     */
    public void setStrikethrough(boolean strikethrough) {
        this.isStrikethrough = strikethrough;
    }

    /**
     * Sets the underline property of the text.
     *
     * @param underlined a boolean indicating whether the text should be underlined
     */
    public void setUnderline(boolean underlined) {
        this.isUnderlined = underlined;
    }

    /**
     * Sets the text content for the text node.
     *
     * @param text the text content to be set for the text node
     */
    public void setText(String text) {
        this.textNode.setText(text);
    }

    /**
     * Retrieves the text content from the text node.
     *
     * @return the text content of the text node as a String.
     */
    public String getText() {
        return this.textNode.getText();
    }

    /**
     * Sets the color of the text object.
     *
     * @param color the Color object to be set for the text node
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retrieves the color of the text object.
     *
     * @return the Color object representing the text color.
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Retrieves the thickness of the text object.
     *
     * @return an integer representing the thickness
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * Retrieves the shape description of the text.
     *
     * @return a string representing the shape of the text, which is "Text".
     */
    @Override
    public String getShape() {
        return "Text";
    }

    /**
     * Checks if the given eraser object overlaps with this Text object.
     *
     * @param eraser the Eraser object
     * @return true if the eraser overlaps with this Text object, false otherwise
     */
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

    /**
     * Renders the text on the provided GraphicsContext with the specified font, color,
     * and optional strikethrough or underline styles.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the text
     */
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

    /**
     * Returns a string representation of the Text object, including text content,
     * font details, position, style attributes, and color.
     *
     * @return a string representation of the Text object
     */
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
