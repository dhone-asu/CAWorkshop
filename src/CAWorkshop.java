import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

public class CAWorkshop extends Application {

    public static MainDialog mainDialog;

    @Override
    public void start(Stage stage) throws Exception {
        mainDialog = new MainDialog(stage);
        stage.getIcons().add(new Image("file:resources/icon.png"));
        mainDialog.showViewDialog();
    }
}
