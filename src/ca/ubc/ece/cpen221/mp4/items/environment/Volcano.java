package ca.ubc.ece.cpen221.mp4.items.environment;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.SpreadCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * Volcanos erupt to produce fire at random times.
 * 
 * @author benhughes
 *
 */
public class Volcano extends AbstractActiveEnvironment {

    private final int INITIAL_ENERGY       = 2000;
    private final int MAX_ENERGY           = 2000;
    private final int STRENGTH             = 10000;

    private final int INITIAL_MOVING_RANGE = 0;
    private final int INITIAL_COOLDOWN     = 10;
    private int       eruptionRange        = 3;

    public Volcano(Location initialLocation) {
        this.setMAX_ENERGY(MAX_ENERGY);
        this.setSTRENGTH(STRENGTH);

        this.setEnergy(INITIAL_ENERGY);
        this.setMovingRange(INITIAL_MOVING_RANGE);
        this.setCooldown(INITIAL_COOLDOWN);

        this.setLocation(initialLocation);

        this.setImage(Util.loadImage("volcano.gif"));
    }

    @Override
    public void moveTo(Location targetLocation) {
        super.setLocation(targetLocation);
    }

    @Override
    public void loseEnergy(int energyLoss) {
        super.setEnergy(super.getEnergy() - energyLoss);
    }

    @Override
    public String getName() {
        return "Volcano";
    }

    @Override
    public Command getNextAction(World world) {
        Set<Item> nearbyItems = world.searchSurroundings(this.getLocation(),
                eruptionRange);

        for (Item item : nearbyItems) {
            if (item.getMeatCalories() > 0 || item.getPlantCalories() > 0) {
                return new SpreadCommand(this, item);
            }
        }

        this.loseEnergy(10);

        return new WaitCommand();
    }

    @Override
    public ActiveEnvironment spread() {
        Fire child = new Fire(super.getLocation());
        return child;
    }

    @Override
    public void consume(Item item) {
        super.setEnergy(super.getEnergy() + item.getPlantCalories()
                + item.getMeatCalories());

    }

}
