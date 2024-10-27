package ca.utoronto.utm.assignment2.paint;

public class ShapeFactory {
    public Shape getShape(String shapeName) {
        switch (shapeName.toLowerCase()) {  // ignore case
            case "circle":
                return new Circle();
            case "rectangle":
                return new Rectangle();
            case "squiggle":
                return new Squiggle();
            case "square":
                return new Square();
            case "oval":
                return new Oval();
            case "triangle":
                return new Triangle();

            // add more shapes here

            default:
                break;
        }
        return null;  // case null or invalid shapeName
    }
}
