import javafx.scene.paint.Color;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Block{
    int size;

    Color[] pixels;

    public Block()
    {
        super();
    }

    public Block(int size)
    {
       setSize(size);
    }

    public Block(Color ... colors)
    {
        setSize(colors.length);
        for (int i = 0; i < colors.length; i++)
        {
            pixels[i] = colors[i];
        }
    }

    public Block(List<Color> colors)
    {
        setSize(colors.size());
        for (int i = 0; i < colors.size(); i++)
        {
            pixels[i] = colors.get(i);
        }
    }

    public Color getPixel(int i)
    {
        return pixels[i];
    }

    public void setPixel(int i, Color color)
    {
        pixels[i] = color;
    }

    public void setSize(int size) {
        this.size = size;
        pixels = new Color[size];
    }

    public int getSize() {
        return size;
    }


    public static Block parseBlock(String str)
    {
        Pattern blockPattern = Pattern.compile("\\s*(?<color>\\S+)");
        Matcher blockMatcher = blockPattern.matcher(str);

        List<Color> colors = new ArrayList<>();
        while (blockMatcher.find())
        {
            String colorStr = blockMatcher.group("color");
            if (colorStr.equals("_"))
                colors.add(Color.TRANSPARENT);
            else
                colors.add(Color.valueOf(colorStr));
        }

        Block block = new Block(colors);
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return size == block.size && Arrays.equals(pixels, block.pixels);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(pixels);
        return result;
    }
}
