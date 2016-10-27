package game;

import content.Label;
import screen.Screen;
import screen.CharScreen;
import content.wrap.Bordered;
import world.World;

/**
 * Screen on the game field, which (most of time) contains focus.
 *
 * @author mike239x
 */
public class GameScreen extends Screen {

    CharScreen screen;
    Bordered bWorld;
    World world;
    short borderColor = 5;

    public GameScreen(World world, CharScreen screen) {
        super(0, 0, screen.getWidth(), screen.getHeight());
        this.bWorld = new Bordered(world, '\t');
        this.world = world;
        //border of chars \t, by drawing they are changed to something normal
        this.screen = screen;
    }

    public boolean isUnfocused() {
        int screenFocusX = world.focusX - posX;
        int screenFocusY = world.focusY - posY;
        if (screenFocusX < (width - 2) / 5 || screenFocusX >= width - 2) {
            return true;
        }
        if (screenFocusY < (height - 2) / 5 || screenFocusY >= height - 2) {
            return true;
        }
        screenFocusX = width - 2 - screenFocusX - 1;
        if (screenFocusX < (width - 2) / 5) {
            return true;
        }
        screenFocusY = height - 2 - screenFocusY - 1;
        return screenFocusY < (height - 2) / 5;
    }

    public void focus() {
        this.posX = world.focusX - width / 2 + 1;
        this.posY = world.focusY - height / 2 + 1;
    }

    public void draw() {
        screen.setColor(borderColor);
        screen.fill('\n');
        CharScreen forWorld = screen.getPart(-posX, -posY, bWorld);
        bWorld.draw(forWorld);
        //now go arround the border and replace everything, what is not \n
        // or \t to ▒
        screen.setColor(borderColor);
        char c;
        for (int x = 0; x < width; x++) {
            c = screen.getCharAt(x, 0);
            if (c != '\n' && c != '\t') {
                screen.moveCursor(x, 0);
                screen.putChar('▒');
            }
            c = screen.getCharAt(x, height - 1);
            if (c != '\n' && c != '\t') {
                screen.moveCursor(x, height - 1);
                screen.putChar('▒');
            }
        }
        for (int y = 0; y < height; y++) {
            c = screen.getCharAt(0, y);
            if (c != '\n' && c != '\t') {
                screen.moveCursor(0, y);
                screen.putChar('▒');
            }
            c = screen.getCharAt(width - 1, y);
            if (c != '\n' && c != '\t') {
                screen.moveCursor(width - 1, y);
                screen.putChar('▒');
            }
        }
        replace('\n', ' ');
        replace('\t', '█');
        //draw stats:
        screen.moveCursor(0, 0);
        screen.setColor((short) 1);
        screen.putChar('♥');
        Label health = new Label(" x"
                + world.getPlayer().getHealth());
        CharScreen forHealth = screen.getPart(1, 0, health);
        health.draw(forHealth);
        screen.moveCursor(0, 1);
        screen.setColor((short) 3);
        screen.putChar('ჭ');
        Label keys = new Label(" x"
                + world.getPlayer().getKeys());
        CharScreen forKeys = screen.getPart(1, 1, keys);
        keys.draw(forKeys);
    }

    //replaces all chars a on the screen with chars b
    //saves the color
    private void replace(char a, char b) {
        char c;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                c = screen.getCharAt(x, y);
                if (c == a) {
                    screen.moveCursor(x, y);
                    screen.setColor(screen.getColotAt(x, y));
                    screen.putChar(b);
                }
            }
        }
    }
}
