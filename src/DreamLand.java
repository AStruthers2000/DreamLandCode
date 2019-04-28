import java.awt.*;
import java.util.List;
import java.util.Map;

public class DreamLand {

    static DreamLand game = new DreamLand();

    private Player player;
    private FrameHandler frameHandler;
    private LevelHandler levelHandler;
    private static Map<String, List<String>> worldNames;

    private int screenX;
    private int screenY;

    protected Player getPlayer() {
        return this.player;
    }
    protected void setPlayer(Player player){
        this.player = player;
    }

    protected FrameHandler getFrameHandler() {
        return this.frameHandler;
    }

    protected LevelHandler getLevelHandler() {
        return this.levelHandler;
    }

    protected Map<String, List<String>> getWorldNames(){
        return this.worldNames;
    }

    protected int getScreenX() {
        return this.screenX;
    }

    protected int getScreenY() {
        return this.screenY;
    }

    private DreamLand() {
        System.out.println("Thank you for playing my game! - Andrew");

        screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
    } //Constructor

    private void run() {
        Thread playerThread = new Thread(Player::new);
        playerThread.start();

        levelHandler = new LevelHandler();
        worldNames = levelHandler.getWorldNames();

        Thread paintThread = new Thread(FrameHandler::new);
        paintThread.start();
    } //Run

    public static void main(String[] args) {
        game.run();
    } //Main
}
