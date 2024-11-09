package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

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
     * @param panel the PaintPanel instance containing the PaintModel and other necessary components
     * @return a Command object based on the parsed content, or null if the command is directly executed
     */
    public static Command parseLine(String content, PaintPanel panel) {
        PaintModel model = panel.getModel();
        CommandHistory history = model.getHistory();
        int layerIndex;

        // Gets the command type
        String commandType = content.substring(0, content.indexOf("#"));
        int beginIndex = content.indexOf("#") + 1;

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

                ArrayList<Shape> shapes = new ArrayList<>();

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
                return null; // already executed

            default:
                return null;
        }
    }
}
