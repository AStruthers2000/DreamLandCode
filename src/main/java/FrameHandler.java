import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class FrameHandler extends JPanel {

    private JFrame frame;

    private boolean isScreenChanged;
    private HashMap<String, Screen> screenList;
    private Screen currentScreen;
    private String whatScreen;

    private Level curLevel;
    private String worldName = "world0";
    private String levelName = "level0";
    private ControllerInputHandler controllerInputHandler;
    private ControllerState curState;

    protected JFrame getFrame() {
        return this.frame;
    } //getFrame

    private void getScreens() {
        screenList.put("pause", new PauseScreen());
        screenList.put("home", new HomeScreen());
        screenList.put("game", new GameScreen());
        screenList.put("upgrade", new UpgradeScreen());
        screenList.put("world", new WorldScreen());
    }

    private Screen whereToGo() {
        if (whatScreen.equalsIgnoreCase("game")) {
            curLevel = new Level(new LevelHandler().generateLevel(worldName, levelName));
        }
        return screenList.get(whatScreen);
    } //whereToGo

    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.BLACK);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);

        if (isScreenChanged) {
            isScreenChanged = false;
            currentScreen = whereToGo();
        }
        currentScreen.setGraphics(graphics);
        currentScreen.setPlayer(DreamLand.game.getPlayer());

        switch (whatScreen) {
            case "game":
                ((GameScreen) currentScreen).drawScreen(curLevel);
                break;
            case "home":
                ((HomeScreen) currentScreen).drawScreen();
                break;
            case "pause":
                ((PauseScreen) currentScreen).drawScreen();
                break;
            case "upgrade":
                ((UpgradeScreen) currentScreen).drawScreen();
                break;
            case "world":
                ((WorldScreen) currentScreen).drawScreen();
                break;
            default:
                break;
        }

    } //Paint

    FrameHandler() {
        screenList = new HashMap<>();
        getScreens();
        whatScreen = "game";
        isScreenChanged = true;

        Thread controllerInputHandlerThread = new Thread(ControllerInputHandler::new);

        frame = new JFrame("DreamLand");
        frame.add(this);
        frame.setSize(DreamLand.game.getScreenX(), DreamLand.game.getScreenY());
        //frame.setSize(800,800);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        ControllerManager controller = new ControllerManager();
        controller.initSDLGamepad();

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN || curState.leftStickY <= -0.5) {
                    if (DreamLand.game.getPlayer().getOnLadder()) {
                        DreamLand.game.getPlayer().setYa(DreamLand.game.getPlayer().getSpeed());
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || curState.leftStickY >= 0.5) {
                    if (DreamLand.game.getPlayer().getOnLadder()) {
                        DreamLand.game.getPlayer().setYa(-DreamLand.game.getPlayer().getSpeed());
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || curState.leftStickX <= -0.5) {
                    DreamLand.game.getPlayer().setXa(-DreamLand.game.getPlayer().getSpeed());
                    DreamLand.game.getPlayer().setHittingLeftSide(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || curState.leftStickX >= 0.5) {
                    DreamLand.game.getPlayer().setXa(DreamLand.game.getPlayer().getSpeed());
                    DreamLand.game.getPlayer().setHittingRightSide(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE || curState.a) {
                    DreamLand.game.getPlayer().setJumping(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                DreamLand.game.getPlayer().setHittingRightSide(false);
                DreamLand.game.getPlayer().setHittingLeftSide(false);
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    DreamLand.game.getPlayer().setYa(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    DreamLand.game.getPlayer().setYa(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    DreamLand.game.getPlayer().setXa(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    DreamLand.game.getPlayer().setXa(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    DreamLand.game.getPlayer().setJumping(false);
                }
            }
        });

        frame.setVisible(true);

        controllerInputHandlerThread.start();

        while (true) {
            curState = controller.getState(0);
            if(curState.isConnected){
                ControllerInputHandler.setControllerState(curState);
            }
            //System.out.println(Thread.activeCount());
            //controllerInputHandlerThread.setControllerState(curState);
            //System.out.println(curState.isConnected);
            //System.out.println(curState.y);
            //System.out.println(curState.x);
            //System.out.println(curState.a);
            //System.out.println(curState.b);
            //System.out.println(curState.leftStickMagnitude);
            //System.out.println(curState.leftStickAngle);

            frame.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("This has never happened");
                System.exit(1);
            }
        }
    } //Constructor
}
