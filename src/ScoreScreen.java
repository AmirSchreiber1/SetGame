import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * class for the score screen displayed after each turn.
 */
public class ScoreScreen extends Screen {
    private final int score;

    /**
     * constructor method.
     * @param gui - the gui object responsible for handling the animation (taken from biu-oop jar file).
     * @param ks - keyboard sensor.
     * @param sleeper - the object responsible for pausing the image on the screen every given amount of milliseconds.
     * @param score - the score of the turn that just ended.
     */
    public ScoreScreen(GUI gui, KeyboardSensor ks, Sleeper sleeper, int score) {
        super(gui, ks, sleeper);
        this.score = score;
        getPlayButton().turnRetryOn();
    }

    /**
     * this is the method responsible for getting the highest score so far (and updating it if needed).
     * this is done by reading from and writing to a txt file named "highscore.txt".
     * @return - the highest score so far (including the score of the turn that just ended).
     */
    private int getHighScore() throws IOException {
        Path path = Path.of("highscore.txt");
        String s = Files.readString(path); //read the file (get the text as a string).
        int currHighScore = Integer.parseInt(s); //convert the string to an integer.
        if (this.score > currHighScore) { //update the highest score if needed.
            Files.writeString(path, Integer.toString(this.score));
            return this.score; //if here, the highest score is the score of the last turn.
        }
        return currHighScore; //if here, the highest score is the same score that was written in the doc.
    }

    @Override
    public void display() throws IOException {
        while (true) {
            manageKeyBoard(); //detects if user pressed up or down on his keyboard and react accordingly.
            // the below condition is true if user pressed space, whether it was on the play button or the exit one.
            // anyway, the menu screen animation should stop.
            if (shouldBreakFromLoop()) {
                break;
            }
            DrawSurface d = getGui().getDrawSurface();
            d.setColor(Color.BLACK);
            d.drawText(165, 200, this.score + " sets found!", 50);
            d.drawText(250, 250, "HighScore: " + getHighScore(), 20);
            getPlayButton().drawSelf(d);
            getExitButton().drawSelf(d);
            getGui().show(d);
            getSleeper().sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
