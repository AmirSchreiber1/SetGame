import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.io.IOException;

/**
 * the class for the main method (runs the menu screen, the game itself, and the score screen).
 */
public class SetMain {

    /**
     * the main method.
     *
     * @param args - keyboard arguments.
     * @throws IOException - necessary for working with the highscore.txt file.
     */
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI("Set", 600, 640);
        Sleeper sleeper = new Sleeper();
        KeyboardSensor ks = gui.getKeyboardSensor();
        //create and display the menu screen:
        MenuScreen menuScreen = new MenuScreen(gui, ks, sleeper);
        menuScreen.display();
        if (menuScreen.shouldExitGame()) {
            gui.close();
            return;
        }
        while (true) {
            SetGame newGame = new SetGame(gui, sleeper, ks);
            newGame.run();
            ScoreScreen scoreScreen = new ScoreScreen(gui, ks, sleeper, newGame.getScore());
            scoreScreen.display();
            if (scoreScreen.shouldExitGame()) {
                break;
            }
        }
        gui.close();
    }
}