import java.awt.*;

public class DreamLand {

    static DreamLand game = new DreamLand();

    private Player player;
    private FrameHandler frameHandler;
    private LevelHandler levelHandler;

    private int screenX;
    private int screenY;

    protected Player getPlayer(){
        return this.player;
    }

    protected FrameHandler getFrameHandler(){
        return this.frameHandler;
    }

    protected LevelHandler getLevelHandler(){
        return this.levelHandler;
    }

    protected int getScreenX(){
        return this.screenX;
    }

    protected int getScreenY(){
        return this.screenY;
    }

    private DreamLand(){
        System.out.println("Thank you for playing my game! - Andrew");

        screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
    } //Constructor

    private void run(){
        player = new Player();
        frameHandler = new FrameHandler();
        levelHandler = new LevelHandler();
    } //Run

    public static void main(String[] args){
        game.run();
    } //Main
}
