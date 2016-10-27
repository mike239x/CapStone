package content;

import content.basics.Interactive;
import com.googlecode.lanterna.terminal.Terminal;
import screen.CharScreen;

/**
 * Label. Interactive, sort of.
 *
 * @author mike239x
 */
public class Label implements Interactive {

    private final String[] text; // list of strings without \n
    private final int width, height; //just to know the preferred size
    private short color;

    /**
     * Creates label with given message and given color.
     *
     * @param msg text of label
     * @param color color of text
     */
    public Label(String msg, short color) {
        text = msg.split("\n");
        int h = 0;
        int w = 0;
        for (String s : text) {
            h++;
            int l = s.length();
            if (l > w) {
                w = l;
            }
        }
        this.height = h;
        this.width = w;
        this.color = color;
    }

    /**
     * Creates label with given message and white color.
     *
     * @param msg text of label
     */
    public Label(String msg) {
        this(msg, (short) 7);
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen) {
    }

    @Override
    public int getOptimalWidth() {
        return width;
    }

    @Override
    public int getOptimalHeight() {
        return height;
    }

    @Override
    public void draw(CharScreen screen) {
        screen.setColor(color);
        int w = screen.getWidth();
        int h = screen.getHeight();
        int yStart = (h - height) / 2;
        for (int j = 0; j < height; j++) {
            char[] toDraw = middleAlign(text[j], screen.getWidth()).toCharArray();
            screen.moveCursor(0, yStart + j);
            for (char c : toDraw) {
                screen.putChar(c);
            }
        }
    }

    private static String middleAlign(String s, int length) {
        if (length < 1) return "";
        if (s.length() > length) return s.substring(0,length-1) + "_";
        int rest = length - s.length();
        String result = "";
        while (result.length() < rest / 2) {
            result += " ";
        }
        result+=s;
        while (result.length() < length) {
            result += " ";
        }
        return result;
    }
    
    /**
     * Set the main color to the given one.
     *
     * @param color new color
     */
    public void setColor(short color) {
        this.color = color;
    }

    /**
     * @return used color
     */
    public short getColor() {
        return color;
    }

    @Override
    public short getMainColor() {
        return color;
    }

    @Override
    public void setMainColor(short color) {
        this.color = color;
    }

    @Override
    public String toString() {
        if (text == null) {
            return "";
        }
        if (text.length == 0) {
            return "";
        }
        String result = "";
        for (int i = 0; i < text.length-1; i++) {
            result += text[i]+"\n";
        }
        result +=text[text.length-1];
        return result;
    }

}
