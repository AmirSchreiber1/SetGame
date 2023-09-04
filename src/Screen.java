import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import java.io.IOException;

/**
 * an abstract class for sharing code between the two screens (the menu screen and the score screen).
 */
public abstract class Screen {
    private final GUI gui;
    private final PlayButton playButton = new PlayButton(true);
    private final ExitButton exitButton = new ExitButton(false);
    private final KeyboardSensor ks;
    private final Sleeper sleeper;
    private boolean exitGame = false;
    private boolean breakFromLoop = false;

    /**
     * the constructor method.
     * @param gui - the gui object responsible for handling the animation (taken from biu-oop jar file).
     * @param ks - keyboard sensor.
     * @param sleeper - the object responsible for pausing the image on the screen every given amount of milliseconds.
     */
    public Screen(GUI gui, KeyboardSensor ks, Sleeper sleeper) {
        this.gui = gui;
        this.ks = ks;
        this.sleeper = sleeper;
    }

    /**
     * querying if the animation loop which displays the screen should be broken from.
     * @return true if the user pressed space (whether in order to play a turn or to exit the game).
     */
    public boolean shouldBreakFromLoop() {
        return breakFromLoop;
    }

    /**
     * querying if the current game run should be terminated.
     * @return true if the user pressed space while on the exit button, false otherwise.
     */
    public boolean shouldExitGame() {
        return this.exitGame;
    }

    /**
     * getter for the play button object of the screen.
     * @return - the play button object.
     */
    public PlayButton getPlayButton() {
        return playButton;
    }

    /**
     * getter for the keyboard sensor.
     * @return - the keyboard sensor object.
     */
    public KeyboardSensor getKs() {
        return ks;
    }

    /**
     * getter for the exit button object of the screen.
     * @return - the exit button object.
     */
    public ExitButton getExitButton() {
        return exitButton;
    }

    /**
     * getter for the gui object.
     * @return - the gui object.
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * getter for the sleeper object.
     * @return - the sleeper object.
     */
    public Sleeper getSleeper() {
        return sleeper;
    }

    /**
     * method for changing the exit game flag (if the user press space while on the exit button, it turns true).
     */
    public void changeExitGameFlag() {
        this.exitGame = !this.exitGame;
    }

    /**
     * the way the screen is going to be displayed on screen.
     * an abstract method which the extending subclasses (menu-screen and score-screen) implement inside them.
     * @throws IOException - necessary for working with the highscore.txt file (which is necessary for displaying the
     * highscore on the score screen).
     */
    public abstract void display() throws IOException;

    /**
     * method for detecting up/down keys pressed by the user and responding accordingly (inside menu/score screen).
     */
    public void manageKeyBoard() {
        if (getKs().isPressed(KeyboardSensor.DOWN_KEY)) {
            if (getPlayButton().isMarked()) { //move from play to exit
                getPlayButton().changeMarkedState(); //turn off
                getExitButton().changeMarkedState(); //turn on
            }
        }
        if (getKs().isPressed(KeyboardSensor.UP_KEY)) {
            if (getExitButton().isMarked()) { //move from exit to play
                getExitButton().changeMarkedState(); //turn off
                getPlayButton().changeMarkedState(); //turn on
            }
        }
        if (getKs().isPressed(KeyboardSensor.SPACE_KEY)) {
            breakFromLoop = true;
            //if space is pressed, the loop displaying the animation of the current screen should be terminated.
            if (getExitButton().isMarked()) {
                changeExitGameFlag(); //turn to true.
            }
        }
    }
}
