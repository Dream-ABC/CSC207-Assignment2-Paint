package ca.utoronto.utm.assignment2.paint;

/**
 * A factory to instantiate new shape strategies for all shapes that users can draw.
 */
public class StrategyFactory {
    /**
     * Constructs a new ShapeStrategy based on the provided parameters.
     * @param shapeName a string representation of the shape's name
     * @param panel the PaintPanel where drawn shapes are displayed
     */
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
