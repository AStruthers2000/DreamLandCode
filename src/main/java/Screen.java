import java.awt.*;
import java.util.HashMap;

public class Screen {

    private Player player;
    private Graphics2D graphics;
    private String whatScreen;
    private LevelHandler levelHandler;
    private Level curLevel;

    private int screenX;
    private int screenY;

    Screen() {
        levelHandler = DreamLand.game.getLevelHandler();
        screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    Player getPlayer() {
        return this.player;
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    Graphics2D getGraphics() {
        return this.graphics;
    }

    void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }

    void setWhatScreen(String whatScreen) {
        this.whatScreen = whatScreen;
    }

    void setCurLevel(Level curLevel) {
        this.curLevel = curLevel;
    }

    void drawScreen() {
        switch (whatScreen) {
            case "game":
                drawGame();
                break;
            case "pause":
                drawPause();
                break;
            case "home":
                drawHome();
                break;
            case "world":
                drawWorld();
                break;
            case "upgrade":
                drawUpgrade();
                break;
        }
    }

    void drawGame() {
        if (curLevel == null) {
            System.out.println("level is null you hoe");

            int worldNum = player.getWorldNum();
            int levelNum = player.getLevelNum();

            String worldName;
            if (worldNum == -1) {
                worldName = "worldTutorial";
            } else {
                worldName = "world" + worldNum;
            }
            String levelName = "level" + levelNum;

            setCurLevel(new Level(DreamLand.game.getLevelHandler().generateLevel(worldName, levelName)));
        }
        Graphics2D graphics = getGraphics();
        curLevel.drawLevel(graphics);

    }


    void drawPause() {

    }

    private HashMap<String, Rectangle> homeScreenBoxes = new HashMap<>();
    private String currentlyOn;

    HashMap<String, Rectangle> getHomeScreenBoxes(){
        return this.homeScreenBoxes;
    }

    void setHomeScreenBoxes(HashMap<String, Rectangle> homeScreenBoxes){
        this.homeScreenBoxes = homeScreenBoxes;
    }

    void setCurrentlyOn(String box){
        this.currentlyOn = box;
    }


    void drawHome() {
        HashMap<String, Rectangle> boxes = new HashMap<>();

        int halfScreenX = screenX / 2;
        int halfScreenY = screenY / 2;

        Graphics2D graphics = getGraphics();

        String[] names = {"New Game", "Start", "Settings", "Controls", "Worlds", "Store", "Credits", "Exit"};

        //graphics.setColor(Color.red);
        graphics.drawLine(halfScreenX,0,halfScreenX,screenY);
        graphics.drawLine(0,halfScreenY,screenX,halfScreenY);
        graphics.setFont(new Font("verdana", Font.BOLD, 40));

        for(int a = 0; a < names.length/2; a++){
            for(int b = 0; b <= 1; b++) {
                int squareX = halfScreenX - 350 + 400*b;
                int squareY = halfScreenY - 175 + 100 * a;

                String stringToDraw = names[a+b*4];
                int stringLengthInPixels = graphics.getFontMetrics().stringWidth(names[a+4*b]);
                int centeredString = 300/2 - stringLengthInPixels/2;

                if(stringToDraw.equalsIgnoreCase(this.currentlyOn)){
                    graphics.setColor(Color.LIGHT_GRAY);
                }else{
                    graphics.setColor(Color.WHITE);
                }
                graphics.fillRoundRect(squareX, squareY, 300, 50, 20,20);

                graphics.setColor(Color.BLUE);
                graphics.drawString(stringToDraw, squareX + centeredString, squareY + 40);

                boxes.put(stringToDraw, new Rectangle(squareX, squareY, 300, 50));
            }
        }
        this.setHomeScreenBoxes(boxes);
    }


    void drawWorld() {

    }

    void drawUpgrade() {

    }
}



