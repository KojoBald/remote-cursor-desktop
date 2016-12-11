import java.awt.*;

public class CursorHandler {
    private Robot robot;

    public CursorHandler() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Could not initialize cursor handler");
            e.printStackTrace();
        }
    }

    public void move(int xMod, int yMod) {
        Point currentLocation = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove(currentLocation.x + xMod, currentLocation.y + yMod);
    }
}
