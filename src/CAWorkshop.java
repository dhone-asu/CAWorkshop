import javafx.application.Application;
import javafx.stage.Stage;


public class CAWorkshop extends Application {

    ViewDialog viewDialog;

    @Override
    public void start(Stage stage) throws Exception {
        viewDialog = new ViewDialog(stage);
        viewDialog.showViewDialog();
    }
}
