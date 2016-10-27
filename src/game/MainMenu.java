package game;

import content.ContentList;
import content.Label;
import content.OptionsList;

/**
 * Main menu.
 *
 * @author mike239x
 */
public class MainMenu extends OptionsList {

    // in menu options
    protected enum Option {

        CONTINUE(" CONTUNUE "),
        NEW_GAME(" NEW GAME "),
        SAVE("   SAVE   "),
        LOAD("   LOAD   "),
        //SETTINGS(" SETTINGS "),
        INFO("   INFO   "),
        EXIT("   EXIT   "),;

        private final String strRepr; //string representation

        String getStrRepr() {
            return strRepr;
        }

        static Label[] getLabels() {
            int length = Option.values().length;
            Label[] options = new Label[length];
            for (int i = 0; i < length; i++) {
                options[i] = new Label(Option.values()[i].getStrRepr());
            }
            return options;
        }

        static Option get(int index) {
            return Option.values()[index];
        }

        private Option(String str) {
            strRepr = str;
        }
    }

    public MainMenu() {
        super(Option.getLabels(), ContentList.Dispose.VERTICALLY);
    }

    public void setAvailible(Option option, boolean a) {
        for (int index = 0; index < Option.values().length; index++) {
            if (option == Option.get(index)) {
                super.setAvailible(index, a);
            }
        }
    }

    Option getChosenOption() {
        if (getChosen() == -1) {
            return null;
        }
        return Option.values()[getChosen()];
    }

}
