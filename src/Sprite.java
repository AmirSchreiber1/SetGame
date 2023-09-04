import biuoop.DrawSurface;

/**
 * an interface for sprites (object which are displayed on screen).
 */
public interface Sprite {
    /**
     * method for drawing the sprite object on screen.
     * @param d - the draw surface object (a part of the biuoop animation package).
     */
    void drawSelf(DrawSurface d);
}
