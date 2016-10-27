package world.particles;

import exceptions.LoadException;
import exceptions.PlayerIsDead;
import java.util.Properties;
import screen.CharScreen;

/**
 * Class for person.
 *
 * @author mike239x
 */
public class Person extends Particle {

    private int health;
    private int keys;
    private boolean placed;
    private static final int MAX_HEALTH = 3;

    //well, creates dead non-placed person
    public Person() {
        super(Type.PERSON, -1, -1);
        this.health = 0;
        this.keys = 0;
        picture = 'i';
        color = 16 + 5 * 36 + 3 * 6; //orange
        placed = false;
    }

    @Override
    public void draw(CharScreen screen) {
        if (placed) {
            super.draw(screen);
        }
    }

    public boolean isPlaced() {
        return placed;
    }

    public void place(int x, int y) {
        this.x = x;
        this.y = y;
        placed = true;
    }

    public void save(Properties prop) {
        prop.setProperty("playerX", "" + x);
        prop.setProperty("playerY", "" + y);
        prop.setProperty("playerHealth", "" + health);
        prop.setProperty("playerKeys", "" + keys);
    }

    public void load(Properties prop)
            throws LoadException {
        try {
            x = Integer.parseInt(prop.getProperty("playerX"));
            y = Integer.parseInt(prop.getProperty("playerY"));
            health = Integer.parseInt(prop.getProperty("playerHealth"));
            keys = Integer.parseInt(prop.getProperty("playerKeys"));
            placed = true;
        } catch (NumberFormatException ex) {
            health = MAX_HEALTH;
            keys = 0;
            placed = false;
        }
    }

    public void getKey() {
        keys++;
    }

    public void getDamage() throws PlayerIsDead {
        health--;
        if (isDead()) {
            throw new PlayerIsDead();
        }
    }

    public boolean isDead() {
        return health < 1;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public int getKeys() {
        return keys;
    }

    public boolean hasKey() {
        return keys > 0;
    }
}
