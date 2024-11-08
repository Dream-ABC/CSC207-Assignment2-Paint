package ca.utoronto.utm.assignment2.paint;

public class ShapeFactory {
    public Shape getShape(String shapeName, String fillStyle) {
        return switch (shapeName.toLowerCase()) {  // ignore case
            case "circle" -> new Circle(fillStyle);
            case "rectangle" -> new Rectangle(fillStyle);
            case "squiggle" -> new Squiggle();
            case "square" -> new Square(fillStyle);
            case "oval" -> new Oval(fillStyle);
            case "triangle" -> new Triangle(fillStyle);
            case "polyline" -> new Polyline();
            case "text" -> new Text();
            default -> null;
        };
    }
}
