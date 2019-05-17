import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Level {
    private Player player;
    private List<GameObject> gameObjectList;

    Level(List<GameObject> gameObjects) {
        this.player = DreamLand.game.getPlayer();
        this.gameObjectList = gameObjects;
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public void setGameObjectList(List<GameObject> gameObjectList) {
        this.gameObjectList = gameObjectList;
    }

    void drawLevel(Graphics2D graphics) {
        int multiplier = DreamLand.game.getObjectMultiplier();

        int xOffset = DreamLand.game.getCamera().getxOffset();
        int yOffset = -1 * DreamLand.game.getPlayer().getYa();
        //System.out.println(xOffset);
        //System.out.println(yOffset);

        player = DreamLand.game.getPlayer();

        for (GameObject object : gameObjectList) {
            if (object.getObjectImage() == null) {
                graphics.setColor(object.getObjectColor());
                graphics.fillRect(object.getItem().x * multiplier, (object.getItem().y + yOffset) * multiplier, object.getItem().width * multiplier, object.getItem().height * multiplier);

            } else {
                graphics.drawImage(object.getObjectImage(), object.getItem().x * multiplier, (object.getItem().y + yOffset) * multiplier, object.getItem().width * multiplier, object.getItem().height * multiplier, null);
            }

            //System.out.println("Object: " + object.getType() + " X: " + String.valueOf(object.getItem().x * multiplier) + ", Y: " + String.valueOf(object.getItem().y * multiplier));
            Rectangle newObjectItem = new Rectangle(object.getItem().x * multiplier, (object.getItem().y + yOffset) * multiplier, object.getItem().width * multiplier, object.getItem().height * multiplier);
            object.setItem(newObjectItem);

            /*if(object.getType().equalsIgnoreCase("platform")){
                graphics.setColor(Color.RED);
                graphics.drawRect(object.getUpBound().x, object.getUpBound().y, object.getUpBound().width, object.getUpBound().height);
                graphics.drawRect(object.getDownBound().x, object.getDownBound().y, object.getDownBound().width, object.getDownBound().height);
                graphics.drawRect(object.getLeftBound().x, object.getLeftBound().y, object.getLeftBound().width, object.getLeftBound().height);
                graphics.drawRect(object.getRightBound().x, object.getRightBound().y, object.getRightBound().width, object.getRightBound().height);
            }*/

            graphics.setColor(Color.WHITE);
            Rectangle playerCam = DreamLand.game.getCamera().cameraBox;
            graphics.drawRect(playerCam.x, playerCam.y, playerCam.width, playerCam.height);
        }

        player.move(this, getGameObjectList());
        graphics.setColor(Color.WHITE);
        graphics.fillRect(player.getBody().x, player.getBody().y, player.getBody().width, player.getBody().height);
        //graphics.drawImage(player.getCurrentFrame(), player.getBody().x * multiplier, player.getBody().y * multiplier, player.getBody().width * multiplier, player.getBody().height * multiplier, null);

        gameObjectList.removeAll(toDelete);

        player.drawPlayerUI(this, graphics);
    }

    void moveObjects(int x, int y) {
        for (GameObject object : gameObjectList) {
            Rectangle item = object.getItem();
            object.setItem(new Rectangle(item.x + x, item.y + y, item.width, item.height));
        }
    }

    private List<GameObject> toDelete = new ArrayList<>();

    void objectDelete(GameObject object) {
        while (true) {
            try {
                toDelete.add(object);
                break;
            } catch (ConcurrentModificationException e) {
                System.out.println("Concurrent in Level.java");
            }
        }
    }

    int countAllItemsOnLevel(){
        int items = 0;
        for(GameObject object : gameObjectList){
            String type = object.getType();
            switch (type){
                case "coin":
                    items++;
                    break;
                case "heart":
                    items++;
                    break;
                default:
                    break;
            }
        }
        return items;
    }
}
