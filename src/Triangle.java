import java.awt.Point;
import java.awt.Polygon;

import biuoop.DrawSurface;

/**
 * class for the triangle shape.
 */
public class Triangle extends Shape implements Sprite {

    /**
     * constructor method (calling super).
     * @param markedState - true if the player's keyboard is currently on the card, false otherwise.
     * @param color - the color of the shape (RED/BLUE/GREEN).
     * @param fill - the fill of the shape (EMPTY/HALF/FULL).
     * @param shape - the type of the shape (SQUARE/TRIANGLE/CIRCLE).
     * @param code - the number to which the specific shape is mapped according to the key doc.
     * @param chosenFlag - true if the player chose the shape (as part of a set), false otherwise.
     * @param center - the point representing the center of the shape on the gui window.
     */
    public Triangle(boolean markedState, EColor color, EFill fill, EShape shape, int code, boolean chosenFlag, Point center) {
        super(markedState, color, fill, shape, code, chosenFlag, center);
    }

    @Override
    public void drawSelf(DrawSurface surface) {
        super.drawSelf(surface); // draws outside board-square and chooses a color for the specific shape.

        //outside triangle:
        double x = this.getCenter().getX(), y = this.getCenter().getY();
        int[] xPoints = {(int) (x + Shape.BOARD_SQUARE_EDGE / 3), (int) (x - Shape.BOARD_SQUARE_EDGE / 3), (int) x},
                yPoints = {(int) (y + Shape.BOARD_SQUARE_EDGE / 3),
                        (int) (y + Shape.BOARD_SQUARE_EDGE / 3), (int) (y - Shape.BOARD_SQUARE_EDGE / 3)};
        Polygon outsideTriangle = new Polygon(xPoints, yPoints, 3);
        surface.fillPolygon(outsideTriangle);

        //inside triangle:
        if (this.getFill() != EFill.FULL) {
            int[] inXPoints = {(int) (x + Shape.BOARD_SQUARE_EDGE / 3) - 6,
                    (int) (x - Shape.BOARD_SQUARE_EDGE / 3) + 6, (int) x};
            int[] inYPoints = {(int) (y + Shape.BOARD_SQUARE_EDGE / 3 - 3),
                    (int) (y + Shape.BOARD_SQUARE_EDGE / 3 - 3), (int) (y - Shape.BOARD_SQUARE_EDGE / 3 + 6)};
            Polygon insideTriangle = new Polygon(inXPoints, inYPoints, 3);
            getNonFullFillColor(surface); //HALF (light version of the shape's color) or EMPTY (white).
            surface.fillPolygon(insideTriangle);
        }
    }
}
