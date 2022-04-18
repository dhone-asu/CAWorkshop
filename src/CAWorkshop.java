import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/*
                //if (pixel1.equals(Color.BLACK) && pixel2.equals(Color.BLACK) && pixel3.equals(Color.BLACK))
                    pw.setColor(i + offset, j, Color.WHITE);
                //else if (pixel1.equals(Color.BLACK) && pixel2.equals(Color.BLACK) && pixel3.equals(Color.WHITE))
                    pw.setColor(i + offset, j, Color.WHITE);
                //else if (pixel1.equals(Color.BLACK) && pixel2.equals(Color.WHITE) && pixel3.equals(Color.BLACK))
                    pw.setColor(i + offset, j, Color.WHITE);
                //else if (pixel1.equals(Color.BLACK) && pixel2.equals(Color.WHITE) && pixel3.equals(Color.WHITE))
                    pw.setColor(i + offset, j, Color.BLACK);
                //else if (pixel1.equals(Color.WHITE) && pixel2.equals(Color.BLACK) && pixel3.equals(Color.BLACK))
                    pw.setColor(i + offset, j, Color.WHITE); //
                //else if (pixel1.equals(Color.WHITE) && pixel2.equals(Color.BLACK) && pixel3.equals(Color.WHITE))
                    pw.setColor(i + offset, j, Color.BLACK);
                else if (pixel1.equals(Color.WHITE) && pixel2.equals(Color.WHITE) && pixel3.equals(Color.BLACK))
                    pw.setColor(i + offset, j, Color.BLACK);
                else if (pixel1.equals(Color.WHITE) && pixel2.equals(Color.WHITE) && pixel3.equals(Color.WHITE))
                    pw.setColor(i + offset, j, Color.WHITE);
 */

public class CAWorkshop extends Application {

    public static ViewDialog viewDialog;
    public static CellularAutomaton ca = new CellularAutomaton();

    @Override
    public void start(Stage stage) throws Exception {
        ca = new CellularAutomaton();
        ca.readFromFile(new File("ca.txt"));

        viewDialog = new ViewDialog(stage);
        viewDialog.showViewDialog();
    }
}
