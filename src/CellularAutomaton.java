import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Handler;
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
        //colors = new ArrayList<>(List.of(Color.BLACK, Color.WHITE));
        colors = new ArrayList<>();
        background = Color.WHITE;
        blockSize = 3;
        height = 600;
        width = 400;
        //initialPixels = new ArrayList<>(List.of(new Pixel(width / 2, 0, Color.BLACK)));
        initialPixels = new ArrayList<>();
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setInitialPixels(List<Pixel> initialPixels) {
        this.initialPixels = initialPixels;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
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

    public boolean read(InputStream is)
    {
        CAReader reader = new CAReader(this);

        reader.read(is);
        return true;
    }

    public boolean readFromFile(File file)
    {
        try {
            FileInputStream is = new FileInputStream(file);
            CAReader reader = new CAReader(this);
            reader.read(is);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
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
