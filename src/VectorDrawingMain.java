import controller.DrawAreaController;
import model.DrawAreaModel;
import view.VectorDrawingView;

public class VectorDrawingMain {

    public static void main(String argv[]) {
        DrawAreaModel drawAreaModel = new DrawAreaModel();
        DrawAreaController drawAreaController = new DrawAreaController(drawAreaModel);
        new VectorDrawingView(drawAreaModel, drawAreaController);
    }
}
