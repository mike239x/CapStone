package world.particles;

/**
 * Class for key.
 *
 * @author mike239x
 */
public class Key extends Particle {

    public Key(int x, int y) {
        super(Type.KEY, x, y);
        picture = 'ไ';
        color = 11; //yellow
    }

}
