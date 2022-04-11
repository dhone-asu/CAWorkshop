import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CellularAutomaton {
    List<Rule> rules;
    List<Color> colors;
    List<Pixel> initialPixels;
    int blockSize;
    int width, height;
    Color background;

    public CellularAutomaton()
    {
        rules = new ArrayList<>();
        colors = new ArrayList<>(List.of(Color.BLACK, Color.WHITE));
        background = Color.WHITE;
        blockSize = 3;
        height = 600;
        width = 400;
        initialPixels = new ArrayList<>(List.of(new Pixel(width / 2, 0, Color.BLACK)));
    }

    public CellularAutomaton(List<Color> colors, Color background, int blockSize, int width, int height)
    {
        this.colors = new ArrayList<>(colors);
        this.rules = new ArrayList<>();
        this.initialPixels = new ArrayList<>();
        this.background = background;
        this.blockSize = blockSize;
        this.width = width;
        this.height = height;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Pixel> getInitialPixels() {
        return initialPixels;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public boolean readFromFile()
    {
        try {
            FileInputStream is = new FileInputStream("ca.txt");
            Pattern keywordPattern = Pattern.compile("\\s*(?<keyword>\\S*)\\s*");
            String file = new String(is.readAllBytes());
            List<String> lines = file.lines().toList();

            for (String str : lines)
            {
                Matcher matcher = keywordPattern.matcher(str);

                while (matcher.find())
                {
                    System.out.printf("\"%s\"\n",matcher.group("keyword"));
                }

            }
            /*System.out.println(file);
            Pattern pattern = Pattern.compile("(\\w*\\s*)*");
            Matcher m = pattern.matcher(file);
            m.find();
            int n = m.groupCount();
            System.out.printf("%d\n", n);
            for (int i = 0; i < n; i++)
            {
                System.out.printf("\"%s\"\n", m.group(i));
            }*/
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    Rule findRule(Block block)
    {
        for (Rule rule : rules)
        {
            Block b = rule.getKey();
            if (rule.getKey().equals(block))
                return rule;
        }
        return null;
    }

    Image createImage()
    {
        WritableImage image = new WritableImage(width,height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++)
                pixelWriter.setColor(i,j,background);

        for (Pixel p : initialPixels)
            pixelWriter.setColor(p.getX(), p.getY(), p.getColor());

        for (int j = 1; j < height; j++)
        {
            for (int i = 0; i < width - blockSize; i++)
            {
                Block block = new Block(blockSize);

                for (int k = 0; k < blockSize; k++)
                    block.setPixel(k, pixelReader.getColor(i + k, j - 1));

                Rule rule = findRule(block);

                if (rule != null)
                {
                    for (int k = 0; k < rule.getBlockSize(); k++) {
                        Color pixelColor = rule.getValue().getPixel(k);
                        if (pixelColor != Color.TRANSPARENT) {
                            pixelWriter.setColor(i + k, j, pixelColor);
                        }
                    }
                }
            }
        }
        return image;
    }

}
