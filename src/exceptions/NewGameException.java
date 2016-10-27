package exceptions;

/**
 * Really rare exception, is thrown if game get some problem while 
 * creating new game.
 *
 * @author mike239x
 */
public class NewGameException extends Exception {

    public NewGameException() {
    }

    @Override
    public String toString() {
        return "Got problem creating new game.";
    }
}
