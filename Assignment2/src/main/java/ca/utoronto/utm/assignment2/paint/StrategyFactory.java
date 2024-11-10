package ca.utoronto.utm.assignment2.paint;


public class StrategyFactory {
    public ShapeStrategy getStrategy(String shapeName, PaintPanel panel) {
        return switch (shapeName.toLowerCase()) { // ignore case
            case "circle" -> new CircleStrategy(panel);
            case "rectangle" -> new RectangleStrategy(panel);
            case "squiggle" -> new SquiggleStrategy(panel);
            case "square" -> new SquareStrategy(panel);
            case "oval" -> new OvalStrategy(panel);
            case "triangle" -> new TriangleStrategy(panel);
            case "stroke eraser" -> new StrokeEraserStrategy(panel);
            case "polyline" -> new PolylineStrategy(panel);
            case "selection tool" -> new SelectionToolStrategy(panel);
            case "text" -> new TextStrategy(panel);
            case "precision eraser" -> new PrecisionEraserStrategy(panel);
            default -> null;
        };
    }
}
