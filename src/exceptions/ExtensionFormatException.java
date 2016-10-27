package exceptions;

/**
 * Excetion thrown if the given exception has wrong format.
 * 
 * @author mike239x
 */
public class ExtensionFormatException extends Exception{

    public final String badExt;
    
    public ExtensionFormatException(String ext) {
        badExt = ext;
    }
    
}
