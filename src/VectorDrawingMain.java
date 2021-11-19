import controller.DrawAreaController;
import model.DrawAreaModel;
import view.VectorDrawingView;

/**
 * Main entrypoint for the vector drawing application.
 *
 * @author 210032207
 */
public class VectorDrawingMain {
    /**
     * Static entrypoint method that launches the application.
     *
     * @param argv command line arguments
     */
    public static void main(String argv[]) {
        DrawAreaModel drawAreaModel = new DrawAreaModel();
        DrawAreaController drawAreaController = new DrawAreaController(drawAreaModel);
        new VectorDrawingView(drawAreaModel, drawAreaController);
    }
}
