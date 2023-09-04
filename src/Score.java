import biuoop.DrawSurface;

/**
 * class for the score object (displayed on screen while playing).
 */
public class Score implements Sprite {
    private int score = 0;

    /**
     * increase score by 1 if a set is found.
     */
    public void add() {
        this.score += 1;
    }

    /**
     * getter for the score.
     *
     * @return - an integer representing the score.
     */
    public int getScore() {
        return score;
    }

    @Override
    public void drawSelf(DrawSurface d) {
        d.drawText(80, 140, "Score: " + this.score, 20);
    }
}
