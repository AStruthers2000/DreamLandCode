import java.awt.*;
import java.util.List;

public class Camera {
    Rectangle cameraBox;

    int screenX;
    int screenY;

    int xOffset;
    int yOffset;

    Camera(){
        screenX = DreamLand.game.getScreenX();
        screenY = DreamLand.game.getScreenY();

        int halfX = screenX / 2;
        int halfY = screenY / 2;

        int leftBound = halfX - 200;
        int width = 400;

        int upBound = halfY - 200;
        int height = 400;

        setCameraBox(new Rectangle(leftBound, upBound, width, height));
    }

    public Rectangle getCameraBox() {
        return cameraBox;
    }

    public void setCameraBox(Rectangle cameraBox) {
        this.cameraBox = cameraBox;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public Rectangle getLeftBound(){
        return new Rectangle(cameraBox.x - 2000, cameraBox.y, 2000, cameraBox.height);
    }

    public Rectangle getRightBound(){
        return new Rectangle(cameraBox.x + cameraBox.width, cameraBox.y, 2000, cameraBox.height);
    }

    public Rectangle getUpBound(){
        return new Rectangle(cameraBox.x, cameraBox.y - 2000, cameraBox.width, 2000);
    }

    public Rectangle getDownBound(){
        return new Rectangle(cameraBox.x, cameraBox.y + cameraBox.height, cameraBox.width, 2000);
    }

    void calcLevelLoadOffset(List<GameObject> objectList){
        Player player = DreamLand.game.getPlayer();

        System.out.println(player.getX());
        System.out.println(player.getY());

        int midX = DreamLand.game.getScreenX() / 2;
        int midY = DreamLand.game.getScreenY() / 2;

        int initXOffset = (midX - player.getX()) * DreamLand.game.getObjectMultiplier();
        int initYOffset = (midY - player.getY()) * DreamLand.game.getObjectMultiplier();

        System.out.println("X offset: " + initXOffset);
        System.out.println("Y offset: " + initYOffset);

        player.setX(player.getX() + initXOffset - player.getSizeX() * DreamLand.game.getObjectMultiplier());
        player.setY(player.getY() + initYOffset - player.getSizeY() * DreamLand.game.getObjectMultiplier());

        setxOffset(initXOffset - player.getSizeX() * DreamLand.game.getObjectMultiplier());
        setyOffset(initYOffset - player.getSizeY() * DreamLand.game.getObjectMultiplier());

        for(GameObject object : objectList){
            Rectangle item = object.getItem();
            object.setItem(new Rectangle(item.x + xOffset, item.y + yOffset, item.width, item.height));
        }
    }
}
