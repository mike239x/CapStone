package content;

import content.basics.Interactive;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import exceptions.EscKeyPressed;
import screen.CharScreen;

/**
 * Text field, here you can type a String. 
 * Spaces and most of special chars are not allowed.
 *
 * @author mike239x
 */
public class TextField implements Interactive {

    private final int textMaxLength;
    private String text;
    private short color;

    public String getText() {
        return text;
    }

    public TextField(int maxLength) {
        this.textMaxLength = maxLength;
        text = "";
        color = 7; //white
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen) throws EscKeyPressed {
        while (true) {
            this.draw(screen);
            screen.flush(); //yeah, flush this!
            Key key = null;
            while (key == null) {
                key = t.readInput();
            }
            Key.Kind kind = key.getKind();
            if (kind.equals(Key.Kind.Escape)) {
                throw new EscKeyPressed();
            } else if (kind.equals(Key.Kind.Enter)) {
                return;
            } else if (kind.equals(Key.Kind.Backspace)) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
            } else if (kind.equals(Key.Kind.NormalKey)) {
                char c = key.getCharacter();
                if (Character.isAlphabetic(c) || Character.isDigit(c)
                        || c == '_' || c == '!' || c == '.'
                        || c == ','  || c == '#' || c == '~') {
                    if (text.length() < textMaxLength) {
                        text += c;
                    }
                }
            }
        }
    }

    @Override
    public int getOptimalWidth() {
        return textMaxLength;
    }

    @Override
    public int getOptimalHeight() {
        return 1;
    }

    @Override
    public void draw(CharScreen screen) {
        Label toDraw;
        if (text.length() != textMaxLength) {
            toDraw = new Label(text + "_", color);
        } else {
            toDraw = new Label(text, color);
        }
        toDraw.draw(screen);
    }

    @Override
    public short getMainColor() {
        return color;
    }

    @Override
    public void setMainColor(short color) {
        this.color = color;
    }

}
