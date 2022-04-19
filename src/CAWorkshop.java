import javafx.application.Application;
import javafx.stage.Stage;

public class CAWorkshop extends Application {

    public static MainDialog mainDialog;

    @Override
    public void start(Stage stage) throws Exception {
        mainDialog = new MainDialog(stage);
        mainDialog.showViewDialog();
    }
}
