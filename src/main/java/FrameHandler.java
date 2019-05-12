import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class FrameHandler extends JPanel {

    private JFrame frame;

    //private boolean isScreenChanged;
    private Screen mainscreen;
    private String whatScreen;

    private boolean click;

    private ControllerState curState;

    protected JFrame getFrame() {
        return this.frame;
    } //getFrame

    void setWhatScreen(String screen){
        this.whatScreen = screen;
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.BLACK);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //this.graphics = graphics;
        graphics.setColor(Color.WHITE);

        /*if (isScreenChanged) {
            isScreenChanged = false;
            loadLevel();
        }*/

        mainscreen.setGraphics(graphics);
        mainscreen.setPlayer(DreamLand.game.getPlayer());
        //mainscreen.setCurLevel(curLevel);
        mainscreen.setWhatScreen(whatScreen);

        mainscreen.drawScreen();

    } //Paint

    private void mouseState(){
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;

        Point mouse = new Point(mouseX, mouseY);

        boolean onButton = false;
        String lastButton = "";

        HashMap<String, Rectangle> boxes = mainscreen.getHomeScreenBoxes();
        for(String homeScreenButton : boxes.keySet()){
            Rectangle box = boxes.get(homeScreenButton);

            if(box.contains(mouse)){
                onButton = true;
                lastButton = homeScreenButton;

                if(click){
                    System.out.println(homeScreenButton );
                    click = false;

                    switch (homeScreenButton){
                        case "New Game":
                            System.out.println("Creating new game!!!");
                            break;
                        case "Start":
                            System.out.println("Starting game!");
                            whatScreen = "game";
                            break;
                        case "Worlds":
                            System.out.println("Going to worlds screen!");
                            whatScreen = "world";
                            break;
                        case "Store":
                            System.out.println("Going to upgrade screen!");
                            whatScreen = "upgrade";
                            break;
                        case "Settings":
                            System.out.println("Displaying settings");
                            whatScreen = "settings";
                            break;
                        case "Credits":
                            System.out.println("Displaying credits");
                            whatScreen = "credits";
                            break;
                        case "Controls":
                            System.out.println("Displaying controls");
                            whatScreen = "controls";
                            break;
                        case "Exit":
                            System.out.println("Exiting");
                            int quit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                            if(quit == 0){
                                System.exit(0);
                            }
                            break;
                        default:
                            System.out.println("Uhhhhh");
                            break;
                    }
                }
            }
        }

        if(onButton){
            mainscreen.setCurrentlyOn(lastButton);
        }else{
            mainscreen.setCurrentlyOn("");
        }


        //System.out.println(mouse);
    }

    //******************************************************************************************************************
    FrameHandler() { //CONSTRUCTOR
        mainscreen = new Screen();
        //getScreens();
        //**************************************************************************************************************
        whatScreen = "game"; //change to game for testing game
        //**************************************************************************************************************
        //isScreenChanged = true;

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
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (DreamLand.game.getPlayer().getOnLadder()) {
                        DreamLand.game.getPlayer().setYa(DreamLand.game.getPlayer().getSpeed());
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (DreamLand.game.getPlayer().getOnLadder()) {
                        DreamLand.game.getPlayer().setYa(-DreamLand.game.getPlayer().getSpeed());
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    DreamLand.game.getPlayer().setXa(-DreamLand.game.getPlayer().getSpeed());
                    DreamLand.game.getPlayer().setHittingLeftSide(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    DreamLand.game.getPlayer().setXa(DreamLand.game.getPlayer().getSpeed());
                    DreamLand.game.getPlayer().setHittingRightSide(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    DreamLand.game.getPlayer().setJumping(true);
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    if(whatScreen.equalsIgnoreCase("game")){
                        setWhatScreen("pause");
                    }else if(whatScreen.equalsIgnoreCase("pause")){
                        setWhatScreen("game");
                    }
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
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.setVisible(true);

        if(controller.getState(0).isConnected) {
            controllerInputHandlerThread.start();
            System.out.println("Controller Connected");
        }

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
            mouseState();
            try {
                Thread.sleep(10);

            } catch (InterruptedException e) {
                System.out.println("This has never happened");
                System.exit(1);
            }
        }
    } //Constructor
}
