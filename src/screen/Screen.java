package screen;

/**
 * Abstract class for any kind of windows, like terminal window, game field or
 * any other screen.
 *
 * @author mike239x
 */
public abstract class Screen {

    //X-Coordinate of the screens left top corners position on the window.
    protected int posX;
    //Y-Coordinate of the screens left top corners position on the window.
    protected int posY;
    //Width of this screen.
    protected int width;
    //Height of this screen.
    protected int height;

    public Screen(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    /**
     * Tells if screen contains the given point.
     *
     * @param x x-coordinate of the given point
     * @param y y-coordinate of the given point
     * @return whether or not the point (x,y) lays inside the screen
     */
    public boolean contains(int x, int y) {
        return (x > -1 && x < width && y > -1 && y < height);
    }

}
