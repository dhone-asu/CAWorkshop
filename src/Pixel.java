import javafx.scene.paint.Color;

public class Pixel {
    int x, y;
    Color color;

    public Pixel() {
        super();
    }

    public Pixel(int x, int y, Color color) {
        super();
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
