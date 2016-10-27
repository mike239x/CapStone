package content;

import content.wrap.Bordered;
import content.basics.Interactive;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import content.ContentList.Dispose;
import exceptions.EscKeyPressed;
import screen.CharScreen;
import java.util.Arrays;

/**
 * Class for list of options. Can have both vertical and horizontal dispose.
 * Allows to chose content with arrows + enter. After enter lets
 * the content capture the input. On Escape-key throws EskKeyPressed exception.
 *
 * @author mike239x
 */
public class OptionsList implements Interactive {

    private final ContentList options;
    private boolean[] available;
    private short[] startColors;
    private int chosen;
    private short highlightColor;
    private short unavailibleContentColor;

    /**
     * Create new option list with given options.
     *
     * @param options list inner content
     * @param dispose dispose of the list
     */
    public OptionsList(Interactive[] options, Dispose dispose) {
        this.options = new ContentList(dispose);
        for (Interactive option : options) {
            if (option != null) {
                Bordered bordered = new Bordered(option);
                this.options.add(bordered);
            }
        }
        initTheRest();
    }

    public OptionsList(String[] options, Dispose dispose) {
        this.options = new ContentList(dispose);
        for (String option : options) {
            if (option != null) {
                Bordered bordered = new Bordered(new Label(option));
                this.options.add(bordered);
            }
        }
        initTheRest();
    }

    private void initTheRest() {
        available = new boolean[options.size()];
        Arrays.fill(available, true);
        startColors = new short[options.size()];
        for (int i = 0; i < startColors.length; i++) {
            startColors[i] = this.options.get(i).getMainColor();
        }
        highlightColor = 3; //orange
        unavailibleContentColor = 4; //cyan
        chooseFirstAvailible();
    }

    private void unmarkChosen() {
        if (chosen != -1) {
            ((Bordered) options.get(chosen)).setDefaultBorderColor();
        }
    }

    private void markChosen() {
        if (chosen != -1) {
            ((Bordered) options.get(chosen)).setBorderColor(highlightColor);
        }
    }

    public void setAvailible(int index, boolean a) {
        available[index] = a;
        if (a) {
            options.get(index).setMainColor(startColors[index]);
            ((Bordered) options.get(index))
                    .setBorderColor(startColors[index]);
        } else {
            options.get(index).setMainColor(unavailibleContentColor);
            ((Bordered) options.get(index))
                    .setBorderColor(unavailibleContentColor);
        }
        if (chosen == -1 || !available[chosen]) {
            chooseFirstAvailible();
        }
    }

    private void chooseNext() {
        if (chosen == -1) {
            chooseFirstAvailible();
            return;
        }
        chosen++;
        for (; chosen < available.length; chosen++) {
            if (available[chosen]) {
                return;
            }
        }
        chooseFirstAvailible();
    }

    private void choosePrev() {
        if (chosen == -1) {
            chooseLastAvailible();
            return;
        }
        chosen--;
        for (; chosen >= 0; chosen--) {
            if (available[chosen]) {
                return;
            }
        }
        chooseLastAvailible();
    }

    public void chooseFirstAvailible() {
        for (int i = 0; i < available.length; i++) {
            if (available[i]) {
                chosen = i;
                return;
            }
        }
        chosen = -1;
    }

    public void chooseLastAvailible() {
        for (int i = available.length - 1; i >= 0; i--) {
            if (available[i]) {
                chosen = i;
                return;
            }
        }
        chosen = -1;
    }

//    /**
//     * 
//     * @param t
//     * @param screen
//     * @return
//     * @throws EscKeyPressed
//     */
//    public int letUserChoose(Terminal t, CharScreen screen)
//            throws EscKeyPressed {
//        captureInput(t, screen);
//        return chosen;
//    }
    public int getChosen() {
        return chosen;
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen)
            throws EscKeyPressed {
        if (chosen == -1){
            chooseFirstAvailible();
        }
        while (true) {
            markChosen();
            this.draw(screen);
            screen.flush(); //yeah, flush this!
            Key key = null;
            while (key == null) {
                key = t.readInput();
            }
            Key.Kind kind = key.getKind();
            if (kind.equals(Key.Kind.Escape)) {
                unmarkChosen();
                throw new EscKeyPressed();
            } else if (kind.equals(Key.Kind.Enter)) {
                if (chosen != -1) {
                    Interactive choice = options.get(chosen);
                    choice.captureInput(t,
                            options.getItemSubScreen(chosen, screen));
                }
                unmarkChosen();
                return;
            } else if (kind.equals(Key.Kind.ArrowDown)) {
                if (options.isVerticallyDisposed()) {
                    unmarkChosen();
                    chooseNext();
                }
            } else if (kind.equals(Key.Kind.ArrowRight)) {
                if (options.isHorizontallyDisposed()) {
                    unmarkChosen();
                    chooseNext();
                }
            } else if (kind.equals(Key.Kind.ArrowUp)) {
                if (options.isVerticallyDisposed()) {
                    unmarkChosen();
                    choosePrev();
                }
            } else if (kind.equals(Key.Kind.ArrowLeft)) {
                if (options.isHorizontallyDisposed()) {
                    unmarkChosen();
                    choosePrev();
                }
            }
        }
    }

    @Override
    public int getOptimalWidth() {
        return options.getOptimalWidth();
    }

    @Override
    public int getOptimalHeight() {
        return options.getOptimalHeight();
    }

    @Override
    public void draw(CharScreen screen) {
        ContentList toDraw;
        if (options.isVerticallyDisposed()) {
            if (getOptimalHeight() > screen.getHeight()) {
                if (chosen != -1) {
                    int maxItemsOnScreen = screen.getHeight()
                            / options.itemMaxHeight;
                    toDraw = new ContentList(options.dispose);
                    for (int i = 0; i < maxItemsOnScreen; i++) {
                        toDraw.add(options.get((chosen + i) % options.size()));
                    }
                } else {
                    toDraw = options;
                }
            } else {
                toDraw = options;
            }
        } else {
            if (getOptimalWidth() > screen.getWidth()) {
                if (chosen != -1) {
                    int maxItemsOnScreen = screen.getWidth()
                            / options.itemMaxWidth;
                    toDraw = new ContentList(options.dispose);
                    for (int i = 0; i < maxItemsOnScreen; i++) {
                        toDraw.add(options.get((chosen + i) % options.size()));
                    }
                } else {
                    toDraw = options;
                }
            } else {
                toDraw = options;
            }
        }
        toDraw.draw(screen);
    }

    @Override
    public short getMainColor() {
        return highlightColor;
    }

    @Override
    public void setMainColor(short color) {
        highlightColor = color;
    }

}
