package content;

import content.wrap.Header;

/**
 * Class for dialog, it's list of options + header (some kind of message).
 *
 * @author mike239x
 */
public class Dialog extends Header {

    public Dialog(String msg, Label[] options, ContentList.Dispose dispose) {
        super(msg, new OptionsList(options, dispose));
    }

    public Dialog(String msg, String[] options, ContentList.Dispose dispose) {
        super(msg, new OptionsList(options, dispose));
    }

    public Dialog(String msg, String[] options) {
        super(msg, new OptionsList(options, ContentList.Dispose.HORISONTALLY));
    }

    public int getChosen() {
        return ((OptionsList) getContent()).getChosen();
    }

}
