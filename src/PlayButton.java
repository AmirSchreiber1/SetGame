import biuoop.DrawSurface;
import java.awt.Color;

/**
 * the class for the play button.
 * it appears on both the menu screen (green button with "play" written on it),
 * and on the score screen (green button with "retry" written on it).
 */
public class PlayButton extends MarkAble implements Sprite {
    private final int x = 210;
    private final int y = 300;
    private final int width = 200;
    private final int height = 52;
    private boolean retryFlag = false;

    /**
     * constructor (calls super).
     * @param markedState - the starting marked state of the button (for retry & play is true).
     */
    public PlayButton(Boolean markedState) {
        super(markedState);
    }

    /**
     * method for turning retry flag on (the retry flag indicates if "retry" or "play" should be written).
     * it is turned on immediately when starting the first turn (from then on, the menu screen won't be displayed).
     */
    public void turnRetryOn() {
        this.retryFlag = true;
    }

    @Override
    public void drawSelf(DrawSurface d) {
        if (isMarked()) { //if the button is marked, it is surrounded by a gray rec.
            d.setColor(Color.lightGray);
            d.fillRectangle(x - 5, y - 5, width + 10, height + 10);
        }
        d.setColor(new Color(125, 224, 92));
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.WHITE);
        if (retryFlag) {
            d.drawText(250, 340, "Retry", 50);
        } else {
            d.drawText(260, 340, "Play", 50);
        }
    }
}
