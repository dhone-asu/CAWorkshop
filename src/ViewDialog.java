import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ViewDialog {
    Stage stage;
    Scene scene;

    ImageView iv;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showViewDialog()
    {
        stage.show();
    }


    public ViewDialog(Stage stage)
    {
        stage.setTitle("Cellular Automaton Workshop");
        this.stage = stage;

        BorderPane borderPane = new BorderPane();
        scene = new Scene(borderPane, 800, 500);
        stage.setScene(scene);

        iv = new ImageView();
        borderPane.setCenter(iv);

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10,10,10,10));
        flowPane.setHgap(10);
        borderPane.setBottom(flowPane);

        Button btnRun = new Button("Run");
        flowPane.getChildren().add(btnRun);
        btnRun.setOnAction(event -> handleRunAction(event));


        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileOpen = new MenuItem("Open");
        SeparatorMenuItem fileSep1 = new SeparatorMenuItem();
        MenuItem fileExit = new MenuItem("Exit");

        fileMenu.getItems().addAll(fileOpen, fileSep1, fileExit);
        menuBar.getMenus().add(fileMenu);
        borderPane.setTop(menuBar);
    }

    public void handleRunAction(ActionEvent event)
    {
        Image i = CAWorkshop.ca.createImage();

        iv.setImage(i);
    }
}
