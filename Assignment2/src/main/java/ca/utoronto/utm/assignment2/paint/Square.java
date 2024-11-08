package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A class to represent drawing squares.
 * Square implements the Shape interface.
 */
public class Square implements Shape {
    private Point topLeft;
    private Point origin;
    private double size;
    private Color color;
    private final String fillStyle;

    /**
     * Constructs a default black square with a size of 0.
     */
    public Square(String fillStyle) {
        this.size = 0;
        this.color = Color.BLACK;
        this.fillStyle = fillStyle;
    }

    /**
     * @return the top left point of the Square
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * @param topLeft top left corner of Square
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * @return the size of the Square
     */
    public double getSize() {
        return this.size;
    }

    /**
     * @param size size of Square (width and height)
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * @return the origin of the Rectangle (first mouse click)
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * @param origin origin of Square (first mouse click)
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * @return the color of the Square
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color color of Square
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the stroke thickness of the Square
     */
    @Override
    public int getThickness() {
        return -1;
    }

    /**
     * @return 'Square' as a string
     */
    @Override
    public String getShape() {
        return "Square";
    }

    /**
     * Checks if the Eraser is overlapping the Square.
     * If it is, then the Eraser will erase the Square.
     * @param eraser the Eraser instance which is currently erasing drawings
     * @return True if the Eraser should erase this Square, False otherwise
     */
    @Override
    public boolean overlaps(Tool tool) {
        double eraserLeft = tool.getCentre().x-(tool.getDimensionX()/2.0);
        double eraserRight = tool.getCentre().x+(tool.getDimensionX()/2.0);
        double eraserTop = tool.getCentre().y-(tool.getDimensionY()/2.0);
        double eraserBottom = tool.getCentre().y+(tool.getDimensionY()/2.0);

        double rectLeft = this.topLeft.x;
        double rectRight = this.topLeft.x + this.size;
        double rectTop = this.topLeft.y;
        double rectBottom = this.topLeft.y + this.size;

        return eraserRight >= rectLeft && eraserLeft <= rectRight && eraserBottom >= rectTop && eraserTop <= rectBottom;
    }

    /**
     * Displays the Square with user-created color and size.
     * @param g2d GraphicsContext
     */
    @Override
    public void display(GraphicsContext g2d) {
        if (this.fillStyle.equals("Solid")){
            g2d.setFill(this.color);
            g2d.fillRect(this.topLeft.x, this.topLeft.y,
                    this.size, this.size);
        }
        else if (this.fillStyle.equals("Outline")){
            g2d.setStroke(this.color);
            g2d.strokeRect(this.topLeft.x, this.topLeft.y,
                    this.size, this.size);
        }
    }
