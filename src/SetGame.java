import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * the class which makes the game itself (in a turn) run (including animating, game logic, etc.).
 */
public class SetGame {
    private Shape[] shapes;
    private final Score score;
    private int numOfChosenShapes = 0;
    private final ShapeGenerator sg;
    private final GUI gui;
    private final Sleeper sleeper;
    private final KeyboardSensor ks;
    private int markedShapeNum = 4;
    private boolean stopTurn = false;

    /**
     * @param gui     - the gui object responsible for handling the animation (taken from biu-oop jar file).
     * @param sleeper - the object responsible for pausing the image on the screen every given amount of milliseconds.
     * @param ks      - keyboard sensor.
     */
    public SetGame(GUI gui, Sleeper sleeper, KeyboardSensor ks) {
        this.sg = new ShapeGenerator();
        this.shapes = sg.generateNewGame();
        this.score = new Score();
        this.gui = gui;
        this.sleeper = sleeper;
        this.ks = ks;
    }

    /**
     * getter for the score object.
     *
     * @return - the score object.
     */
    public int getScore() {
        return this.score.getScore();
    }

    /**
     * increase the num-of-chosen-shapes field by 1 when user chooses a card.
     */
    public void increaseNumOfChosenShapes() {
        this.numOfChosenShapes += 1;
    }

    /**
     * decrease the num-of-chosen-shapes field by 1 when user cancel a choice of a card.
     */
    public void decreaseNumOfChosenShapes() {
        this.numOfChosenShapes -= 1;
    }

    /**
     * method for responding for arrow&space keys pressed by the user.
     * --see the small numbered board on the key doc for better understanding.--
     */
    public void manageKeyBoard() {
        if (this.ks.isPressed(KeyboardSensor.UP_KEY)) { //go up in board:
            if (markedShapeNum > 2) { // if not on the first row (there is space to go up).
                this.shapes[markedShapeNum].changeMarkedState(); //current marked shape isn't marked anymore.
                markedShapeNum -= 3; //move one row down.
                shapes[markedShapeNum].changeMarkedState(); //mark the shape just under the previous marked shape.
            } else { //no space to go up to (on the first row).
                return;
            }
        }

        if (this.ks.isPressed(KeyboardSensor.DOWN_KEY)) { //go down in board:
            if (markedShapeNum < 6) { // if not on the last row (there is space to go down).
                this.shapes[markedShapeNum].changeMarkedState(); //current marked shape isn't marked anymore.
                markedShapeNum += 3; //move one row up.
                shapes[markedShapeNum].changeMarkedState(); //mark the shape just above the previous marked shape.
            } else { //no space to go down to (on the last row).
                return;
            }
        }
        if (this.ks.isPressed(KeyboardSensor.RIGHT_KEY)) { //go right in the board:
            if (markedShapeNum % 3 != 2) { //if not on the rightest column (there is space to go right).
                this.shapes[markedShapeNum].changeMarkedState(); //current marked shape isn't marked anymore.
                markedShapeNum += 1; //move one column right.
                shapes[markedShapeNum].changeMarkedState(); //mark the shape just right to the previous marked shape.
            } else { //no space to move right (on the rightest column).
                return;
            }
        }
        if (this.ks.isPressed(KeyboardSensor.LEFT_KEY)) { //go left in the board:
            if (markedShapeNum % 3 != 0) { //if not on the leftest column (there is space to go left).
                this.shapes[markedShapeNum].changeMarkedState(); //current marked shape isn't marked anymore.
                markedShapeNum -= 1; //move one column left.
                shapes[markedShapeNum].changeMarkedState(); //mark the shape just left to the previous marked shape.
            } else { //no space to move left (on the leftest column).
                return;
            }
        }
        if (this.ks.isPressed(KeyboardSensor.SPACE_KEY)) { //choose or un-choose a marked shape.
            shapes[markedShapeNum].changeChoiceState();
            if (shapes[markedShapeNum].isChosen()) {
                increaseNumOfChosenShapes();
            } else {
                decreaseNumOfChosenShapes();
            }
        }
        if (this.ks.isPressed(KeyboardSensor.RETURN_KEY)) {
            stopTurn = true;
        }
    }

