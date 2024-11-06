package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.regex.*;

public class PatternParser {

    private PaintPanel panel;
    private PaintModel model;
    private CommandHistory history;

    public static Command parseLine(String content, PaintPanel panel) {
        PaintModel model = panel.getModel();
        CommandHistory history = model.getHistory();
        int layerIndex;

        String commandType = content.substring(0, content.indexOf("#"));
        int beginIndex = content.indexOf("#") + 1;

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
                String shapeType = content.substring(beginIndex, content.indexOf(":"));
                Shape shape = panel.getShapeFactory().getShape(shapeType);
                return new AddShapeCommand(shape, model.getSelectedLayer(), history);

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
                String[] shapeIndexes = content.substring(beginIndex).split(",");

                ArrayList<Shape> shapes = new ArrayList<>();

                for (String index : shapeIndexes) {
                    int i = Integer.parseInt(index);
                    shapes.add(model.getSelectedLayer().getShapes().get(i));
                }

                EraserStrokeCommand command = new EraserStrokeCommand(model.getSelectedLayer(), history);
                command.addRemovedShapes(shapes);
                return command;

            default:
                return null;
        }
    }
}
