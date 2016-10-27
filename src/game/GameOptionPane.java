package game;

import content.OptionPane;
import com.googlecode.lanterna.terminal.Terminal;
import content.ContentList;
import content.Dialog;
import exceptions.EscKeyPressed;
import exceptions.ExtensionFormatException;
import exceptions.NoFilesAvailibleException;
import java.io.IOException;
import screen.*;

/**
 * Class with all menus and dialogs usually needed in game.
 *
 * @author mike239x
 */
public class GameOptionPane {

    Terminal terminal;
    CharScreen screen;

    public GameOptionPane(Terminal t, CharScreen terminalScreen) {
        terminal = t;
        screen = terminalScreen;
    }

    public String showLoadDialog()
            throws EscKeyPressed, NoFilesAvailibleException {
        try {
            return OptionPane.showChooseFileDialog("Choose load slot:",
                    Game.saveExt, Game.saveDir, false, terminal, screen);
        } catch (ExtensionFormatException ex) {
            return null;
        }
    }

    public String showSaveDialog() throws EscKeyPressed {
        try {
            return OptionPane.showChooseFileDialog("Choose save slot:",
                    Game.saveExt, Game.saveDir, true, terminal, screen);
        } catch (ExtensionFormatException | NoFilesAvailibleException ex) {
            return null;
        }
    }

    public int showGameLevelDialog() throws EscKeyPressed {
        String[] options = {
            "   EASY   ",
            "  MEDIUM  ",
            "   HARD   ",
            " HARDCORE "};
        Dialog dialog = new Dialog("Choose game level:", options, ContentList.Dispose.VERTICALLY);
        CharScreenPart forDialog = screen.getCenter(dialog);
        dialog.captureInput(terminal, forDialog);
        return dialog.getChosen();
    }

    public String askForSlotName() throws EscKeyPressed {
        return OptionPane.showInputDialog("Type name for a new slot:",
                Game.levelNameMaxLength, terminal, screen);
    }

    public boolean ask(String question) throws EscKeyPressed {
        return OptionPane.showConfirmDialog(question, terminal, screen);
    }

    public void showError(Exception e) {
        try {
            if (e instanceof IOException) {
                OptionPane.showMessageDialog(
                        "ERROR!\nProblems with IO stream.", terminal, screen);
//                OptionPane.showMessageDialog(e.getMessage(), terminal, screen);
            } else {
                OptionPane.showMessageDialog(
                        "ERROR!\n" + e.toString(), terminal, screen);
            }
        } catch (EscKeyPressed ex) {
        }
    }

}
