import biuoop.DrawSurface;
import java.awt.Color;

/**
 * class for the exit button (appearing also on the menu screen and on the score screen).
 */
public class ExitButton extends MarkAble implements Sprite {
    private final int x = 210;
    private final int y = 420;
    private final int width = 200;
    private final int height = 52;

    /**
     * constructor (simply calls super).
     * @param markedState - is the card marked by the player's keyboard.
     */
    public ExitButton(Boolean markedState) {
        super(markedState);
    }

    @Override
    public void drawSelf(DrawSurface d) {
        if (isMarked()) { //if button is marked, it is surrounded by a gray rec.
            d.setColor(Color.lightGray);
            d.fillRectangle(x - 5, y - 5, width + 10, height + 10);
        }
        d.setColor(new Color(224, 92, 92));
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.WHITE);
        d.drawText(260, 462, "Exit", 50);
    }
}
