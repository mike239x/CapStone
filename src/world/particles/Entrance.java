package world.particles;

/**
 * Class for entrance.
 *
 * @author mike239x
 */
public class Entrance extends Particle{

    public Entrance(int x, int y) {
        super(Type.ENTRANCE, x, y);
        picture = '□';
        color = 7; //white
    }
}
