import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;

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
