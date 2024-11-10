package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * The Text class represents a text shape that can be drawn on the screen.
 * It implements the Shape interface and provides functionalities to set
 * text properties such as font, color, strikethrough, and underline.
 */
public class Text implements Shape {

    private final javafx.scene.text.Text textNode;
    private Color color;
    private boolean isStrikethrough;
    private boolean isUnderlined;
    private Point topLeft;
    private Point originalPosition;

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
        this.originalPosition = topLeft.copy();
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
     * Checks if the given eraser object overlaps with this Text object.
     *
     * @param tool the Tool object
     * @return True if the eraser overlaps with this Text object, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        double left = this.textNode.getLayoutBounds().getMinX() + topLeft.x;
        double right = this.textNode.getLayoutBounds().getMaxX() + topLeft.x;
        double top = this.textNode.getLayoutBounds().getMinY() + topLeft.y;
        double bottom = this.textNode.getLayoutBounds().getMaxY() + topLeft.y;

        double eraserLeft = tool.getTopLeft().x - (tool.getDimensionX() / 2.0);
        double eraserRight = tool.getTopLeft().x + (tool.getDimensionX() / 2.0);
        double eraserTop = tool.getTopLeft().y - (tool.getDimensionY() / 2.0);
        double eraserBottom = tool.getTopLeft().y + (tool.getDimensionY() / 2.0);

        return eraserRight >= left && eraserLeft <= right && eraserBottom >= top && eraserTop <= bottom;
    }

    /**
     * Shifts the top left point of Text by the specified horizontal and vertical offsets.
     *
     * @param x the horizontal offset
     * @param y the vertical offset
     */
    @Override
    public void shift(double x, double y) {
        this.topLeft.shift(x,y);
    }

    /**
     * Creates a copy of the Text instance.
     *
     * @return a copy of the Text instance
     */
    @Override
    public Text copy() {
        Text t = new Text();
        t.setText(textNode.getText());
        t.setFont(textNode.getFont());
        t.setColor(color);
        t.setStrikethrough(isStrikethrough);
        t.setUnderline(isUnderlined);
        t.setTopLeft(topLeft.copy());
        return t;
    }

    /**
     * Renders the text on the provided GraphicsContext with the specified font, color,
     * and optional strikethrough or underline styles.
     *
     * @param g2d the GraphicsContext for the current layer used to draw the Text
     */
    @Override
    public void display(GraphicsContext g2d) {
        // init text
        g2d.setLineDashes();
        g2d.setFont(this.textNode.getFont());
        g2d.setFill(this.color);

        // init stroke style (for underline and strikethrough)
        g2d.setLineWidth(this.textNode.getFont().getSize() * 0.08);
        g2d.setStroke(this.color);

        // Calculate the width and height of the textNode
        double textWidth = this.textNode.getLayoutBounds().getWidth();
        double textHeight = this.textNode.getLayoutBounds().getHeight();

        // Draw the textNode
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
     * Sets the properties of the Text based on the provided data array.
     *
     * @param data an array of strings containing the following information in order:
     *             data[0] - text content
     *             data[1] - text font
     *             data[2] - font size
     *             data[3] - x-coordinate of the top-left point
     *             data[4] - y-coordinate of the top-left point
     *             data[5] - "true" for bold and "false" otherwise
     *             data[6] - "true" for italic and "false" otherwise
     *             data[7] - "true" for strikethrough and "false" otherwise
     *             data[8] - "true" for underlined and "false" otherwise
     *             data[9] - color of the Text in web format
     */
    public void setShape(String[] data) {
        this.topLeft = new Point(Double.parseDouble(data[3]), Double.parseDouble(data[4]));
        this.color = Color.web(data[9]);

        this.textNode.setText(data[0]);
        this.isStrikethrough = data[7].equals("true");
        this.isUnderlined = data[8].equals("true");

        // set font
        FontWeight weight = Boolean.parseBoolean(data[5]) ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = Boolean.parseBoolean(data[6]) ? FontPosture.ITALIC : FontPosture.REGULAR;
        this.textNode.setFont(Font.font(data[1], weight, posture, Double.parseDouble(data[2])));
    }

    /**
     * Returns a string representation of the Text instance, including its text content,
     * font name, font size, original top-left position coordinates, style flags for bold, italic,
     * underlined, and strikethrough, as well as color.
     *
     * @return a string representation of the Text instance
     */
    public String toString() {
        return "Text{" + this.textNode.getText() + ","
                + this.textNode.getFont().getName() + "," + this.textNode.getFont().getSize() + ","
                + this.originalPosition.x + "," + this.originalPosition.y + ","
                + this.textNode.getFont().getStyle().contains("Bold") + ","
                + this.textNode.getFont().getStyle().contains("Italic") + ","
                + this.isStrikethrough + "," + this.isUnderlined + ","
                + this.color.toString() + "}";
    }
}
