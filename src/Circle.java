import biuoop.DrawSurface;
import java.awt.Point;

/**
 * class for the circle shape.
 */
public class Circle extends Shape implements Sprite {

    /**
     * constructor (simply calls for the super class constructor).
     * @param markedState - true if the player's keyboard is currently on the card, false otherwise.
     * @param color - the color of the shape (RED/BLUE/GREEN).
     * @param fill - the fill of the shape (EMPTY/HALF/FULL).
     * @param shape - the type of the shape (SQUARE/TRIANGLE/CIRCLE).
     * @param code - the number to which the specific shape is mapped according to the key doc.
     * @param chosenFlag - true if the player chose the shape (as part of a set), false otherwise.
     * @param center - the point representing the center of the shape on the gui window.
     */
    public Circle(Boolean markedState, EColor color, EFill fill, EShape shape, int code, boolean chosenFlag, Point center) {
        super(markedState, color, fill, shape, code, chosenFlag, center);
    }

    @Override
    public void drawSelf(DrawSurface surface) {
        super.drawSelf(surface); // draws outside board-square and chooses a color for the specific shape.
        surface.fillCircle((int) this.getCenter().getX(), (int) this.getCenter().getY(),
                (int) (Shape.BOARD_SQUARE_EDGE / 3));
        if (this.getFill() != EFill.FULL) {
            getNonFullFillColor(surface); //HALF (light version of the shape's color) or EMPTY (white).
            surface.fillCircle((int) this.getCenter().getX(), (int) this.getCenter().getY(),
                    (int) (Shape.BOARD_SQUARE_EDGE / 3) - 3);
        }
    }
}
