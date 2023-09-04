/**
 * abstract class to share code between mark-able items (like buttons or cards, which are marked when user's keyboard
 * is "on" them).
 */
public abstract class MarkAble {
    private boolean markedState;

    /**
     * constructor, receives the starting marking-state of the item.
     * @param markedState - the starting marking-state of the item.
     */
    public MarkAble(Boolean markedState) {
        this.markedState = markedState;
    }

    /**
     * method for querying the current marking state of the item.
     * @return - the current marking state of the item.
     */
    public boolean isMarked() {
        return this.markedState;
    }

    /**
     * method for changing the current marking state of the item.
     */
    public void changeMarkedState() {
        this.markedState = !this.isMarked();
    }
}
