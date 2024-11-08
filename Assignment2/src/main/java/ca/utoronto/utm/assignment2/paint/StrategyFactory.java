package ca.utoronto.utm.assignment2.paint;

public class StrategyFactory {
    public ShapeStrategy getStrategy(String shapeName, PaintPanel panel) {
        switch (shapeName.toLowerCase()) { // ignore case
            case "circle":
                return new CircleStrategy(panel);
            case "rectangle":
                return new RectangleStrategy(panel);
            case "squiggle":
                return new SquiggleStrategy(panel);
            case "square":
                return new SquareStrategy(panel);
            case "oval":
                return new OvalStrategy(panel);
            case "triangle":
                return new TriangleStrategy(panel);
            case "eraser":
                return new EraserStrategy(panel);
            case "polyline":
                return new PolylineStrategy(panel);
            case "selectiontool":
                return new SelectionToolStrategy(panel);
            case "text":
                return new TextStrategy(panel);
            // add more strategies here

            default:
                break;
        }
        return null; // case null or invalid shapeName
    }
}
