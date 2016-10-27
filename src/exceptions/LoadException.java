package exceptions;

/**
 * @author mike239x
 */
public class LoadException extends Exception {

    public LoadException() {
    }

    @Override
    public String toString() {
        return "Unable to load map from save.";
    }
}
