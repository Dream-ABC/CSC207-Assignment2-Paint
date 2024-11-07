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
                layerIndex = Integer.parseInt(content.substring(beginIndex, content.indexOf("&")));
                String shapeType = content.substring(content.indexOf("&") + 1, content.indexOf("{"));
                Shape shape = panel.getShapeFactory().getShape(shapeType);

                String[] dataString = content.substring(content.indexOf("{") + 1,
                        content.indexOf("}")).split(",");
                shape.setShape(dataString);

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

                for (String index : shapeIndices) {
                    int i = Integer.parseInt(index);
                    shapes.add(model.getSelectedLayer().getShapes().get(i));
                }

                EraserStrokeCommand command = new EraserStrokeCommand(model.getSelectedLayer(), history);

                model.storeState();
                command.addRemovedShapes(shapes);
                for (Shape s : shapes) {
                    model.getSelectedLayer().removeShape(s);
                }
                return command;

            default:
                return null;
        }
    }
}
