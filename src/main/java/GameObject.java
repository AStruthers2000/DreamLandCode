import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GameObject {
    private String type;
    private Rectangle item;
    private Color objectColor;
    private BufferedImage objectImage;
    private String fullPath = System.getProperty("user.dir") + "\\Sprites\\LevelSprites\\";

    GameObject(String type, int x, int y, int width, int height, Color color, String imagePath){
        this.setType(type);
        Rectangle object = new Rectangle(x, y, width, height);
        this.setItem(object);
        this.setObjectColor(color);

        try{
            BufferedImage image = ImageIO.read(new File(this.getFullPath() + imagePath));
            this.setObjectImage(image);
        }catch (IOException e){
            System.out.println("Can't load image file for: " + imagePath);
            this.setObjectImage(null);
        }
    }

    String getType() {
        return this.type;
    }

    void setType(String type) {
        this.type = type;
    }

    Rectangle getItem() {
        return this.item;
    }

    void setItem(Rectangle item) {
        this.item = item;
    }

    Color getObjectColor() {
        return this.objectColor;
    }

    void setObjectColor(Color objectColor) {
        this.objectColor = objectColor;
    }

    BufferedImage getObjectImage(){
        return this.objectImage;
    }

    void setObjectImage(BufferedImage objectImage){
        this.objectImage = objectImage;
    }

    String getFullPath(){
        return this.fullPath;
    }

    Rectangle getUpBound() {
        return new Rectangle(getItem().x, getItem().y - 200, getItem().width, 200);
    }

    Rectangle getDownBound() {
        return new Rectangle(getItem().x, getItem().y + getItem().height, getItem().width, 200);
    }

    Rectangle getLeftBound() {
        return new Rectangle(getItem().x - 100, getItem().y, 100, getItem().height);
    }

    Rectangle getRightBound() {
        return new Rectangle(getItem().x + getItem().width, getItem().y, 100, getItem().height);
    }

    private Rectangle intersection(Rectangle bound, Rectangle playerBody) {
        int newX = Math.max(bound.x, playerBody.x);
        int newY = Math.max(bound.y, playerBody.y);

        int newWidth = Math.min(bound.x + bound.width, playerBody.x + playerBody.width) - newX;
        int newHeight = Math.min(bound.y + bound.height, playerBody.y + playerBody.height) - newY;

        if (newWidth <= 0 || newHeight <= 0) return null;

        return new Rectangle(newX, newY, newWidth, newHeight);
    }

    double getAreaOfIntersection(Rectangle bound, Rectangle playerBody) {
        return Objects.requireNonNull(intersection(bound, playerBody)).width * Objects.requireNonNull(intersection(bound, playerBody)).height;
    }

    public String toString() {
        return "GameObject: " + getType();
    }
}