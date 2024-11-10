package ca.utoronto.utm.assignment2.paint;

public class ShapeFactory {
    public Shape getShape(String shapeName, String fillStyle, double lineThickness) {
        return switch (shapeName.toLowerCase()) {  // ignore case
            case "circle" -> new Circle(fillStyle, lineThickness);
            case "rectangle" -> new Rectangle(fillStyle, lineThickness);
            case "squiggle" -> new Squiggle(lineThickness);
            case "square" -> new Square(fillStyle, lineThickness);
            case "oval" -> new Oval(fillStyle, lineThickness);
            case "triangle" -> new Triangle(fillStyle, lineThickness);
            case "polyline" -> new Polyline(lineThickness);
            case "text" -> new Text();
            case "precision eraser" -> new PrecisionEraser();
            default -> null;
        };
    }
}
