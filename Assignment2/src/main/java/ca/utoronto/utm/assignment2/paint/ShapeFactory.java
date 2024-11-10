package ca.utoronto.utm.assignment2.paint;

/**
 * A factory to instantiate new shapes for all shapes that users can draw.
 */
public class ShapeFactory {
    /**
     * Constructs a new Shape based on the provided parameters.
     * @param shapeName a string representation of the shape's name
     * @param fillStyle "Solid"/"Outline"
     * @param lineThickness ranges from 1.0 to 10.0
     */
    public Shape getShape(String shapeName, String fillStyle, double lineThickness) {
        return switch (shapeName.toLowerCase()) {  // ignore case
            case "circle" -> new Circle(fillStyle, lineThickness);
            case "rectangle" -> new Rectangle(fillStyle, lineThickness);
            case "squiggle" -> new Squiggle(lineThickness);
            case "square" -> new Square(fillStyle, lineThickness);
            case "oval" -> new Oval(fillStyle, lineThickness);
            case "triangle" -> new Triangle(fillStyle, lineThickness);
            case "polyline" -> new Polyline(fillStyle, lineThickness);
            case "text" -> new Text();
            case "precision eraser" -> new PrecisionEraser();
            default -> null;
        };
    }
}
