package game;

import com.googlecode.lanterna.terminal.Terminal;
import content.*;
import exceptions.*;
import java.io.File;
import java.io.IOException;
import screen.*;
import terminal.MyTerminal;

/**
 * Sort of the main screen of the game, also has all menus and so on.
 * Also has main method for launching game (not Game object, but CapStone).
 *
 * @author mike239x
 */
public class GameContainer {

    Terminal terminal;
    CharScreen screen;
    Game game;
    GameOptionPane dialogs;
    MainMenu mainMenu;
    CharScreenPart mainMenuScreen;
    boolean working;

    public GameContainer() {
        //terminal, its screen and game - most basic stuff:
        terminal = new MyTerminal(90, 30);//(100, 30);
        terminal.setCursorVisible(false);
        screen = new TerminalScreen(terminal);
        game = new Game();
        //folder for saving:
        File saveDir = new File(Game.saveDir);
        saveDir.mkdirs();
        //some menus:
        //main menu:
        dialogs = new GameOptionPane(terminal, screen);
        mainMenu = new MainMenu();
        mainMenuScreen = screen.getCenter(mainMenu);
        mainMenuScreen = screen.intersect(mainMenuScreen);
    }

    public void runApp() {
        terminal.enterPrivateMode();
        working = true;
        enterMainMenu(); //here is an infinite loop
        terminal.exitPrivateMode();
    }

    private void setAvailibleMainMenuOptions() {
        mainMenu.setAvailible(MainMenu.Option.CONTINUE, game.canContinue());
        mainMenu.setAvailible(MainMenu.Option.SAVE, game.needToSave());
        boolean canLoadSomething = false;
        File directory = new File(Game.saveDir);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                String name = file.getName();
                if (name.endsWith(Game.saveExt)) {
                    canLoadSomething = true;
                    break;
                }
            }
        }
        mainMenu.setAvailible(MainMenu.Option.LOAD, canLoadSomething);
    }

    //if game is not saved, asks to save it
    private void ifNeededAskToSave() throws EscKeyPressed {
        cls();
        while (game.needToSave() && dialogs.ask("Save the game first?")) {
            try {
                enterSaveDialog();
            } catch (EscKeyPressed e) {
                cls();
            }
        }
    }

    //make new game
    private void enterNewGameDialog() throws EscKeyPressed {
        ifNeededAskToSave();
        cls();
        int level = dialogs.showGameLevelDialog();
        try {
            game.newGame(level);
            continuePlaying();
        } catch (NewGameException ex) {
            dialogs.showError(ex);
        }
    }

    private void enterSaveDialog() throws EscKeyPressed {
        String filename;
        File f;
        while (true) {
            cls();
            filename = dialogs.showSaveDialog();
            try {
                boolean newSlot = false;
                if (filename.equals(OptionPane.NEW_SLOT)) {
                    cls();
                    filename = dialogs.askForSlotName() + Game.saveExt;
                    newSlot = true;
                }
                f = new File(Game.saveDir + filename);
                if (f.exists()) {
                    String overwrite
                            = (newSlot ? "Slot with this name already"
                                    + " exists\n" : "")
                            + "Overwrite this slot?";
                    cls();
                    if (dialogs.ask(overwrite)) {
                        break;
                    }
                } else {
                    break;
                }
            } catch (EscKeyPressed e) {
            }
        }
        try {
            game.save(f);
        } catch (IOException ex) {
            cls();
            dialogs.showError(ex);
        }
    }

    private void enterLoadDialog() throws EscKeyPressed {
        ifNeededAskToSave();
        try {
            cls();
            String filename = dialogs.showLoadDialog();
            File f = new File(Game.saveDir + filename);
            game.load(f);
            cls();
            continuePlaying();
        } catch (NoFilesAvailibleException |
                IOException | LoadException ex) {
            cls();
            dialogs.showError(ex);
        }
    }

    private void enterInfoDialog() throws EscKeyPressed {
        cls();
        GameInfo gi = new GameInfo();
        gi.captureInput(terminal, screen);
    }

    private void continuePlaying() throws EscKeyPressed{
        game.continuePlaying(terminal, screen);
        mainMenu.chooseFirstAvailible();
    }
    
    private void enterMainMenu() {
        while (working) {
            setAvailibleMainMenuOptions();
            cls();
            try {
                mainMenu.captureInput(terminal, mainMenuScreen);
            } catch (EscKeyPressed e) {
                if (game.canContinue()) {
                    try {
                        cls();
                        continuePlaying();
                        continue;
                    } catch (EscKeyPressed ex) {
                        mainMenu.chooseFirstAvailible();
                        continue;
                    }
                } else {
                    continue;
                }
            }
            try {
                switch (mainMenu.getChosenOption()) {
                    case CONTINUE:
                        cls();
                        continuePlaying();
                        break;
                    case NEW_GAME:
                        enterNewGameDialog();
                        break;
                    case SAVE:
                        enterSaveDialog();
                        break;
                    case LOAD:
                        enterLoadDialog();
                        break;
//                    case SETTINGS:
//                        enterSettingsDialog();
//                        break;
                    case INFO:
                        enterInfoDialog();
                        break;
                    case EXIT:
                        ifNeededAskToSave();
                        working = false;
                        break;
                    default:
                        //well, default? that would be sudden :)
                        break;
                }
            } catch (EscKeyPressed ex) {
            }
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer();
        gc.runApp();
    }

    //clean screen
    private void cls() {
        screen.fill(' ');
    }
}
