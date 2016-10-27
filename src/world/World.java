package world;

import com.googlecode.lanterna.terminal.Terminal;
import content.basics.Interactive;
import exceptions.*;
import java.util.*;
import screen.*;
import world.particles.*;
import world.particles.Person;

/**
 * World with physics. It has focus - place which should be shown in game.
 * Saves/loads using property-objects.
 *
 * @author mike239x
 */
public class World extends Map implements Interactive {

    public int focusX, focusY;
    Person player;
    ArrayList<Monster> monsters;
    EntranceChooser entranceChooser;

    public Person getPlayer() {
        return player;
    }

    public World() {
        super();
        player = new Person(); //initially dead
        monsters = new ArrayList<>();
    }

    @Override
    public void save(Properties prop) {
        super.save(prop);
        if (player.isPlaced()) {
            player.save(prop);
        }
    }

    @Override
    public void load(Properties prop)
            throws LoadException {
        try {
            super.load(prop);
            player.load(prop);
            style.apply(player);
            monsters.clear();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Particle p = map[x][y];
                    if (p != null && (p instanceof Monster)) {
                        monsters.add((Monster) p);
                    }
                }
            }
            if (!player.isPlaced()) {
                entranceChooser = new EntranceChooser();
                entranceChooser.focusOnFirstEntrance();
            } else {
                focusOnPlayer();
            }
        } catch (LoadException ex) {
            throw ex;
        }
    }

    public void moveAll() throws PlayerIsDead, LevelCompleted {
        //move player
        int newX = player.getX() + player.getSpeedX();
        int newY = player.getY() + player.getSpeedY();
        if (contains(newX, newY)) {
            Particle p = map[newX][newY];
            if (p == null) {
                player.move();
            } else if (!(p instanceof Wall)) {
                player.move();
                if (p instanceof Key) {
                    player.getKey();
                    map[p.getX()][p.getY()] = null;
                } else if (p instanceof Monster) {
                    player.getDamage();
                    monsters.remove((Monster) p);
                    map[p.getX()][p.getY()] = null;
                } else if (p instanceof Exit) {
                    if (player.hasKey()) {
                        throw new LevelCompleted();
                    }
                }
            }
        }
        //move monsters
        ArrayList<Monster> monst = new ArrayList<>(monsters);
        Collections.shuffle(monst);
        for (Monster m : monst) {
            m.chooseNextMove(this);
            moveParticle(m);
            if (m.getX() == player.getX()
                    && m.getY() == player.getY()) {
                player.getDamage();
                map[m.getX()][m.getY()] = null;
                monsters.remove(m);
            }
        }
    }
    
    public void focusOnPlayer() {
        focusX = player.getX();
        focusY = player.getY();
    }

    private void moveParticle(Particle p) {
        if (p == null) {
            return;
        }
        if (map[p.getX()][p.getY()] != p) {
            return;
        }
        int newX = p.getX() + p.getSpeedX();
        int newY = p.getY() + p.getSpeedY();
        if (contains(newX, newY)) {
            if (map[newX][newY] == null) {
                map[p.getX()][p.getY()] = null;
                map[newX][newY] = p;
                p.move();
            }
        }
    }

    @Override
    public void captureInput(Terminal t, CharScreen screen) throws EscKeyPressed {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void draw(CharScreen screen) {
        super.draw(screen);
        //TODO: highlight focus
        if (screen.contains(focusX, focusY)) {
            char c = screen.getCharAt(focusX, focusY);
            screen.setColor(style.focusColor);
            screen.moveCursor(focusX, focusY);
            if (c != ' ') {
                screen.putChar(c);
            } else {
                screen.putChar('˖');
            }
        }
        player.draw(screen);
    }

    public void showNextEntrance() {
        entranceChooser.focusOnNextEntrance();
    }

    public void showPrevEntrance() {
        entranceChooser.focusOnPrevEntrance();
    }

    private class EntranceChooser {
        ArrayList<Entrance> entrances;
        int chosen = 0;

        public EntranceChooser() {
            entrances = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Particle p = map[x][y];
                    if (p != null && (p instanceof Entrance)) {
                        entrances.add((Entrance) p);
                    }
                }
            }
            Collections.shuffle(entrances);
        }

        public void focusOnFirstEntrance() {
            focus(entrances.get(0));
        }

        public void focusOnNextEntrance() {
            chosen++;
            if (chosen == entrances.size()) {
                chosen = 0;
            }
            focus(entrances.get(chosen));
        }

        public void focusOnPrevEntrance() {
            chosen--;
            if (chosen == -1) {
                chosen = entrances.size() - 1;
            }
            focus(entrances.get(chosen));
        }

        private void focus(Entrance e) {
            focusX = e.getX();
            focusY = e.getY();
        }
    }
}
