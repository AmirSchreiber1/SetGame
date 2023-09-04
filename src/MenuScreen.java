import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * class for the menu screen, which is displayed before the first turn is taken.
 */
public class MenuScreen extends Screen {

    /**
     * constructor (simply calls super).
     * @param gui - the gui object responsible for handling the animation (used via biu-oop jar file).
     * @param ks - the keyboard sensor.
     * @param sleeper - the object responsible to pause the animation every given amount of milliseconds.
     */
    public MenuScreen(GUI gui, KeyboardSensor ks, Sleeper sleeper) {
        super(gui, ks, sleeper);
    }

    @Override
    public void display() {
        while (true) {
            manageKeyBoard(); //detects if user pressed up or down on his keyboard and react accordingly.
            // the below condition is true if user pressed space, whether it was on the play button or the exit one.
            // anyway, the menu screen animation should stop.
            if (shouldBreakFromLoop()) {
                break;
            }
            DrawSurface d = getGui().getDrawSurface();
            d.setColor(Color.BLACK);
            d.drawText(120, 200, "Welcome to Set!", 50);
            getPlayButton().drawSelf(d); // draws the play button.
            getExitButton().drawSelf(d); // draws the exit button.
            getGui().show(d);
            getSleeper().sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
