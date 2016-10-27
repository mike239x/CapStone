package content.wrap;

import content.basics.Interactive;
import com.googlecode.lanterna.terminal.Terminal;
import content.Label;
import exceptions.EscKeyPressed;
import screen.CharScreen;
import screen.CharScreenPart;

/**
 * Allows to add header to some content (and make
 * content out of that).
 *
 * @author mike239x
 */
public class Header implements Interactive {

    Label header;
    Interactive content;

    public Interactive getContent() {
        return content;
    }

    public Header(Label header, Interactive content) {
        this.header = header;
        this.content = content;
    }

    public Header(String headerText, Interactive content) {
        this(new Label(headerText), content);
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen) throws EscKeyPressed {
        CharScreenPart forHeader
                = screen.getPart(0, 0,
                        screen.getWidth(), header.getOptimalHeight());
        header.draw(forHeader);
        CharScreenPart forContent
                = screen.getPart(0, header.getOptimalHeight(),
                        screen.getWidth(), content.getOptimalHeight());
        forContent = screen.intersect(forContent);
        content.captureInput(t, forContent);
    }

    @Override
    public int getOptimalWidth() {
        return Math.max(header.getOptimalWidth(), content.getOptimalWidth());
    }

    @Override
    public int getOptimalHeight() {
        return header.getOptimalHeight() + content.getOptimalHeight();
    }

    @Override
    public void draw(CharScreen screen) {
        CharScreenPart forHeader
                = screen.getPart(0, 0,
                        screen.getWidth(), header.getOptimalHeight());
        header.draw(forHeader);
        CharScreenPart forContent
                = screen.getPart(0, header.getOptimalHeight(),
                        screen.getWidth(), content.getOptimalHeight());
        content.draw(forContent);
    }

    @Override
    public short getMainColor() {
        return content.getMainColor();
    }

    @Override
    public void setMainColor(short color) {
        content.setMainColor(color);
    }

    public short getHeaderColor() {
        return header.getMainColor();
    }

    public void setHeaderColor(short color) {
        header.setMainColor(color);
    }

}
