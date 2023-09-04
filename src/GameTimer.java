import biuoop.DrawSurface;

/**
 * class for the timer (counting down the time left in each turn).
 */
public class GameTimer implements Sprite {
    private final long playTimeInSec;
    private final long startTime;
    private long minutesDisplay;
    private long secondsDisplay;

    /**
     * constructor 1#, receives not only the current time, but also how many seconds a turn should take.
     *
     * @param playTimeInSec - how many seconds a turn should take.
     * @param startTime     - the current time.
     */
    public GameTimer(long playTimeInSec, long startTime) {
        this.playTimeInSec = playTimeInSec;
        this.startTime = startTime;
    }

    /**
     * constructor 2#, which receives only the current time. Each turn takes by default 60 seconds.
     *
     * @param startTime - the current time.
     */
    public GameTimer(long startTime) {
        this.playTimeInSec = 60;
        this.startTime = startTime;
    }

    /**
     * this method activate the timer, including computing the seconds left and displaying it on screen.
     *
     * @param d - the surface for the gui representation.
     * @return - true if there is still time left, false otherwise.
     */
    public boolean run(DrawSurface d) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long remainingSeconds = playTimeInSec - elapsedTime / 1000;
        this.minutesDisplay = remainingSeconds / 60;
        this.secondsDisplay = remainingSeconds % 60;
        if (minutesDisplay == 0 & secondsDisplay <= 0) {
            return false;
        }
        drawSelf(d);
        return true;
    }

    @Override
    public void drawSelf(DrawSurface d) {
        if (secondsDisplay >= 10) {
            d.drawText(420, 140, "Time: " + minutesDisplay + ":" + secondsDisplay, 20);
        } else {
            d.drawText(420, 140, "Time: " + minutesDisplay + ":0" + secondsDisplay, 20);
        }
    }
}
