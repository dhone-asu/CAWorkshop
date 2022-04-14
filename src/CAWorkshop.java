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
        /*ca = new CellularAutomaton(List.of(Color.BLACK,Color.WHITE), Color.WHITE,3,600,400);
        ca.readFromFile(new File("ca.txt"));
        Rule rule1 = new Rule(new Block(Color.BLACK, Color.BLACK, Color.BLACK),
                new Block(Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT));
        Rule rule2 = new Rule(new Block(Color.BLACK, Color.BLACK, Color.WHITE),
                new Block(Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT));
        Rule rule3 = new Rule(new Block(Color.BLACK, Color.WHITE, Color.BLACK),
                new Block(Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT));
        Rule rule4 = new Rule(new Block(Color.BLACK, Color.WHITE, Color.WHITE),
                new Block(Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT));
        Rule rule5 = new Rule(new Block(Color.WHITE, Color.BLACK, Color.BLACK),
                new Block(Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT));
        Rule rule6 = new Rule(new Block(Color.WHITE, Color.BLACK, Color.WHITE),
                new Block(Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT));
        Rule rule7 = new Rule(new Block(Color.WHITE, Color.WHITE, Color.BLACK),
                new Block(Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT));
        Rule rule8 = new Rule(new Block(Color.WHITE, Color.WHITE, Color.WHITE),
                new Block(Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT));
        ca.getRules().addAll(List.of(rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8));
        ca.getInitialPixels().add(new Pixel(ca.getWidth()/2, 0, Color.BLACK));*/
        ca = new CellularAutomaton();
        ca.readFromFile(new File("ca.txt"));

        System.out.printf("Blocksize: %d", ca.blockSize);

        for (Color c : ca.getColors()) {
            System.out.printf("Color: %s\n",c.toString());
        }

        /*ca = new CellularAutomaton(List.of(Color.BLUE, Color.RED, Color.WHITE), Color.WHITE, 2, 600, 400);
        Rule rule1 = new Rule(new Block(Color.BLUE, Color.BLUE), new Block(Color.RED, Color.RED));//
        Rule rule2 = new Rule(new Block(Color.RED, Color.RED), new Block(Color.BLUE, Color.BLUE));//
        Rule rule3 = new Rule(new Block(Color.RED, Color.BLUE), new Block(Color.RED, Color.BLUE));//
        Rule rule4 = new Rule(new Block(Color.BLUE, Color.RED), new Block(Color.BLUE, Color.RED));//
        Rule rule5 = new Rule(new Block(Color.BLUE, Color.WHITE), new Block(Color.WHITE, Color.BLUE));
        Rule rule6 = new Rule(new Block(Color.RED, Color.WHITE), new Block(Color.RED, Color.WHITE));
        Rule rule7 = new Rule(new Block(Color.WHITE, Color.BLUE), new Block(Color.BLUE, Color.WHITE));
        Rule rule8 = new Rule(new Block(Color.WHITE, Color.RED), new Block(Color.WHITE, Color.RED));
        Rule rule9 = new Rule(new Block(Color.WHITE, Color.WHITE), new Block(Color.WHITE, Color.WHITE));
        ca.getRules().addAll(List.of(rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8,rule9));
        ca.getInitialPixels().add(new Pixel(200,0,Color.RED));
        ca.getInitialPixels().add(new Pixel(201,0,Color.RED));*/
        viewDialog = new ViewDialog(stage);
        viewDialog.showViewDialog();
    }
}
