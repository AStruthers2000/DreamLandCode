import com.studiohartman.jamepad.ControllerState;

import java.awt.event.KeyEvent;

public class ControllerInputHandler {
    private static ControllerState controllerState;

    ControllerInputHandler() {
        this.handleInput();
    }

    static void setControllerState(ControllerState state) {
        controllerState = state;
    }

    private void handleInput() {

        while (true) {
            try {
                ControllerState curState = controllerState;

                if (curState.leftStickY <= -0.5) {
                    if (DreamLand.game.getPlayer().getOnLadder()) {
                        DreamLand.game.getPlayer().setYa(DreamLand.game.getPlayer().getSpeed());
                    }
                }
                if (curState.leftStickY >= 0.5) {
                    if (DreamLand.game.getPlayer().getOnLadder()) {
                        DreamLand.game.getPlayer().setYa(-DreamLand.game.getPlayer().getSpeed());
                    }
                }
                if (curState.leftStickX <= -0.5) {
                    DreamLand.game.getPlayer().setXa(-DreamLand.game.getPlayer().getSpeed());
                    DreamLand.game.getPlayer().setHittingLeftSide(false);
                }
                if (curState.leftStickX >= 0.5) {
                    DreamLand.game.getPlayer().setXa(DreamLand.game.getPlayer().getSpeed());
                    DreamLand.game.getPlayer().setHittingRightSide(false);
                }
                if (curState.a) {
                    DreamLand.game.getPlayer().setJumping(true);
                }

                if(curState.leftStickX > -0.25 && curState.leftStickX < 0.25){
                    DreamLand.game.getPlayer().setHittingRightSide(false);
                    DreamLand.game.getPlayer().setHittingLeftSide(false);
                    DreamLand.game.getPlayer().setXa(0);
                }
                if(curState.leftStickY > -0.25 && curState.leftStickY < 0.25){
                    DreamLand.game.getPlayer().setHittingRightSide(false);
                    DreamLand.game.getPlayer().setHittingLeftSide(false);
                    DreamLand.game.getPlayer().setYa(0);
                }

                if (!curState.a) {
                    DreamLand.game.getPlayer().setJumping(false);
                }
                Thread.sleep(10);
            } catch (NullPointerException e) {
                System.out.println("No state for controller set");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}