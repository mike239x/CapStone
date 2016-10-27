package world.particles;

import world.World;
import java.util.Random;

/**
 * Class for moving trap.
 *
 * @author mike239x
 */
public class MovingTrap extends Monster {

    public MovingTrap(int x, int y) {
        super(Type.MOVING_TRAP, x, y);
        picture = '¤';
        //{'¤','Ὠ', '۞', '罗'}
        color = 1; //red
    }

    @Override
    public void chooseNextMove(World w) {
        Random r = new Random();
        this.speedX = r.nextInt(3) - 1;
        this.speedY = r.nextInt(3) - 1;
    }

}
