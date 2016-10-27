package screen;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Screen of terminal. Flushes its content directly to terminal.
 *
 * @author mike239x
 */
public class TerminalScreen extends CharScreen {

    private final Terminal terminal;
    
    //colored chars, which will be drawn by next flushing
    private final char[][] charsCast;
    private final short[][] colorsCast;
    //colored chars, which are already drawn.
    private final char[][] chars;
    private final short[][] colors;

    public TerminalScreen(Terminal t) {
        super(0, 0,
                t.getTerminalSize().getColumns(),
                t.getTerminalSize().getRows());
        this.terminal = t;
        charsCast = new char[width][height];
        colorsCast = new short[width][height];
        chars = new char[width][height];
        colors = new short[width][height];
    }

    @Override
    public void flush() {
        short curColor = 0;
        terminal.applyForegroundColor(curColor);
        int drawPosX = -1;
        int drawPosY = -1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (colors[x][y] != colorsCast[x][y]
                        || chars[x][y] != charsCast[x][y]) {
                    if (curColor != colors[x][y]) {
                        curColor = colors[x][y];
                        terminal.applyForegroundColor(curColor);
                    }
                    if (x != drawPosX || y != drawPosY) {
                        terminal.moveCursor(x + posX, y + posY);
                        drawPosX = x;
                        drawPosY = y;
                    }
                    terminal.putCharacter(chars[x][y]);
                    drawPosX++;
                }
                charsCast[x][y] = chars[x][y];
                colorsCast[x][y] = colors[x][y];
            }
        }
    }

    @Override
    protected void writeChar(char c) {
        chars[cursorPosX][cursorPosY] = c;
        colors[cursorPosX][cursorPosY] = currentColor;
    }

    @Override
    public void setColor(short color) {
        currentColor = color;
    }

    @Override
    public char getCharAt(int x, int y) {
        if (contains(x, y)) {
            return chars[x][y];
        }
        return ' ';
    }

    @Override
    public short getColotAt(int x, int y) {
        if (contains(x, y)) {
            return colors[x][y];
        }
        return 0;
    }

}
