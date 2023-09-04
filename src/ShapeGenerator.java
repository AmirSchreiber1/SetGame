import java.awt.Point;
import java.util.Random;

/**
 * class for the shape generator, responsible for creating shapes when needed (generating a new board, or creating new
 * shapes when a valid set was chosen.
 */
public class ShapeGenerator {
    private final int[] currShapes = new int[27]; // curr on board shapes (mapped to the codes written in the key doc).
    private final Point[] locations = new Point[]{//on board locations for each of the 9 shapes.
            new Point(140, 220),
            new Point(300, 220),
            new Point(460, 220),
            new Point(140, 380),
            new Point(300, 380),
            new Point(460, 380),
            new Point(140, 540),
            new Point(300, 540),
            new Point(460, 540)};

    /**
     * generate 9 shapes for a new game.
     *
     * @return - an array of the new 9 shapes.
     */
    public Shape[] generateShapes() {
        Shape[] shapes = new Shape[9];
        int counter = 0;
        while (counter < 9) { // 9 shapes needed at first.
            Random r = new Random();
            int code = r.nextInt(27);
            //shapes created are random, but different from each other:
            if (currShapes[code] == 0) { // (currShapes[code] = 1 if the shape mapped to this code was already created).
                Shape newShape = generateShapeByCode(code);
                newShape.setCenter(locations[counter]);
                if (counter == 4) { //the shape in the center of the screen is initially marked.
                    newShape.changeMarkedState();
                }
                shapes[counter] = newShape;
                currShapes[code] = 1; // shape mapped to this specific code is taken and shall not be created again.
                counter += 1;
            }
        }
        return shapes;
    }

    /**
     * method for getting an array of shapes for a new game which necessary include at least one available set.
     *
     * @return - shapes array ready for a new game.
     */
    public Shape[] generateNewGame() {
        Shape[] shapes = generateShapes();
        while (numOfAvailableSets(shapes) == 0) {
            shapes = generateShapes();
        }
        return shapes;
    }

    /**
     * create a new shape instead of the one that is passed.
     *
     * @param shape - a shape that is part of a chosen set and shall be replaced.
     * @return - the new replacement shape.
     */
    public Shape replaceShape(Shape shape) {
        Random r = new Random();
        int code = r.nextInt(27);
        while (currShapes[code] == 1) { //assure the new created shape is not already on screen.
            code = r.nextInt(27);
        }
        Shape newShape = generateShapeByCode(code);
        newShape.setCenter(shape.getCenter());
        currShapes[code] = 1; //newshape code shall be 1.
        currShapes[shape.getCode()] = 0; //the code of the shape that was chosen and now disappears should be 0.
        return newShape;
    }

    /**
     * generate the shape mapped to the given number passed as a parameter.
     *
     * @param code - the code of the required shape to be created.
     * @return - the shape created matching the passed code.
     */
    private Shape generateShapeByCode(int code) {
        EColor color;
        EFill fill;
        EShape shape;

        // the properties are determined according to the codes appearing in the key doc:
        if (code < 9) {
            color = EColor.RED;
            if (code < 3) {
                fill = EFill.EMPTY;
            } else if (code < 6) {
                fill = EFill.HALF;
            } else {
                fill = EFill.FULL;
            }
        } else if (code < 18) {
            color = EColor.BLUE;
            if (code < 12) {
                fill = EFill.EMPTY;
            } else if (code < 15) {
                fill = EFill.HALF;
            } else {
                fill = EFill.FULL;
            }
        } else {
            color = EColor.GREEN;
            if (code < 21) {
                fill = EFill.EMPTY;
            } else if (code < 24) {
                fill = EFill.HALF;
            } else {
                fill = EFill.FULL;
            }
        }
        // create a new shape according to the given code (center is assigned in the calling method).
        // marked state is handled later (created initially as false).
        if (code % 3 == 0) {
            shape = EShape.SQUARE;
            return new Square(false, color, fill, shape, code, false, null);
        } else if (code % 3 == 1) {
            shape = EShape.TRIANGLE;
            return new Triangle(false, color, fill, shape, code, false, null);
        } else {
            shape = EShape.CIRCLE;
            return new Circle(false, color, fill, shape, code, false, null);
        }
    }

    /**
     * computing number of available sets on the board.
     * @param shapes - the shapes' arr representing on-board shapes.
     * @return - the number of available sets.
     */
    public int numOfAvailableSets(Shape[] shapes) {
        int numOfAvailableSets = 0;
        boolean isSet;
        for (int i = 0; i < 7; i += 1) {
            for (int j = i + 1; j < 8; j += 1) {
                for (int k = j + 1; k < 9; k += 1) {
                    isSet = true; //assume it's true and change later otherwise.
                    int[] colors = new int[]{0, 0, 0}; // [red counter, blue counter, green counter].
                    int[] fills = new int[]{0, 0, 0}; // [empty counter, half counter, full counter].
                    int[] shapesTypes = new int[]{0, 0, 0}; // [square counter, triangle counter, circle counter].

                    // colors counting:
                    countColors(i, colors, shapes);
                    countColors(j, colors, shapes);
                    countColors(k, colors, shapes);

                    // fills counting:
                    countFills(i, fills, shapes);
                    countFills(j, fills, shapes);
                    countFills(k, fills, shapes);

                    //shapesTypes counting:
                    countShapesTypes(i, shapesTypes, shapes);
                    countShapesTypes(j, shapesTypes, shapes);
                    countShapesTypes(k, shapesTypes, shapes);

                    //if a counter equals 2, set is not valid and doesn't count. otherwise, add 1 to the num of sets.
                    for (int color : colors) {
                        if (color == 2) {
                            isSet = false;
                            break;
                        }
                    }
                    for (int fill : fills) {
                        if (fill == 2) {
                            isSet = false;
                            break;
                        }
                    }
                    for (int shapeType : shapesTypes) {
                        if (shapeType == 2) {
                            isSet = false;
                            break;
                        }
                    }
                    if (isSet) {
                        numOfAvailableSets += 1;
                    }
                }
            }
        }
        return numOfAvailableSets;
    }

    private void countColors(int index, int[] colors, Shape[] shapes) {
        if (shapes[index].getColor() == EColor.RED) {
            colors[0] += 1;
        } else if (shapes[index].getColor() == EColor.BLUE) {
            colors[1] += 1;
        } else if (shapes[index].getColor() == EColor.GREEN) {
            colors[2] += 1;
        }
    }

    private void countFills(int index, int[] fills, Shape[] shapes) {
        if (shapes[index].getFill() == EFill.EMPTY) {
            fills[0] += 1;
        } else if (shapes[index].getFill() == EFill.HALF) {
            fills[1] += 1;
        } else if (shapes[index].getFill() == EFill.FULL) {
            fills[2] += 1;
        }
    }

    private void countShapesTypes(int index, int[] shapesTypes, Shape[] shapes) {
        if (shapes[index].getShape() == EShape.SQUARE) {
            shapesTypes[0] += 1;
        } else if (shapes[index].getShape() == EShape.TRIANGLE) {
            shapesTypes[1] += 1;
        } else if (shapes[index].getShape() == EShape.CIRCLE) {
            shapesTypes[2] += 1;
        }
    }
}
