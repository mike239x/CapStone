package content.basics;

import screen.CharScreen;

/**
 * Interface for drawable content.
 *
 * @author mike239x
 */
public interface Drawable {

    /**
     * @return Preferred width of this content.
     */
    public int getOptimalWidth();

    /**
     * @return Preferred height of this content.
     */
    public int getOptimalHeight();

    /**
     * Draw this content to the given screen.
     *
     * @param screen screen for drawing
     */
    public void draw(CharScreen screen);

    /**
     * Get the basic color used for this content.
     *
     * @return color
     */
    public short getMainColor();

    /**
     * Set the basic color used for this content.
     *
     * @param color color
     */
    public void setMainColor(short color);

}
