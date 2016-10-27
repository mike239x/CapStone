package content;

import com.googlecode.lanterna.terminal.Terminal;
import content.wrap.Header;
import exceptions.*;
import java.io.File;
import java.util.*;
import screen.*;

/**
 * Analog of JOptionPane. Allows to show some special kinds of
 * dialogs and get result from them.
 *
 * @author mike239x
 */
public abstract class OptionPane {

    private final static String[] YES_NO = {"YES", "NO"};
    private final static String[] OK = {"OK"};

    public static boolean showConfirmDialog(String question,
            Terminal t, CharScreen screen) throws EscKeyPressed {
        Dialog dialog = new Dialog(question, YES_NO);
        CharScreenPart forDialog = screen.getCenter(dialog);
        dialog.captureInput(t, forDialog);
        int result = dialog.getChosen();
        return result == 0;
    }

    public static void showMessageDialog(String msg,
            Terminal t, CharScreen screen) throws EscKeyPressed {
        Dialog dialog = new Dialog(msg, OK);
        CharScreenPart forDialog = screen.getCenter(dialog);
        dialog.captureInput(t, forDialog);
    }

    public static final String NEW_SLOT = "new slot";

    public static String showChooseFileDialog(
            String msg,
            String ext, String dir,
            boolean allowNewFileChoice,
            Terminal t, CharScreen screen)
            throws EscKeyPressed, ExtensionFormatException,
            NoFilesAvailibleException {
        if (!ext.matches("^\\.[a-z]+$")) {
            //well, only normal extensions are allowed.
            throw new ExtensionFormatException(ext);
        }
        File directory = new File(dir);
        File[] files = directory.listFiles();
        List<Label> fileNames = new ArrayList<>();
        try {
            for (File file : files) {
                String name = file.getName();
                if (name.endsWith(ext)) {
                    name = name.substring(0, name.length() - ext.length());
                    fileNames.add(new Label(name));
                }
            }
        } catch (NullPointerException e) {
        }
        if (allowNewFileChoice) {
            fileNames.add(new Label(NEW_SLOT, (short) 246)); //gray color
        }
        if (fileNames.isEmpty()) {
            throw new NoFilesAvailibleException();
        }
        Dialog dialog = new Dialog(msg,
                fileNames.toArray(new Label[0]),
                ContentList.Dispose.VERTICALLY);
        CharScreenPart forDialog = screen.getCenter(dialog);
        forDialog = screen.intersect(forDialog);
        dialog.captureInput(t, forDialog);
        int result = dialog.getChosen();
        if (result == -1) {
            return null;
        }
        if (allowNewFileChoice && result == fileNames.size() - 1) {
            return NEW_SLOT;
        } else {
            return fileNames.get(result).toString() + ext;
        }
    }

    public static String showInputDialog(String msg, int maxInputSize,
            Terminal t, CharScreen screen) throws EscKeyPressed {
        Header dialog = new Header(msg, new TextField(maxInputSize));
        CharScreenPart forDialog = screen.getCenter(dialog);
        dialog.captureInput(t, forDialog);
        return ((TextField) dialog.getContent()).getText();
    }
}
