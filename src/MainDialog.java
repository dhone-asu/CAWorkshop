import javafx.application.Application;
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

        fileOpen.setOnAction(event -> handleOpenAction(event));
        fileSave.setOnAction(event -> handleSaveAction(event));
        fileSaveAs.setOnAction(event -> handleSaveAsAction(event));
        fileNew.setOnAction(event -> handleNewAction(event));
        fileExport.setOnAction(event -> handleExportImage(event));
        fileExit.setOnAction(event -> handleExit(event));

        fileMenu.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs, fileExport, fileSep, fileExit);

        Menu scriptMenu = new Menu("Script");
        MenuItem scriptRun = new MenuItem("Run");
        MenuItem scriptGenerate = new MenuItem("Generate Rules");
        MenuItem scriptCleanUp = new MenuItem("Clean Up Rules");

        scriptRun.setOnAction(event -> handleRunAction(event));
        scriptGenerate.setOnAction(event -> handleGenerateAction(event));
        scriptCleanUp.setOnAction(event -> handleCleanUp(event));

        scriptMenu.getItems().addAll(scriptRun,scriptGenerate,scriptCleanUp);

        Menu helpMenu = new Menu("Help");
        MenuItem helpAbout = new MenuItem("About...");

        helpAbout.setOnAction(event -> handleAbout(event));
        helpMenu.getItems().addAll(helpAbout);

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

    private void handleExit(ActionEvent event)
    {
        stage.close();
    }

    private void handleCleanUp(ActionEvent event)
    {
        String str = txtScript.getText();
        List<String> lines = str.lines().toList();
        List<String> newLines = new ArrayList<>();
        Pattern linePattern = Pattern.compile("\\s*\\[(?<left>.*)\\]\\s*=\\s*\\[(?<right>.*)\\]\\s*");
        Pattern blankBlock = Pattern.compile("^(\\s|_)*$");

        for (String line: lines)
        {
            Matcher matcher = linePattern.matcher(line);

            if (matcher.matches())
            {
                String right = matcher.group("right");
                Matcher blankBlockMatcher = blankBlock.matcher(right);
                if (!blankBlockMatcher.matches())
                {
                    newLines.add(line);
                }
            }
            else
                newLines.add(line);
        }
        txtScript.setText(String.join("\n",newLines));
    }

    private void handleAbout(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ImageView aboutIcon = new ImageView(new Image("file:resources/icon.png"));
        aboutIcon.setFitWidth(128);
        aboutIcon.setFitHeight(128);
        alert.setHeaderText("Cellular Automaton Workshop");
        alert.getDialogPane().setGraphic(aboutIcon);
        alert.setTitle("About Cellular Automaton Workshop");
        alert.setContentText("Version 0.9.0\n" +
                "Author: Dennis Hone\n\n");
        alert.showAndWait();
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
