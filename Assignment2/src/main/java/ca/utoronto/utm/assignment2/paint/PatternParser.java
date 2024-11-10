package ca.utoronto.utm.assignment2.paint;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The PatternParser class is responsible for parsing command strings and converting them into
 * executable command objects. These command objects are then used to restore the user's
 * previous drawing actions.
 */
public class PatternParser {

    private PaintPanel panel;
    private PaintModel model;
    private CommandHistory history;

    /**
     * Parses a command from a string and returns a corresponding Command object.
     *
     * @param content the string representation of the command to parse
     * @param panel   the PaintPanel instance containing the PaintModel and other necessary components
     * @return a Command object based on the parsed content, or null if the command is directly executed
     */
    public static Command parseLine(String content, PaintPanel panel) {
        PaintModel model = panel.getModel();
        CommandHistory history = model.getHistory();
        int layerIndex;

        // Gets the command type
        String commandType = content.substring(0, content.indexOf("#"));
        int beginIndex = content.indexOf("#") + 1;

        // All arraylist that may be used
        ArrayList<Shape> shapes;

        // Maps the command type to its corresponding case
        switch (commandType) {
            case "WIDTH":
                model.getSelectedLayer().setWidth(Double.parseDouble(content.substring(beginIndex)));
                return null;

            case "HEIGHT":
                model.getSelectedLayer().setHeight(Double.parseDouble(content.substring(beginIndex)));
                return null;

            case "AddLayer":
                String[] size = content.substring(beginIndex).split(",");
                double width = Double.parseDouble(size[0]);
                double height = Double.parseDouble(size[1]);
                return new AddLayerCommand(model, new PaintLayer(width, height), history);

            case "AddShape":
                // Gets the current layer where the shape is going to be drawn
                layerIndex = Integer.parseInt(content.substring(beginIndex, content.indexOf("&")));

                // Creates the correct shape
                String shapeType = content.substring(content.indexOf("&") + 1, content.indexOf("{"));
                // The fill style and line thickness are placeholders. They will be formally set at a later stage.
                Shape shape = panel.getShapeFactory().getShape(shapeType, "", 0);

                // Deal with polyline
                if (shapeType.equals("Polyline")) {
                    String[] coordinates = content.substring(content.indexOf(";") + 1).split(",");

                    // add initial point
                    Point firstPoint = new Point(Double.parseDouble(coordinates[0]),
                            Double.parseDouble(coordinates[1]));
                    ((Polyline) shape).addPoint(firstPoint);

                    // add middle points
                    for (int i = 0; i < coordinates.length - 2; i += 2) {
                        ((Polyline) shape).addPoint(new Point(Double.parseDouble(coordinates[i]),
                                Double.parseDouble(coordinates[i + 1])));
                    }

                    // add last point, set isClosed
                    Point lastPoint = new Point(Double.parseDouble(coordinates[coordinates.length - 3]),
                            Double.parseDouble(coordinates[coordinates.length - 2]));  // last element is "}"
                    ((Polyline) shape).addPoint(lastPoint);

                    if (Point2D.distance(lastPoint.x, lastPoint.y, firstPoint.x, firstPoint.y) < 10) {
                        ((Polyline) shape).setClosed(true);
                    }
                }

                // Gets corresponding information and sets them to the shape
                String[] dataString = content.substring(content.indexOf("{") + 1,
                        content.indexOf("}")).split(",");
                shape.setShape(dataString);  // This is where the fill style and line thickness are formally set

                return new AddShapeCommand(shape, model.getLayers().get(layerIndex), history, model);

            case "ChangeLayer":
                layerIndex = Integer.parseInt(content.substring(beginIndex));
                return new ChangeLayerCommand(model.getSelectedLayer(), model.getLayers().get(layerIndex), model);

            case "ChangeVisibility":
                layerIndex = Integer.parseInt(content.substring(beginIndex));
                return new ChangeLayerVisibilityCommand(model.getLayers().get(layerIndex), model);

            case "DeleteLayer":
                layerIndex = Integer.parseInt(content.substring(beginIndex));
                return new DeleteLayerCommand(model, model.getLayers().get(layerIndex), history);

            case "EraserStroke":
                String[] shapeIndices = content.substring(beginIndex).split(",");

                shapes = new ArrayList<>();

                // Gets all the removed shapes
                for (String index : shapeIndices) {
                    int i = Integer.parseInt(index);
                    shapes.add(model.getSelectedLayer().getShapes().get(i));
                }

                model.storeState();  // the eraser stroke command is created and executed here
                for (Shape s : shapes) {
                    model.removeShape(s);
                }
                model.getHistory().addToLast(shapes);
                return null;  // already executed

            case "Paste":
                // Restores the selection tool for the paste command
                String[] toolData = content.substring(content.indexOf("Selection Tool{") + 1).split(",");
                SelectionTool tool = new SelectionTool(model.getSelectedLayer());
                tool.setTool(toolData);

                // Restores shapes to paste
                shapes = new ArrayList<>();
                for (String index : content.substring(content.indexOf("}") + 1).split(",")) {
                    int i = Integer.parseInt(index);
                    shapes.add(model.getSelectedLayer().getShapes().get(i));
                }

                return new PasteCommand(model.getSelectedLayer(), history, shapes, tool, model);

            case "DeleteSelected":
                shapes = new ArrayList<>();
                for (String index : content.substring(beginIndex).split(",")) {
                    int i = Integer.parseInt(index);
                    shapes.add(model.getSelectedLayer().getShapes().get(i));
                }

                return new DeleteSelectedCommand(model.getSelectedLayer(), history, shapes);

            case "Drag":
                double x = Double.parseDouble(content.substring(beginIndex, content.indexOf("&"))
                        .split(",")[0]);
                double y = Double.parseDouble(content.substring(beginIndex, content.indexOf("&"))
                        .split(",")[1]);

                shapes = new ArrayList<>();
                for (String index : content.substring(content.indexOf("&") + 1).split(",")) {
                    int i = Integer.parseInt(index);
                    shapes.add(model.getSelectedLayer().getShapes().get(i));
                }

                DragCommand drag = new DragCommand(shapes, 0, 0, model.getSelectedLayer());
                history.execute(drag);
                drag.addShift(x, y);
                return null;  // already executed

            default:
                return null;
        }
    }
}
