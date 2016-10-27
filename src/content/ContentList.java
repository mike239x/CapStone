package content;

import content.basics.Interactive;
import com.googlecode.lanterna.terminal.Terminal;
import screen.CharScreen;
import screen.CharScreenPart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * List of content; can be drawn vertically/horizontally, depending on
 * disposition; on drawing gives each content in the list screen of the same
 * size; automatically changes the preferred size on adding/removing elements.
 *
 * @author mike239x
 */
public class ContentList extends ArrayList<Interactive>
        implements Interactive {

    protected int itemMaxWidth;
    protected int itemMaxHeight;

    /**
     * Type of the content disposition.
     */
    public enum Dispose {

        VERTICALLY, HORISONTALLY

    }

    protected final Dispose dispose;

    /**
     * @return true if this list is vertically disposed, false otherwise
     */
    public boolean isVerticallyDisposed() {
        return dispose == Dispose.VERTICALLY;
    }

    /**
     * @return true if this list is horizontally disposed, false otherwise
     */
    public boolean isHorizontallyDisposed() {
        return dispose == Dispose.HORISONTALLY;
    }

//--------constructors--------------
    /**
     * Create content array.
     *
     * @param contentList all the content
     * @param dispose dispose, can be vertical or horizontal
     */
    public ContentList(Interactive[] contentList, Dispose dispose) {
        super();
        this.dispose = dispose;
        super.addAll(Arrays.asList(contentList));
        chooseMaxSizes();
    }

    /**
     * Create content array.
     *
     * @param dispose dispose, can be vertical or horizontal
     */
    public ContentList(Dispose dispose) {
        super();
        this.dispose = dispose;
    }

    /**
     * Create content array.
     *
     * @param c initial content list
     * @param dispose dispose, can be vertical or horizontal
     */
    public ContentList(Collection<? extends Interactive> c, Dispose dispose) {
        super(c);
        this.dispose = dispose;
        chooseMaxSizes();
    }

//----------add/remove-functions----------
    @Override
    public Interactive set(int index, Interactive element) {
        Interactive result = super.set(index, element);
        chooseMaxSizes();
        return result;
    }

    @Override
    public boolean add(Interactive e) {
        boolean result = super.add(e);
        chooseMaxSizes();
        return result;
    }

    @Override
    public void add(int index, Interactive element) {
        super.add(index, element);
        chooseMaxSizes();
    }

    @Override
    public boolean addAll(Collection<? extends Interactive> c) {
        boolean result = super.addAll(c);
        chooseMaxSizes();
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Interactive> c) {
        boolean result = super.addAll(index, c);
        chooseMaxSizes();
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        chooseMaxSizes();
        return result;
    }

    @Override
    public Interactive remove(int index) {
        Interactive result = super.remove(index);
        chooseMaxSizes();
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = super.removeAll(c);
        chooseMaxSizes();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        chooseMaxSizes();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = super.retainAll(c);
        chooseMaxSizes();
        return result;
    }

//------changing-preferred-size---------------
    private void chooseMaxSizes() {
        int w = 0;
        int h = 0;
        for (Interactive c : this) {
            if (w < c.getOptimalWidth()) {
                w = c.getOptimalWidth();
            }
            if (h < c.getOptimalHeight()) {
                h = c.getOptimalHeight();
            }
        }
        itemMaxHeight = h;
        itemMaxWidth = w;
    }

//------------implementing-Interactive-interface------------------
    @Override
    public void captureInput(Terminal t, CharScreen screen) {
    }

    @Override
    public int getOptimalWidth() {
        if (dispose == Dispose.HORISONTALLY) {
            return itemMaxWidth * this.size();
        }
        return itemMaxWidth;
    }

    @Override
    public int getOptimalHeight() {
        if (dispose == Dispose.VERTICALLY) {
            return itemMaxHeight * this.size();
        }
        return itemMaxHeight;
    }

    @Override
    public void draw(CharScreen screen) {
        int w, h;
        int s = this.size();
        if (s == 0) {
            return;
        }
        if (dispose == Dispose.VERTICALLY) {
            w = screen.getWidth();
            h = screen.getHeight() / s;
            for (int i = 0; i < s; i++) {
                CharScreenPart part = screen.getPart(0, i * h, w, h);
                this.get(i).draw(part);
            }
        } else { //dispose == Dispose.HORISONTALLY
            w = screen.getWidth() / s;
            h = screen.getHeight();
            for (int i = 0; i < s; i++) {
                CharScreenPart part = screen.getPart(i * w, 0, w, h);
                this.get(i).draw(part);
            }
        }
    }

    /**
     * Get the screen there the chosen item will be drawn.
     *
     * @param index index of chosen item
     * @param screen the whole screen to draw on
     * @return screen for item
     */
    public CharScreen getItemSubScreen(int index, CharScreen screen) {
        int w, h;
        int s = this.size();
        if (dispose == Dispose.VERTICALLY) {
            w = screen.getWidth();
            h = screen.getHeight() / s;
            CharScreenPart part = screen.getPart(0, index * h, w, h);
            return part;
        } else { //dispose == Dispose.HORISONTALLY
            w = screen.getWidth() / s;
            h = screen.getHeight();
            CharScreenPart part = screen.getPart(index * w, 0, w, h);
            return part;
        }
    }

    @Override
    public short getMainColor() {
        //well, where is no main color, so who cares?
        return 0;
    }

    @Override
    public void setMainColor(short color) {
        //well, where is no main color, so who cares?
    }

}
