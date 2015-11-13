package ca.ubc.ece.cpen221.mp4.items.environment;

import java.util.HashSet;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.ExpandCommand;
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

    private final int INITIAL_ENERGY          = 2000;
    private final int MAX_ENERGY              = 2000;
    private final int STRENGTH                = 10000;

    private final int INITIAL_MOVING_RANGE    = 0;
    private final int INITIAL_EXPANSION_RANGE = 2;

    public Volcano(Location initialLocation) {
        this.setMAX_ENERGY(MAX_ENERGY);
        this.setSTRENGTH(STRENGTH);

        this.setEnergy(INITIAL_ENERGY);
        this.setMovingRange(INITIAL_MOVING_RANGE);
        this.setCooldown((int)(Math.random() * 50 + 50));
        this.setExpansionRange(INITIAL_EXPANSION_RANGE);

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
        Location here = super.getLocation();

        Set<Location> expansion = new HashSet<Location>();

        for (int x = -super.getExpansionRange(); x <= super.getExpansionRange(); x++) {
            for (int y = -super.getExpansionRange(); y <= super.getExpansionRange(); y++) {
                Location there = new Location(here.getX() + x, here.getY() + y);

                if (here.getDistance(there) <= super.getExpansionRange()) {
                    expansion.add(there);
                }
            }
        }
        
        this.loseEnergy(10);
        
        return new ExpandCommand(this, expansion);
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
