import javafx.scene.paint.Color;

public class Rule {
    int blockSize;
    Color background;
    Block key;
    Block value;

    public Rule(int blockSize)
    {
        this.blockSize = blockSize;
        key = new Block(blockSize);
        value = new Block(blockSize);

    }

    public Rule(Block key, Block value)
    {
        this.blockSize = key.getSize();
        this.key = key;
        this.value = value;
    }

    public void setKey(Block key) {
        this.key = key;
    }

    public void setValue(Block value) {
        this.value = value;
    }

    public Block getKey() {
        return key;
    }

    public Block getValue() {
        return value;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public int getBlockSize() {
        return blockSize;
    }
}
