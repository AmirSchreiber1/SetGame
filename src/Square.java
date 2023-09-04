import biuoop.DrawSurface;
import java.awt.Point;

/**
 * class for the square shape.
 */
public class Square extends Shape implements Sprite {

    /**
     * constructor (calling super).
     * @param markedState - true if the player's keyboard is currently on the card, false otherwise.
     * @param color - the color of the shape (RED/BLUE/GREEN).
     * @param fill - the fill of the shape (EMPTY/HALF/FULL).
     * @param shape - the type of the shape (SQUARE/TRIANGLE/CIRCLE).
     * @param code - the number to which the specific shape is mapped according to the key doc.
     * @param chosenFlag - true if the player chose the shape (as part of a set), false otherwise.
     * @param center - the point representing the center of the shape on the gui window.
     */
    public Square(Boolean markedState, EColor color, EFill fill, EShape shape, int code, boolean chosenFlag, Point center) {
        super(markedState, color, fill, shape, code, chosenFlag, center);
    }

    @Override
    public void drawSelf(DrawSurface surface) {
        super.drawSelf(surface); // draws outside board-square and chooses a color for the specific shape.
        surface.fillRectangle((int) (this.getCenter().getX() - Shape.BOARD_SQUARE_EDGE / 4),
                (int) (this.getCenter().getY() - Shape.BOARD_SQUARE_EDGE / 4),
                (int) (Shape.BOARD_SQUARE_EDGE / 2),
                (int) (Shape.BOARD_SQUARE_EDGE / 2));
        if (this.getFill() != EFill.FULL) {
            getNonFullFillColor(surface); //HALF (light version of the shape's color) or EMPTY (white).
            surface.fillRectangle((int) (this.getCenter().getX() - Shape.BOARD_SQUARE_EDGE / 4 + 3),
                    (int) (this.getCenter().getY() - Shape.BOARD_SQUARE_EDGE / 4 + 3),
                    (int) (Shape.BOARD_SQUARE_EDGE / 2 - 6),
                    (int) (Shape.BOARD_SQUARE_EDGE / 2) - 6);
        }
    }
}
