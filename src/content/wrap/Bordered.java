/**
 * Actually, I wanted to make this in such a way, that Bordered<Drawable> would
 * implement Drawable interface, and Bordered<Interactive> would implement
 * Interactive interface, but I haven't found how to do it, that's why I have to
 * make almost every content Interactive.
 */
package content.wrap;

import content.basics.Interactive;
import com.googlecode.lanterna.terminal.Terminal;
import exceptions.EscKeyPressed;
import screen.*;
import java.util.*;

/**
 * Class for warping the given context with a border. Takes content and makes
 * content in border (which is also content).
 *
 * @author mike239x
 */
public class Bordered implements Interactive {

    //some standart border types:
    private static final char[][] borders
            = {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'┌', '─', '┐', '└', '─', '┘', '│', '│'},
            {'╔', '═', '╗', '╚', '═', '╝', '║', '║'},
            {'█', '█', '█', '█', '█', '█', '█', '█'}};
    
    private final char[] border;
    
    private short borderColor;
    private final Interactive content;

    public Interactive getContent() {
        return content;
    }

    public Bordered(Interactive content, int borderType) {
        this.content = content;
        borderColor = content.getMainColor();
        if (borderType > -1 && borderType < borders.length) {
            border = borders[borderType];
        } else {
            border = borders[1];
        }
    }
    
    public Bordered(Interactive content) {
        this(content, 1);
    }

    public Bordered(Interactive content, char borderFiller) {
        this(content, 0);
        for (int i = 0; i < border.length; i++) {
            border[i] = borderFiller;
        }
    }
    
    @Override
    public int getOptimalWidth() {
        return content.getOptimalWidth() + 2;
    }

    @Override
    public int getOptimalHeight() {
        return content.getOptimalHeight() + 2;
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen) throws EscKeyPressed {
        drawBorder(screen);
        CharScreenPart center = screen.getPart(1, 1,
                screen.getWidth() - 2, screen.getHeight() - 2);
        content.captureInput(t, center);
    }

    @Override
    public void draw(CharScreen screen) {
        drawBorder(screen);
        CharScreenPart center = screen.getPart(1, 1,
                screen.getWidth() - 2, screen.getHeight() - 2);
        content.draw(center);
    }

    private void drawBorder(CharScreen screen) {
        int w = screen.getWidth();
        int h = screen.getHeight();
        screen.setColor(borderColor);
        screen.moveCursor(0, 0);
        screen.putChar(border[0]);
        for (int i = 1; i < w - 1; i++) {
            screen.putChar(border[1]);
        }
        screen.putChar(border[2]);
        screen.moveCursor(0, screen.getHeight() - 1);
        screen.putChar(border[3]);
        for (int i = 1; i < w - 1; i++) {
            screen.putChar(border[4]);
        }
        screen.putChar(border[5]);
        for (int j = 1; j < h - 1; j++) {
            screen.moveCursor(0, j);
            screen.putChar(border[6]);
            screen.moveCursor(w - 1, j);
            screen.putChar(border[7]);
        }
    }

    /**
     * Get color of border.
     *
     * @return border color
     */
    public short getBorderColor() {
        return borderColor;
    }

    /**
     * Set color of border.
     *
     * @param color new border color
     */
    public void setBorderColor(short color) {
        borderColor = color;
    }

    /**
     * Set color of border to be the same as main color of inner content.
     */
    public void setDefaultBorderColor() {
        borderColor = content.getMainColor();
    }

    @Override
    public short getMainColor() {
        return content.getMainColor();
    }

    @Override
    public void setMainColor(short color) {
        content.setMainColor(color);
    }

    //borders all elements in arr
    public static void borderArray(Interactive[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Bordered(arr[i]);
        }
    }

    //borders all elements in list
    public static void borderList(List<Interactive> li) {
        for (int i = 0; i < li.size(); i++) {
            Interactive get = li.get(i);
            li.set(i, new Bordered((get)));
        }
    }

}
