package world;

import content.basics.Drawable;
import screen.CharScreen;
import world.particles.*;
import exceptions.LoadException;
import exceptions.UnknownTypeException;
import java.util.*;

/**
 * Map with particles (without player).
 *
 * @author mike239x
 */
public class Map implements Drawable {

    protected Particle[][] map;

    int width, height;
    private short borderColor;
    final StyleShema style;

    //empty map
    public Map() {
        map = null;
        width = height = 0;
        borderColor = 5;
        style = new StyleShema(null, (short)2, null, null);
    }

    /**
     * Loads the map from the Properties-Object given.
     *
     * @param p data
     * @throws exceptions.LoadException in case the given Properties-Object has
     * wrong data.
     */
    public void load(Properties p) throws LoadException {
        try {
            String s = p.getProperty("Width");
            width = Integer.parseInt(s);
            s = p.getProperty("Height");
            height = Integer.parseInt(s);
            map = new Particle[width][height];
            style.load(p);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    s = p.getProperty(x + "," + y);
                    if (s == null) {
                        continue;
                    }
                    int type = Integer.parseInt(s);
                    try {
                        map[x][y] = Particle.classifyParticle(type, x, y);
                        style.apply(map[x][y]);
                    } catch (UnknownTypeException ex) {
                        map[x][y] = null;
                        //this allows to use modes from other people
                        //(more or less)
                    }
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("number format!");
            throw new LoadException();
        }
    }

    /**
     * Save the map to the Properties-Object given.
     *
     * @param p place for data
     */
    void save(Properties p) {
        p.setProperty("Width", "" + width);
        p.setProperty("Height", "" + height);
        style.save(p);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y] != null) {
                    p.setProperty(x + "," + y,
                            "" + map[x][y].getType().getIndex());
                }
            }
        }
    }

    /**
     * @return width of the maze
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the maze
     */
    public int getHeight() {
        return height;
    }

    /**
     * Check if the given point lays inside the map.
     *
     * @param x X-coordinate of given point
     * @param y Y-coordinate of given point
     * @return whether or not the given point is inside the map
     */
    public boolean contains(int x, int y) {
        return (x > -1 && x < width && y > -1 && y < height);
    }

    @Override
    public int getOptimalWidth() {
        return width;
    }

    @Override
    public int getOptimalHeight() {
        return height;
    }

    @Override
    public void draw(CharScreen screen) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Particle p = map[x][y];
                if (p != null) {
                    p.draw(screen);
                } else {
                    screen.moveCursor(x, y);
                    screen.putChar(' ');
                }
            }
        }
    }

    @Override
    public short getMainColor() {
        return borderColor;
    }

    @Override
    public void setMainColor(short color) {
        borderColor = color;
    }

}
