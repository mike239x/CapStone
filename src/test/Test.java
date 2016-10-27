package test;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import screen.*;

/**
 * Class used for testing some stuff, is not used in game.
 * At current moment it shows colors and thier codes 
 * (for some of them).
 * 
 * @author mike239x
 */
public class Test {

    public static Terminal terminal;
    public static CharScreen screen;
    public static int w,h;

    public static void main(String[] args) {
        terminalTest();
    }

    public static void terminalTest() {
        terminal = new SwingTerminal(110, 30);
        terminal.setCursorVisible(false);
        terminal.enterPrivateMode();
        screen = new TerminalScreen(terminal);
        w = screen.getWidth();
        h = screen.getHeight();
        test1();
//        test2();
        
        while (true) {
            Key k = terminal.readInput();
            if (k == null) {
                continue;
            }
            char got = k.getCharacter();
            if (got == ' ') {
                break;
            }
        }
        terminal.exitPrivateMode();
    }

    public static void test2() {
        screen.moveCursor(0, 0);
        screen.setColor((short)7);
        for (int j = 0; j < h; j++) {
            screen.moveCursor(0, j);
            for (short i = 0; i < w; i++) {
                char toPut = (char) (w*j+i);
                screen.putChar(toPut);
            }
        }
        screen.flush();
    }
    
    public static void test1() {
        CharScreenPart a = new CharScreenPart(screen, 1, 5, 16, 1);
        CharScreenPart b = new CharScreenPart(screen, 1, 7, 36*3, 6);
        CharScreenPart c = new CharScreenPart(screen, 1, 14, 16, 1);
        screen.moveCursor(0, 0);
        for (short i = 0; i < 256; i++) {
            screen.setColor(i);
            screen.putChar('█');
        }
        a.moveCursor(0, 0);
        for (short i = 0; i < 16; i++) {
            a.setColor(i);
            char toPut = (char) ('0'+(char)(i%10));
            a.putChar(toPut);
        }
        for (short j = 0; j < 6; j++) {
            b.moveCursor(0, j);
            for (short i = (short) (16 + j * 36); i < 16 + (j + 1) * 36; i++) {
                b.setColor(i);
                char toPut = (char) ('0'+(char)(i/100));
                b.putChar(toPut);
                toPut = (char) ('0'+(char)((i/10)%10));
                b.putChar(toPut);
                toPut = (char) ('0'+(char)(i%10));
                b.putChar(toPut);
            }
        }
        c.moveCursor(0, 0);
        for (short i = 16 + 6 * 6 * 6; i < 32 + 6 * 6 * 6; i++) {
            c.setColor(i);
            char toPut = (char) ('0'+(char)(i%10));
            c.putChar(toPut);
        }
        screen.flush();
    }
}
