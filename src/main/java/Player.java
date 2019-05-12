import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Player {

    private int sizeX, sizeY;
    private int x, y;
    private int xa, ya;
    private int speed;
    private int jumpSpeed;
    private int gravity;

    private int lives;
    private int health;

    private Rectangle body;
    private BufferedImage[] frames;
    private int frame;

    private int jumpRaise;
    private boolean isJumping;
    private boolean canJump;
    private int jumpHeight;

    private boolean onPlatform;
    private boolean onLadder;
    private boolean hittingLeftSide;
    private boolean hittingRightSide;

    private int coins;
    private int coinsOnLevel;

    private int worldNum;
    private int levelNum;

    Player() {
        DreamLand.game.setPlayer(this);
        this.init();
    }

    private void init() {
        this.sizeX = 50;
        this.sizeY = 100;

        this.x = 0;
        this.y = 725;

        this.xa = 0;
        this.ya = 0;
        this.speed = 5;
        this.jumpSpeed = 9;
        this.gravity = 2;

        this.lives = 5;
        this.health = 4;

        this.body = new Rectangle(this.x, this.y, this.sizeX, this.sizeY);

        //TODO get artwork and frames set up
        this.frames = getFrames();
        this.frame = 2;

        this.jumpRaise = 0;
        this.isJumping = false;
        this.canJump = true;
        this.jumpHeight = 200;

        this.onPlatform = false;
        this.onLadder = false;
        this.hittingLeftSide = false;
        this.hittingRightSide = false;

        this.coins = 0;
        this.coinsOnLevel = 0;

        //**************************************************************************************************************
        this.worldNum = -1;
        this.levelNum = 0;
        //**************************************************************************************************************
    }

    private BufferedImage[] getFrames() {
        String longPath = System.getProperty("user.dir") + "\\Sprites\\PlayerSprites\\";
        File folder = new File(longPath);
        File[] allFiles = folder.listFiles();

        assert allFiles != null;
        BufferedImage[] images = new BufferedImage[allFiles.length];

        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].isFile()) {
                try {
                    images[i] = ImageIO.read(allFiles[i]);
                } catch (IOException e) {
                    System.out.println("Cant read image file");
                }
            }
        }
        return images;
    }

    BufferedImage getCurrentFrame() {
        return frames[frame];
    }

    void setXa(int xa) {
        this.xa = xa;
    }

    void setYa(int ya) {
        this.ya = ya;
    }

    int getSpeed() {
        return speed;
    }

    public void setHittingLeftSide(boolean hittingLeftSide) {
        this.hittingLeftSide = hittingLeftSide;
    }

    public void setHittingRightSide(boolean hittingRightSide) {
        this.hittingRightSide = hittingRightSide;
    }

    boolean getOnLadder() {
        return this.onLadder;
    }

    protected Rectangle getBody() {
        return this.body;
    }

    void setBody(Rectangle body) {
        this.body = body;
    }

    void setJumping(boolean jumping) {
        this.isJumping = jumping;
    }

    int getWorldNum(){
        return this.worldNum;
    }

    int getLevelNum(){
        return this.levelNum;
    }

    void acceptMovement() {
        setBody(new Rectangle(this.x, this.y, this.sizeX, this.sizeY));
    }

    void move(Level currentLevel, List<GameObject> gameObjectList) {
        if (!hittingLeftSide && !hittingRightSide) {
            this.x += this.xa;
        }

        if (this.isJumping && canJump) {
            this.y -= jumpSpeed;
            jumpRaise += jumpSpeed;
            if (jumpRaise >= jumpHeight) {
                this.isJumping = false;
                this.canJump = false;
                jumpRaise = 0;
            }
        } else {
            if (!onPlatform && !onLadder) {
                this.isJumping = false;
                this.canJump = false;
                this.y += this.gravity;
                this.gravity += 1;
            }
        }

        boolean currentlyOnPlatform = false;
        boolean currentlyOnLadder = false;

        Rectangle nextBody = new Rectangle(getBody().x + xa, getBody().y + gravity + ya, getBody().width, getBody().height);

        for (GameObject object : gameObjectList) {
            if (object.getType().equalsIgnoreCase("platform")) {
                if (nextBody.intersects(object.getItem())) {
                    //canJump = true;
                    currentlyOnPlatform = true;
                }

                if (getBody().intersects(object.getItem())) {
                    double upInter = 0.0;
                    double downInter = 0.0;
                    double leftInter = 0.0;
                    double rightInter = 0.0;

                    if (getBody().intersects(object.getUpBound())) {
                        upInter = object.getAreaOfIntersection(object.getUpBound(), getBody());
                    }
                    if (getBody().intersects(object.getDownBound())) {
                        downInter = object.getAreaOfIntersection(object.getDownBound(), getBody());
                    }
                    if (getBody().intersects(object.getLeftBound())) {
                        leftInter = object.getAreaOfIntersection(object.getLeftBound(), getBody());
                    }
                    if (getBody().intersects(object.getRightBound())) {
                        rightInter = object.getAreaOfIntersection(object.getRightBound(), getBody());
                    }

                    double maxVert = Math.max(upInter, downInter);
                    double maxHoriz = Math.max(leftInter, rightInter);

                    double maxVal = Math.max(maxHoriz, maxVert);

                    int up = (int) upInter;
                    int down = (int) downInter;
                    int left = (int) leftInter;
                    int right = (int) rightInter;
                    int max = (int) maxVal;

                    if (max == up) {
                        this.y = object.getItem().y - sizeY;
                        this.onPlatform = true;
                        this.canJump = true;
                        this.gravity = 2;
                    } else if (max == down) {
                        this.y = object.getItem().y + object.getItem().height;
                        isJumping = false;
                        canJump = false;
                        this.gravity = 2;
                    } else if (max == right) {
                        this.x = object.getItem().x + object.getItem().width;
                        this.hittingLeftSide = false;
                        this.hittingRightSide = true;
                        this.onPlatform = false;
                    } else if (max == left) {
                        this.x = object.getItem().x - this.sizeX;
                        this.hittingLeftSide = true;
                        this.hittingRightSide = false;
                        this.onPlatform = false;
                    } else {
                        System.out.println("What the fuck");
                    }
                }
            } //platform

            if (object.getType().equalsIgnoreCase("ladder")) {
                if (getBody().intersects(object.getItem())) {
                    currentlyOnLadder = true;
                    this.y += this.ya;
                    this.gravity = 2;
                    this.onLadder = true;
                    this.canJump = true;
                }
            } //ladder

            if (object.getType().equalsIgnoreCase("coin")) {
                if (getBody().intersects(object.getItem())) {
                    this.coinsOnLevel += 1;
                    currentLevel.objectDelete(object);
                }
            } //coin

            if(object.getType().equalsIgnoreCase("door")){
                if(getBody().intersects(object.getItem())){
                    this.coins += this.coinsOnLevel;
                    this.coinsOnLevel = 0;
                    this.levelNum += 1;

                    StringBuilder worldName = new StringBuilder("World");
                    if(this.worldNum >= 0){
                        worldName.append(this.worldNum);
                    }else{
                        worldName.append("Tutorial");
                    }
                    System.out.println(worldName.toString());

                    System.out.println(DreamLand.game.getWorldNames());
                    if(this.levelNum > DreamLand.game.getWorldNames().get(worldName.toString()).size()){
                        System.out.println("Moving to next level!");
                        this.worldNum += 1;
                        this.levelNum = 0;
                        //TODO in between world screen change
                    }
                    DreamLand.game.loadLevel();
                }
            }
        }

        if (!currentlyOnPlatform) {
            onPlatform = false;
        }
        if (!currentlyOnLadder) {
            onLadder = false;
        }
        acceptMovement();
    }
}
