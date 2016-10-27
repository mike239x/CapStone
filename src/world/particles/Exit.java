package world.particles;

/**
 * Class for exit.
 *
 * @author mike239x
 */
public class Exit extends Particle {

    public Exit(int x, int y) {
        super(Type.EXIT, x, y);
        picture = '۝';
        color = 7; //white
    }

}
