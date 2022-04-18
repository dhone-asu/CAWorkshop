import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.Locale;

public class ViewDialog {
    Stage stage;
    Scene scene;
    File scriptFile;
    CellularAutomaton cellularAutomaton;

    ImageView imageView;
    TextArea txtScript;

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

        BorderPane mainBorderPane = new BorderPane();
        scene = new Scene(mainBorderPane, 800, 500);
        stage.setScene(scene);

        //iv = new ImageView();
        //borderPane.setCenter(iv);

        SplitPane splitPane = new SplitPane();
        mainBorderPane.setCenter(splitPane);

        BorderPane leftBorderPane = new BorderPane();
        splitPane.getItems().add(leftBorderPane);

        txtScript = new TextArea();
        leftBorderPane.setCenter(txtScript);

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10,10,10,10));
        flowPane.setHgap(10);
        leftBorderPane.setBottom(flowPane);

        BorderPane rightBorderPane = new BorderPane();
        splitPane.getItems().add(rightBorderPane);

        imageView = new ImageView();
        rightBorderPane.setCenter(imageView);


        Button btnRun = new Button("Run");
        flowPane.getChildren().add(btnRun);
        btnRun.setOnAction(event -> handleRunAction(event));


        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileNew = new MenuItem("New");
        MenuItem fileOpen = new MenuItem("Open");
        fileOpen.setOnAction(event -> handleOpenAction(event));
        MenuItem fileSave = new MenuItem("Save");
        MenuItem fileSaveAs = new MenuItem("Save As...");
        SeparatorMenuItem fileSep1 = new SeparatorMenuItem();
        MenuItem fileExit = new MenuItem("Exit");

        fileMenu.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs, fileSep1, fileExit);
        menuBar.getMenus().add(fileMenu);
        mainBorderPane.setTop(menuBar);
    }

    public void handleRunAction(ActionEvent event)
    {
        if ((scriptFile != null) && (scriptFile.exists())) {
            cellularAutomaton = new CellularAutomaton();
            InputStream is = new ByteArrayInputStream(txtScript.getText().getBytes());
            cellularAutomaton.read(is);
            Image i = cellularAutomaton.createImage();

            imageView.setImage(i);
        }
    }

    public void handleOpenAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CA Workshop Files", "*.txt", "*.ca"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(stage);
        if ((file != null) && (file.exists()))
        {
            scriptFile = file;
            updateTxtScript();
        }
    }

    public void updateTxtScript()
    {
        if (scriptFile != null)
        {
            try
            {
            txtScript.setText(Files.readString(scriptFile.toPath()));
            }
            catch (IOException e)
            {

            }
        }
    }
}
