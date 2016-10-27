package game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import content.OptionPane;
import exceptions.*;
import generator.Generator;
import world.World;
import java.io.*;
import java.util.Properties;
import screen.CharScreen;

/**
 * Class for the game. New game/save/load, handle input - this all happens here.
 *
 * @author mike239x
 */
public class Game extends World {

    protected static final String saveExt = ".save";
    protected static final String saveDir = "saves/";
    protected static final int levelNameMaxLength = 40;
    private static final String[][] newGameSettings
            = { //w     h    in  out   keys trap mtrap density
                {"60", "20", "3", "3", "3", "5", "2", "2.0"},
                {"90", "30", "4", "3", "5", "15", "6", "1.9"},
                {"120", "40", "5", "3", "8", "50", "20", "1.7"},
                {"180", "60", "3", "3", "3", "80", "80", "1.0"}
            };

    boolean saved;
    boolean completed;

    public Game() {
        super();
        saved = true;
    }

    protected void continuePlaying(Terminal terminal, CharScreen screen)
            throws EscKeyPressed {
        captureInput(terminal, screen);
    }

    protected boolean canContinue() {
        return getPlayer().isAlive() && !completed;
    }

    protected void newGame(int hardness) throws NewGameException {
        if (hardness < 0) {
            hardness = 0;
        }
        if (hardness >= newGameSettings.length) {
            hardness = newGameSettings.length - 1;
        }
        Properties prop = new Properties();
        prop.setProperty("Width", newGameSettings[hardness][0]);
        prop.setProperty("Height", newGameSettings[hardness][1]);
        prop.setProperty("NrIn", newGameSettings[hardness][2]);
        prop.setProperty("NrOut", newGameSettings[hardness][3]);
        prop.setProperty("Keys", newGameSettings[hardness][4]);
        prop.setProperty("StaticTraps", newGameSettings[hardness][5]);
        prop.setProperty("DynamicTraps", newGameSettings[hardness][6]);
        prop.setProperty("Density", newGameSettings[hardness][7]);
        try {
            Properties newMap = Generator.generate(prop);
            super.load(newMap);
        } catch (Exception e) {
            throw new NewGameException();
        }
        saved = false;
        completed = false;
    }

    protected void save(File file) throws IOException {
        Properties prop = new Properties();
        super.save(prop);
        OutputStream out = new FileOutputStream(file);
        prop.store(out, null);
        out.close();
        saved = true;
    }

    protected boolean needToSave() {
        return !saved && !completed;
    }

    protected void load(File file)
            throws FileNotFoundException, IOException, LoadException {
        Properties prop = new Properties();
        InputStream in = new FileInputStream(file);
        prop.load(in);
        in.close();
        super.load(prop);
        saved = true;
        completed = false;
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen)
            throws EscKeyPressed {
        GameScreen ws = new GameScreen(this, screen);
        ws.focus();
        while (true) {
            if (ws.isUnfocused()) {
                ws.focus();
            }
            ws.draw();
            screen.flush(); //yeah, flush this!
            Key key = null;
            while (key == null) {
                key = t.readInput();
            }
            Key.Kind kind = key.getKind();
            if (kind.equals(Key.Kind.Escape)) {
                throw new EscKeyPressed();
            } else {
                if (getPlayer().isPlaced()) {
                    int dx = 0;
                    int dy = 0;
                    if (kind.equals(Key.Kind.Enter)) {
                        //stay still;
                    } else if (kind.equals(Key.Kind.ArrowDown)) {
                        dy = 1;
                    } else if (kind.equals(Key.Kind.ArrowRight)) {
                        dx = 1;
                    } else if (kind.equals(Key.Kind.ArrowUp)) {
                        dy = -1;
                    } else if (kind.equals(Key.Kind.ArrowLeft)) {
                        dx = -1;
                    } else {
                        continue; //unknown symbol, move on
                    }
                    getPlayer().setSpeed(dx, dy);
                    try {
                        moveAll();
                        focusOnPlayer();
                        saved = false;
                    } catch (PlayerIsDead ex) {
                        OptionPane.showMessageDialog(" You died! ", t, screen);
                        completed = true;
                        return;
                    } catch (LevelCompleted ex) {
                        OptionPane.showMessageDialog(" Level completed! ", t, screen);
                        completed = true;
                        return;
                    }
                } else {
                    if (kind.equals(Key.Kind.Enter)) {
                        //place player
                        getPlayer().place(focusX, focusY);
                    } else if (kind.equals(Key.Kind.ArrowRight)) {
                        showNextEntrance();
                    } else if (kind.equals(Key.Kind.ArrowLeft)) {
                        showPrevEntrance();
                    }
                }
            }
        }
    }
}
