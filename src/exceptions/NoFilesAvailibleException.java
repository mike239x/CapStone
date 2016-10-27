package exceptions;

/**
 * Is thrown if in file chooser there are no files at all.
 *
 * @author mike239x
 */
public class NoFilesAvailibleException extends Exception {

    public NoFilesAvailibleException() {
    }

    @Override
    public String toString() {
        return "No saves are available.";
    }
    
}
