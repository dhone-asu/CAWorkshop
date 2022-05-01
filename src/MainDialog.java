import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainDialog {
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


    public MainDialog(Stage stage)
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
        txtScript.setFont(Font.font("Monospaced", 14));
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
        btnRun.setOnAction(event -> handleRunAction(event));

        Button btnGenerateRules = new Button("Generate Rules");
        btnGenerateRules.setOnAction(event -> handleGenerateAction(event));
        flowPane.getChildren().addAll(btnRun,btnGenerateRules);


        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileNew = new MenuItem("New");
        MenuItem fileOpen = new MenuItem("Open");
        MenuItem fileSave = new MenuItem("Save");
        MenuItem fileSaveAs = new MenuItem("Save As...");
        MenuItem fileExport = new MenuItem("Export Image...");

        SeparatorMenuItem fileSep = new SeparatorMenuItem();
        MenuItem fileExit = new MenuItem("Exit");

        Menu scriptMenu = new Menu("Script");
        MenuItem scriptRun = new MenuItem("Run");
        MenuItem scriptGenerate = new MenuItem("Generate Rules");

        scriptRun.setOnAction(event -> handleRunAction(event));
        scriptGenerate.setOnAction(event -> handleGenerateAction(event));

        scriptMenu.getItems().addAll(scriptRun,scriptGenerate);

        Menu helpMenu = new Menu("Help");
        MenuItem helpAbout = new MenuItem("About...");
        helpMenu.getItems().addAll(helpAbout);

        fileOpen.setOnAction(event -> handleOpenAction(event));
        fileSave.setOnAction(event -> handleSaveAction(event));
        fileSaveAs.setOnAction(event -> handleSaveAsAction(event));
        fileNew.setOnAction(event -> handleNewAction(event));
        fileExport.setOnAction(event -> handleExportImage(event));

        fileMenu.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs, fileExport, fileSep, fileExit);
        menuBar.getMenus().addAll(fileMenu,scriptMenu,helpMenu);
        mainBorderPane.setTop(menuBar);
    }

    private void handleExportImage(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Portable Network Graphic", "*.png");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPEG", "*.jpg");
        FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("Graphic Interchange Format", "*.gif");
        FileChooser.ExtensionFilter anyFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
        fileChooser.getExtensionFilters().addAll(pngFilter, jpgFilter, gifFilter, anyFilter);
        File file = fileChooser.showSaveDialog(stage);
        String format;
        FileChooser.ExtensionFilter filter =  fileChooser.getSelectedExtensionFilter();
        if (filter.equals(pngFilter))
            format = "PNG";
        else if (filter.equals(jpgFilter))
            format = "JPG";
        else if (filter.equals(gifFilter))
            format = "GIF";
        else
            format = "PNG";
        System.out.println(format);
        try
        {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            ImageIO.write(bufferedImage, format, file);

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void handleGenerateAction(ActionEvent event)
    {
        Pattern colorsStatementPattern = Pattern.compile("^colors\\s+(?<colors>.*)$",Pattern.MULTILINE);
        Pattern blocksizeStatementPattern = Pattern.compile("^blocksize\\s+(?<blocksize>\\d+)$",Pattern.MULTILINE);
        String str = txtScript.getText();
        Matcher colorsStatementMatcher = colorsStatementPattern.matcher(str);
        Matcher blocksizeStatementMatcher = blocksizeStatementPattern.matcher(str);
        int blockSize = 0;

        if (blocksizeStatementMatcher.find())
        {
            blockSize = Integer.parseInt(blocksizeStatementMatcher.group("blocksize"));
        }

        if (colorsStatementMatcher.find())
        {
            String blockStr = colorsStatementMatcher.group("colors");
            Pattern blockPattern = Pattern.compile("\\s*(?<color>\\S+)");
            Matcher blockMatcher = blockPattern.matcher(blockStr);
            List<String> colors = new ArrayList<>();

            while (blockMatcher.find())
            {
                String colorStr = blockMatcher.group("color");
                colors.add(colorStr);
            }

            String ruleBlock = CAWriter.generateRuleBlock(blockSize, colors);
            txtScript.setText(str + "\n" + ruleBlock);
        }
    }

    private void handleRunAction(ActionEvent event)
    {
            cellularAutomaton = new CellularAutomaton();
            InputStream is = new ByteArrayInputStream(txtScript.getText().getBytes());
            cellularAutomaton.read(is);

            System.out.printf("%d", cellularAutomaton.blockSize);
            Image i = cellularAutomaton.createImage();

            imageView.setImage(i);
    }

    private void handleOpenAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CA Workshop Files", "*.txt", "*.ca"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(stage);

        if ((file != null) && (file.exists()))
        {
            scriptFile = file;
            readScriptFromFile(scriptFile);
        }
    }

    private void handleSaveAction(ActionEvent event)
    {
        if ((scriptFile != null) && (scriptFile.exists()))
        {
                saveScript(scriptFile);
        }
        else
            handleSaveAsAction(event);
    }

    private void handleSaveAsAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CA Workshop Files", "*.txt", "*.ca"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            if (file.exists()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Overwrite");
                alert.setContentText(String.format("Are you sure you want to overwrite \"%s\"?", file.getName()));
                if (alert.getResult() == ButtonType.OK) {
                    saveScript(file);
                    scriptFile = file;
                }
            } else {
                saveScript(file);
                scriptFile = file;
            }
        }
    }

    public boolean saveScript(File file)
    {
        if (file == null)
            return false;

        try {
            if (!file.exists())
                file.createNewFile();
            if (file.canWrite()) {
                Files.writeString(file.toPath(), txtScript.getText(), StandardOpenOption.WRITE);
                return true;
            }
            else
                return false;
        }
        catch (IOException e)
        {
            System.out.println(String.format("Exception: %s\n", e.toString()));
            return false;
        }
    }

    private void handleNewAction(ActionEvent event)
    {
        scriptFile = null;
        txtScript.setText("");
        imageView.setImage(null);
    }

    public void readScriptFromFile(File file)
    {
        if (file != null)
        {
            try
            {
                txtScript.setText(Files.readString(file.toPath()));
            }
            catch (IOException e)
            {
                System.out.println(String.format("Exception: %s\n", e.toString()));
            }
        }
    }
}
