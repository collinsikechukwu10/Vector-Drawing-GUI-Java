import controller.listeners.DrawingAreaController;
import model.DrawAreaModel;
import view.VectorDrawingView;

public class VectorDrawingMain {

    public static void main(String argv[]) {
        DrawAreaModel drawAreaModel = new DrawAreaModel();
        DrawingAreaController drawingAreaController = new DrawingAreaController(drawAreaModel);
        new VectorDrawingView(drawAreaModel, drawingAreaController);
    }
}
