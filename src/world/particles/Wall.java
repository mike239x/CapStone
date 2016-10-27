package world.particles;

/**
 * Class for wall.
 *
 * @author mike239x
 */
public class Wall extends Particle {

    public Wall(int x, int y) {
        super(Type.WALL, x, y);
        picture = '█';
        color = 7; //white
    }

}
