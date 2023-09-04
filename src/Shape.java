import java.awt.Color;
import java.awt.Point;
import biuoop.DrawSurface;

/**
 * abstract class for sharing code between the different shapes classes (square, triangle, circle).
 */
public abstract class Shape extends MarkAble implements Sprite {
    public static final double BOARD_SQUARE_EDGE = 130;

    private final EColor color;
    private final EFill fill;
    private final EShape shape;
    private final int code;
    private boolean chosenFlag;
    private Point center;

    /**
     * the constructor method.
     * @param markedState - the starting marking-state of the card/shape created.
     * @param color - the color of the shape (RED/BLUE/GREEN).
     * @param fill - the fill of the shape (EMPTY/HALF/FULL).
     * @param shape - the shape-type of the card (SQUARE/TRIANGLE/CIRCLE).
     * @param code - the number the curr shaped is mapped to according to the key doc.
     * @param chosenFlag - true if the user chose the card and false otherwise.
     * @param center - a point representing the card/shape position on the game window.
     */
    public Shape(Boolean markedState, EColor color, EFill fill, EShape shape, int code, boolean chosenFlag, Point center) {
        super(markedState);
        this.color = color;
        this.fill = fill;
        this.shape = shape;
        this.code = code; //each shape is mapped to a specific number (depends on shape's type, color and fill).
        this.chosenFlag = chosenFlag; //true if user clicked on the shape, false otherwise.
        this.center = center;
    }

    /**
     * getter for the color of the shape.
     * @return - the ECOLOR color of the shape.
     */
    public EColor getColor() {
        return this.color;
    }

    /**
     * getter for the fill of the shape.
     * @return - the EFILL fill of the shape.
     */
    public EFill getFill() {
        return this.fill;
    }

    /**
     * getter for the shape-type of the shape.
     * @return - the EShape shape of the shape.
     */
    public EShape getShape() {
        return this.shape;
    }

    /**
     * getter for the code of the shape.
     * @return - the number the shape is mapped to according to the key doc.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * querying if the shape was chosen by the user.
     * @return - true if the user chose the shape, false otherwise.
     */
    public boolean isChosen() {
        return this.chosenFlag;
    }

    /**
     * getter for the center point of the shape.
     * @return - the center point of the shape.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * setter method for the center point of the shape.
     * @param center - the required center point of the shape.
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public void drawSelf(DrawSurface surface) {
        // outside square of every shape ("board square shape"):

        //the outsides of the square (painted in yellow if shape is chosen).
        if (isChosen()) {
            surface.setColor(new Color(245, 216, 102));
            surface.fillRectangle((int) (this.center.getX() - BOARD_SQUARE_EDGE / 2 - 2),
                    (int) (this.center.getY() - BOARD_SQUARE_EDGE / 2 - 2),
                    (int) BOARD_SQUARE_EDGE + 4,
                    (int) BOARD_SQUARE_EDGE + 4);
        } else {
            surface.setColor(Color.BLACK);
            surface.fillRectangle((int) (this.center.getX() - BOARD_SQUARE_EDGE / 2),
                    (int) (this.center.getY() - BOARD_SQUARE_EDGE / 2),
                    (int) BOARD_SQUARE_EDGE,
                    (int) BOARD_SQUARE_EDGE);
        }

        // the insides of the square (painted in gray if user keyboard is "on" the square).
        if (this.isMarked()) {
            surface.setColor(Color.lightGray);
        } else {
            surface.setColor(Color.WHITE);
        }
        surface.fillRectangle((int) (this.center.getX() - BOARD_SQUARE_EDGE / 2) + 2,
                (int) (this.center.getY() - BOARD_SQUARE_EDGE / 2) + 2,
                (int) BOARD_SQUARE_EDGE - 4,
                (int) BOARD_SQUARE_EDGE - 4);


        // set the color for the specific shape:
        if (this.getColor() == EColor.RED) {
            surface.setColor(Color.RED);
        } else if (this.getColor() == EColor.BLUE) {
            surface.setColor(Color.BLUE);
        } else if (this.getColor() == EColor.GREEN) {
            surface.setColor(new Color(9, 152, 43));
        }
        // specific details for every shape are drawn in the concrete classes.
    }

    /**
     * turn on/off the choice state of the shape.
     */
    public void changeChoiceState() {
        this.chosenFlag = !this.chosenFlag;
    }

    /**
     * getter for the empty/half-fill color of the shapes.
     * @param surface - the draw surface object for the animation.
     */
    public void getNonFullFillColor(DrawSurface surface) {
        if (this.getFill() == EFill.EMPTY) {
            surface.setColor(Color.WHITE);
        } else if (this.getFill() == EFill.HALF) {
            if (this.getColor() == EColor.RED) {
                surface.setColor(new Color(255, 165, 165));
            }
            if (this.getColor() == EColor.BLUE) {
                surface.setColor((new Color(158, 203, 252)));
            }
            if (this.getColor() == EColor.GREEN) {
                surface.setColor(new Color(172, 255, 178));
            }
        }
    }
}
