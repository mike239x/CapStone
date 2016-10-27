package world.particles;

/**
 * Monster, deal damage to player.
 * 
 * @author mike239x
 */
public abstract class Monster extends Particle{
    public Monster(Type type, int x, int y) {
        super(type, x, y);
    }
}
