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

class Ladder extends GameObject {
    private int x, y, width, height;

    Ladder(int x, int y, int width, int height, String imagePath) {
        super.setType("ladder");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;

        Rectangle ladder = new Rectangle(this.x, this.y, this.width, this.height);
        super.setItem(ladder);
        super.setObjectColor(Color.BLUE);

        try{
            BufferedImage image = ImageIO.read(new File(super.getFullPath() + imagePath));
            super.setObjectImage(image);
        }catch (IOException e){
            System.out.println("Can't load file: " + imagePath);
            super.setObjectImage(null);
        }
    }

}

class Platform extends GameObject {
    int x, y, width, height;

    Platform(int x, int y, int width, int height, String imagePath) {
        super.setType("platform");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Rectangle platform = new Rectangle(this.x, this.y, this.width, this.height);
        super.setItem(platform);
        super.setObjectColor(Color.ORANGE);

        try{
            BufferedImage image = ImageIO.read(new File(super.getFullPath() + imagePath));
            super.setObjectImage(image);
        }catch (IOException e){
            System.out.println("Can't load file: " + imagePath);
            super.setObjectImage(null);
        }
    }
}

class Floor extends GameObject{
    int x, y, width, height;

    Floor(int x, int y, int width, int height, String imagePath) {
        super.setType("platform");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Rectangle platform = new Rectangle(this.x, this.y, this.width, this.height);
        super.setItem(platform);
        super.setObjectColor(Color.GRAY);

        try{
            BufferedImage image = ImageIO.read(new File(super.getFullPath() + imagePath));
            super.setObjectImage(image);
        }catch (IOException e){
            System.out.println("Can't load file: " + imagePath);
            super.setObjectImage(null);
        }
    }
}

class Door extends GameObject {
    int x, y, width, height;

    Door(int x, int y, int width, int height, String imagePath) {
        super.setType("door");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        Rectangle door = new Rectangle(this.x, this.y, this.width, this.height);
        super.setItem(door);
        super.setObjectColor(Color.CYAN);

        try{
            BufferedImage image = ImageIO.read(new File(super.getFullPath() + imagePath));
            super.setObjectImage(image);
        }catch (IOException e){
            System.out.println("Can't load file: " + imagePath);
            super.setObjectImage(null);
        }
    }
}

class Coin extends GameObject {
    int x, y, width, height;

    Coin(int x, int y, int width, int height, String imagePath) {
        super.setType("coin");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        Rectangle coin = new Rectangle(this.x, this.y, this.width, this.height);
        super.setItem(coin);
        super.setObjectColor(Color.YELLOW);

        try{
            BufferedImage image = ImageIO.read(new File(super.getFullPath() + imagePath));
            super.setObjectImage(image);
        }catch (IOException e){
            System.out.println("Can't load file: " + imagePath);
            super.setObjectImage(null);
        }
    }
}