    /**
     * check if the chosen set is valid: count each property and assure there is no "2 of X and 1 of Y" structure.
     *
     * @return true if the set is valid, false otherwise.
     */
    private boolean setIsValid() {
        int[] colors = new int[]{0, 0, 0}; // [red counter, blue counter, green counter].
        int[] fills = new int[]{0, 0, 0}; // [empty counter, half counter, full counter].
        int[] shapesTypes = new int[]{0, 0, 0}; // [square counter, triangle counter, circle counter].

        //counting each property of the chosen shapes separately:
        for (Shape shape : this.shapes) {

            if (shape.isChosen()) {

                //color counting:
                if (shape.getColor() == EColor.RED) {
                    colors[0] += 1;
                } else if (shape.getColor() == EColor.BLUE) {
                    colors[1] += 1;
                } else if (shape.getColor() == EColor.GREEN) {
                    colors[2] += 1;
                }

                //fill counting:
                if (shape.getFill() == EFill.EMPTY) {
                    fills[0] += 1;
                } else if (shape.getFill() == EFill.HALF) {
                    fills[1] += 1;
                } else if (shape.getFill() == EFill.FULL) {
                    fills[2] += 1;
                }

                //shape counting:
                if (shape.getShape() == EShape.SQUARE) {
                    shapesTypes[0] += 1;
                }
                if (shape.getShape() == EShape.TRIANGLE) {
                    shapesTypes[1] += 1;
                }
                if (shape.getShape() == EShape.CIRCLE) {
                    shapesTypes[2] += 1;
                }
            }
        }

        //check if all counters don't equal 2. if a counter equals 2, return false. otherwise, return true.
        for (int color : colors) {
            if (color == 2) {
                return false;
            }
        }
        for (int fill : fills) {
            if (fill == 2) {
                return false;
            }
        }
        for (int shapeType : shapesTypes) {
            if (shapeType == 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * turn all shapes un-chosen (when a set is found).
     */
    private void deleteAllChoices() {
        for (Shape shape : this.shapes) {
            if (shape.isChosen()) {
                shape.changeChoiceState();
            }
        }
        numOfChosenShapes = 0;
    }

    /**
     * insert new shapes instead of those chosen (when a set is found).
     */
    private void insertNewShapes() {
        Shape[] shapesCopy = this.shapes.clone();
        replaceShapes(shapesCopy);
        // assure there is at least one available set on the board after the new shapes were created.
        while (sg.numOfAvailableSets(shapesCopy) == 0) {
            replaceShapes(shapesCopy);
        }
        this.shapes = shapesCopy;
    }

    private void replaceShapes(Shape[] shapes) {
        int i = 0;
        for (Shape shape : shapes) {
            if (shape.isChosen()) {
                Shape newShape = sg.replaceShape(shape);
                if (shape.isMarked()) { //new shape created should be marked if the shape it replaced was marked.
                    newShape.changeMarkedState();
                }
                shapes[i] = newShape; //update the shapes' arr with the new shape created.
            }
            i += 1;
        }
    }

    /**
     * the method responsible for running the game itself (in a turn).
     */
    public void run() {
        // timer handling:
        long startTime = System.currentTimeMillis(); //getting current time to know how much time passed while on turn.
        GameTimer timer = new GameTimer(startTime);

        while (true) { //animation running:
            DrawSurface d = this.gui.getDrawSurface();

            if (!timer.run(d)) { //out of time.
                break;
            }

            for (Shape shape : shapes) {
                shape.drawSelf(d); //draw shapes on screen.
            }

            d.setColor(Color.BLACK); //on screen texts:
            d.drawText(120, 75, "Find the Sets!", 60);
            d.drawText(185, 140, "Press the return button to end your turn", 12);
            if (sg.numOfAvailableSets(this.shapes) > 1) { //num of available sets:
                d.drawText(245, 630, sg.numOfAvailableSets(shapes) + " Available Sets", 15);
            } else {
                d.drawText(245, 630, "1 Available Set", 15);
            }

            score.drawSelf(d);
            gui.show(d);
            sleeper.sleepFor(80);  // wait for 80 milliseconds.
            manageKeyBoard();
            if (numOfChosenShapes == 3) { //check the set chosen:
                if (setIsValid()) {
                    this.score.add();
                    insertNewShapes();
                }
                deleteAllChoices(); // either way (valid set was chosen or not) all choices are deleted.
            }
            if (stopTurn) {
                break;
            }
        }
    }
}
