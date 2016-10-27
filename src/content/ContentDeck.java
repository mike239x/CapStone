/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package content;

import content.basics.Interactive;
import java.util.Collection;
import screen.CharScreen;
import screen.CharScreenPart;

/**
 * Deck of content. Draws only the chosen content, but takes place so it can fit
 * each one contained.
 *
 * @author mike239x
 */
public class ContentDeck extends ContentList {

    int chosen;
    
    public int chosen() {
        return chosen;
    }
    public void choose(int choice) {
        this.chosen = choice;
    }
    public void chooseNext() {
        if (chosen < this.size()-1) {
            chosen++;
        }
    }
    public void choosePrev() {
        if (chosen > 0) {
            chosen--;
        }
    }
    
    public Interactive getChosen() {
        int s = this.size();
        if (s == 0) {
            return null;
        }
        if (chosen < 0) {
            return this.get(0);
        }
        if (chosen >= s) {
            return this.get(s-1);
        }
        return this.get(chosen);
    }
    
    public ContentDeck(Interactive[] contentList) {
        super(contentList, Dispose.VERTICALLY);
    }

    public ContentDeck(Collection<? extends Interactive> c) {
        super(c, Dispose.VERTICALLY);
    }

    public ContentDeck() {
        super(Dispose.VERTICALLY);
    }

    //------------reimplementing-Drawable-interface------------------
    @Override
    public int getOptimalWidth() {
        return itemMaxWidth;
    }

    @Override
    public int getOptimalHeight() {
        return itemMaxHeight;
    }

    @Override
    public void draw(CharScreen screen) {
        Interactive chosenContent = getChosen();
        if (chosenContent == null) {
            return;
        }
        chosenContent.draw(screen);
    }

    
}
