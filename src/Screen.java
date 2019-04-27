import java.awt.*;

public class Screen {

    private Player player;
    private Graphics2D graphics;

    Player getPlayer(){
        return this.player;
    }

    void setPlayer(Player player){
        this.player = player;
    }

    Graphics2D getGraphics(){
        return this.graphics;
    }

    void setGraphics(Graphics2D graphics){
        this.graphics = graphics;
    }

}

class GameScreen extends Screen {
    void drawScreen(Level curLevel){
        Graphics2D graphics = super.getGraphics();
        curLevel.drawLevel(graphics);


    }
}

class HomeScreen extends Screen {
    void drawScreen(){

    }
}

class PauseScreen extends Screen {
    void drawScreen(){

    }
}

class WorldScreen extends Screen {
    void drawScreen(){

    }
}

class UpgradeScreen extends Screen {
    void drawScreen(){

    }
}


