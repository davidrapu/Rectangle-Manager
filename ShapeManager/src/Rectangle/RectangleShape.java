package Rectangle;

import java.awt.*;

public class RectangleShape implements Comparable<RectangleShape> {
    private Color color = Color.BLACK;
    private int id;
    private int width;
    private int height;
    private int xPos;
    private int yPos;

    public RectangleShape(int id, int width, int height, int xPos, int yPos, Color color) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    }

    // Encapsulation
    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public int getWidth() {return this.width;}
    public void setWidth(int width) {this.width = width;}
    public int getHeight() {return this.height;}
    public void setHeight(int height) {this.height = height;}
    public int getXPos() {return this.xPos;}
    public int getYPos() {return this.yPos;}
    public Color getColor() {return this.color;}
    public void setColor(Color color) {this.color = color;}

    // Method to return the string value of the color
    String getColorToString(Color color) {
        if (color == Color.BLACK)return "Black";
        else if (color == Color.RED)return "Red";
        else if (color == Color.GREEN)return "Green";
        else if (color == Color.BLUE)return "Blue";
        else if (color == Color.MAGENTA)return "Magenta";
        else if (color == Color.YELLOW)return "Yellow";
        else if (color == Color.CYAN)return "Cyan";
        else if (color == Color.PINK)return "Pink";
        else if (color == Color.ORANGE)return "Orange";
        else return "Not an accepted color";
    }

    public String toString() {
        RectangleManager rm = new RectangleManager();

        return "ID: " + this.getId() + "\nWidth: " + this.getWidth() +
                "\nHeight: " + this.getHeight() + "\nx: " + this.getXPos() + "\ny: " +
                this.getYPos() + "\nColor: " + getColorToString(this.getColor()) + "\n";
    }


    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(this.xPos, this.yPos, this.width, this.height);
        g2d.setPaint(this.color);
        g2d.fillRect(this.xPos, this.yPos, this.width, this.height);
    }

    @Override
    public int compareTo(RectangleShape other) {
        return Integer.compare(this.id, other.id);
    }
}
