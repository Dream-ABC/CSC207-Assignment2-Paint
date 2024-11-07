package ca.utoronto.utm.assignment2.paint;

public class ChangeLayerVisibilityCommand implements Command {

    private PaintLayer layer;
    private PaintModel model;
    private int index;

    public ChangeLayerVisibilityCommand(PaintLayer l, PaintModel m) {
        this.layer = l;
        this.model = m;
        this.index = m.getLayers().indexOf(this.layer);
        System.out.println("visibility:"+this.index);
    }

    public void execute() {
        layer.setVisible(!layer.isVisible());
    }

    public void undo() {
        layer.setVisible(!layer.isVisible());
    }

    public String toString() {
        System.out.println("visibility stored:"+this.index);
        return "ChangeVisibility#" + this.index;
    }
}
