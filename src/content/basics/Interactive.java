package content.basics;

import content.basics.Drawable;
import screen.CharScreen;
import com.googlecode.lanterna.terminal.Terminal;
import exceptions.EscKeyPressed;

/**
 * Interface for interactive content.
 *
 * @author mike239x
 */
public interface Interactive extends Drawable {

    /**
     * Capture input stream of terminal and handle it.
     *
     * @param t terminal
     * @param screen screen for drawing
     * @throws exceptions.EscKeyPressed
     */
    public void captureInput(Terminal t, CharScreen screen) 
            throws EscKeyPressed;
}
