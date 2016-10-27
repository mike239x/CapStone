package screen;

/**
 * A part of CharScreen. Prints to the parent screen; by flushing flushes parent
 * content. Also by printing changes the cursor position and the color used by
 * the parent.
 *
 * @author mike239x
 */
public class CharScreenPart extends CharScreen {

    private final CharScreen parent;

    public CharScreenPart(CharScreen screen,
            int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        this.parent = screen;
    }

    //Flush saved info to the window (with automatic shift of carriage).
    //Also flushes all the changes happened to upper screen-layers.
    @Override
    public void flush() {
        parent.flush();
    }

    @Override
    protected void writeChar(char c) {
        if (parent.cursorPosX != cursorPosX + posX
                || parent.cursorPosY != cursorPosY + posY) {
            parent.moveCursor(cursorPosX + posX, cursorPosY + posY);
        }
        parent.putChar(c);
    }

    @Override
    public void setColor(short color) {
        parent.setColor(color);
    }

    @Override
    public char getCharAt(int x, int y) {
        return parent.getCharAt(x + posX, y + posY);
    }

    @Override
    public short getColotAt(int x, int y) {
        return parent.getColotAt(x + posX, y + posY);
    }

}
