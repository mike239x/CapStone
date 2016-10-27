package game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import content.basics.Interactive;
import content.ContentDeck;
import content.Label;
import exceptions.EscKeyPressed;
import screen.CharScreen;

/**
 * Info.
 *
 * @author mike239x
 */
public class GameInfo implements Interactive {

    private static final Label[] pagesTexts = {
        new Label(
        "    GENERAL INFO ──>\n"
        + "\n"
        + "This is CapStone version 1.1,\n"
        + "the game full of dungeons,\n"
        + "monsters, treasures and other\n"
        + "unreal stuff.\n"
        + "\n"
        + "          © mike239x, year 2014"),
        new Label(
        "<── GAME MODES ──>\n"
        + "\n"
        + "In this release only one game mode\n"
        + "is available: door-key-door mode.\n"
        + "In this mode you have to take\n"
        + "a key and get out of dungeon,\n"
        + "trying to avoid all kind of\n"
        + "monsters and traps."),
        new Label(
        "<── PHYSICS ──>\n"
        + "\n"
        + "To interact with an object you\n"
        + "have to stay on the same cell\n"
        + "with object. If you already stay\n"
        + "there, interaction can't be escaped."),
        new Label(
        "<── CONTROLS ──>\n"
        + "\n"
        + "Use arrows, enter and esc to navigate\n"
        + "throw menus. After the beginning\n"
        + "of new map you may choose the entrance\n"
        + "you want to start from, using\n"
        + "left/right arrows (enter to confirm).\n"
        + "After that you can move your character\n"
        + "with arrow keys (enter to stay still)."),
        new Label(
        "<── SAVES    \n"
        + "\n"
        + "All saves can be found in \"saves\"\n"
        + "folder. You may freely do with your\n"
        + "saves whatever you want, but don't\n"
        + "edit them if you aren't sure\n"
        + "what are you doing.")
    };
    public static final ContentDeck pages = new ContentDeck(pagesTexts);

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
            } else if (kind.equals(Key.Kind.ArrowRight)) {
                pages.chooseNext();
            } else if (kind.equals(Key.Kind.ArrowLeft)) {
                pages.choosePrev();
            }
        }
    }

    @Override
    public int getOptimalWidth() {
        return pages.getOptimalWidth();
    }

    @Override
    public int getOptimalHeight() {
        return pages.getOptimalHeight();
    }

    @Override
    public void draw(CharScreen screen) {
        screen.fill(' ');
        pages.draw(screen);
    }

    @Override
    public short getMainColor() {
        return pages.getMainColor();
    }

    @Override
    public void setMainColor(short color) {
        pages.setMainColor(color);
    }

}
