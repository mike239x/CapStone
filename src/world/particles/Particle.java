package world.particles;

import content.basics.Drawable;
import exceptions.UnknownTypeException;
import screen.CharScreen;
import world.World;

/**
 * Particle, is represented by colored char. Has also coordinates and speed.
 *
 * @author mike239x
 */
public abstract class Particle implements Drawable {

    //Possible particle types.
    public enum Type {

        WALL(0), ENTRANCE(1), EXIT(2),
        TRAP(3), MOVING_TRAP(4), KEY(5), PERSON(6);

        private final int index;

        private Type(int i) {
            index = i;
        }

        //return in-game index of this type
        public int getIndex() {
            return index;
        }
    }

    private final Type type;

    protected int x, y;
    protected int speedX, speedY;

    //Char representing this particle.
    protected char picture;

    protected short color;

    public Particle(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Move the particle at given vector.
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    //Move the particle at speed vector.
    public void move() {
        x += speedX;
        y += speedY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    //allows, for example, to make monsters moving towards the player.
    public void chooseNextMove(World w) {
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getOptimalHeight() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getOptimalWidth() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void draw(CharScreen screen) {
        screen.moveCursor(x, y);
        screen.setColor(color);
        screen.putChar(picture);
    }

    @Override
    public short getMainColor() {
        return color;
    }

    @Override
    public void setMainColor(short color) {
        this.color = color;
    }
    
    public void setPicture(char pic) {
        this.picture = pic;
    }

    /**
     * Create particle with given position and type given as index.
     *
     * @param type type of particle
     * @param x X-coordinate of particle position
     * @param y Y-coordinate of particle position
     * @return new particle with given type and position
     * @throws UnknownTypeException
     */
    public static Particle classifyParticle(int type, int x, int y) throws UnknownTypeException {
        switch (type) {
            case 0:
                return new Wall(x, y);
            case 1:
                return new Entrance(x, y);
            case 2:
                return new Exit(x, y);
            case 3:
                return new Trap(x, y);
            case 4:
                return new MovingTrap(x, y);
            case 5:
                return new Key(x, y);
            default:
                throw new UnknownTypeException();
        }
    }
}
