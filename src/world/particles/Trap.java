package world.particles;

/**
 * Class for trap.
 *
 * @author mike239x
 */
public class Trap extends Monster {

    public Trap(int x, int y) {
        super(Type.TRAP, x, y);
        picture = '͏';
        color = 52; //dark red
    }

}
