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
        player = DreamLand.game.getPlayer();

        for (GameObject object : gameObjectList) {
            graphics.setColor(object.getObjectColor());
            graphics.fillRect(object.getItem().x, object.getItem().y, object.getItem().width, object.getItem().height);

            /*if(object.getType().equalsIgnoreCase("platform")){
                graphics.setColor(Color.RED);
                graphics.drawRect(object.getUpBound().x, object.getUpBound().y, object.getUpBound().width, object.getUpBound().height);
                graphics.drawRect(object.getDownBound().x, object.getDownBound().y, object.getDownBound().width, object.getDownBound().height);
                graphics.drawRect(object.getLeftBound().x, object.getLeftBound().y, object.getLeftBound().width, object.getLeftBound().height);
                graphics.drawRect(object.getRightBound().x, object.getRightBound().y, object.getRightBound().width, object.getRightBound().height);
            }*/
        }

        player.move(this, getGameObjectList());
        graphics.setColor(Color.WHITE);
        //graphics.fillRect(player.getBody().x, player.getBody().y, player.getBody().width, player.getBody().height);
        graphics.drawImage(player.getCurrentFrame(), player.getBody().x, player.getBody().y, player.getBody().width, player.getBody().height, null);

        gameObjectList.removeAll(toDelete);
    }

    private List<GameObject> toDelete = new ArrayList<>();

    void objectDelete(GameObject object) {
        while (true) {
            try {
                toDelete.add(object);
                break;
            } catch (ConcurrentModificationException e) {
                System.out.println("Concurrent");
            }
        }
    }
}
