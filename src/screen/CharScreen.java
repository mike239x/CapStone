package screen;

import content.basics.Drawable;

/**
 * Screen with possibility to store colored chars and later flush them. As
 * terminal, has cursor to put char on its position. For flushing uses the
 * background color which is currently used by terminal.
 *
 * @author mike239x
 */
public abstract class CharScreen extends Screen {

    //X-coordinate of cursor.
    protected int cursorPosX;
    //Y-coordinate of cursor.
    protected int cursorPosY;
    //Color currently used for printing (before flushing).
    protected short currentColor;

    public CharScreen(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    public void fill(char c) {

        for (int y = 0; y < height; y++) {
            moveCursor(0, y);
            for (int x = 0; x < width; x++) {
                putChar(c);
            }
        }
    }

    public abstract void flush();

    public void moveCursor(int x, int y) {
        cursorPosX = x;
        cursorPosY = y;
    }

    //Put char on the screen and shift cursor one position right. 
    //If out of bounds, puts nothing.
    public void putChar(char c) {
        if (contains(cursorPosX, cursorPosY)) {
            writeChar(c);
        }
        cursorPosX++;
    }

    //Put char on the screen (depends on the implementation).
    protected abstract void writeChar(char c);

    public abstract void setColor(short color);

    // Create screen for part of this screen.
    public CharScreenPart getPart(int x, int y, int width, int height) {
        CharScreenPart part = new CharScreenPart(this, x, y, width, height);
        return part;
    }

    // Get part of this screen, that fits given content.
    public CharScreenPart getPart(int x, int y, Drawable content) {
        CharScreenPart part = new CharScreenPart(this, x, y,
                content.getOptimalWidth(), content.getOptimalHeight());
        return part;
    }

    // Get center part of this screen, that fits given content.
    public CharScreenPart getCenter(Drawable content) {
        int x = (width - content.getOptimalWidth()) / 2;
        int y = (height - content.getOptimalHeight()) / 2;
        CharScreenPart part = new CharScreenPart(this, x, y,
                content.getOptimalWidth(), content.getOptimalHeight());
        return part;
    }

    // Intersect the given part of the screen and the screen.
    public CharScreenPart intersect(CharScreenPart part) {
        int x = part.posX;
        if (x < 0) {
            x = 0;
        }
        int y = part.posY;
        if (y < 0) {
            y = 0;
        }
        int xEnd = part.posX + part.width;
        if (xEnd > width) {
            xEnd = width;
        }
        int yEnd = part.posY + part.height;
        if (yEnd > height) {
            yEnd = height;
        }
        CharScreenPart subpart
                = new CharScreenPart(this, x, y, xEnd - x, yEnd - y);
        return subpart;
    }

    public abstract char getCharAt(int x, int y);

    public abstract short getColotAt(int x, int y);

}
