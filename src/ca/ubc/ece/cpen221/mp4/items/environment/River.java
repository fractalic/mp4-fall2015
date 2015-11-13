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
 * Fire burns adjacent plants. It spreads on its own.
 *
 * @author benhughes
 */
public class River extends AbstractActiveEnvironment {

    private final int INITIAL_ENERGY          = 400;
    private final int MAX_ENERGY              = 400;
    private final int STRENGTH                = 5000;

    private final int INITIAL_MOVING_RANGE    = 1;
    private final int INITIAL_COOLDOWN        = 5;

    private final int INITIAL_EXPANSION_RANGE = 1;

    private boolean   nextCommandWait         = true;
    private boolean   isHead                  = true;

    public River(Location initialLocation) {
        this.setMAX_ENERGY(MAX_ENERGY);
        this.setSTRENGTH(STRENGTH);

        this.setEnergy(INITIAL_ENERGY);
        this.setMovingRange(INITIAL_MOVING_RANGE);
        this.setCooldown(INITIAL_COOLDOWN);
        this.setExpansionRange(INITIAL_EXPANSION_RANGE);

        this.setLocation(initialLocation);

        this.setImage(Util.loadImage("water.gif"));
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
        return "Water";
    }

    @Override
    public Command getNextAction(World world) {
        this.loseEnergy(20);

        if (nextCommandWait || !isHead) {
            nextCommandWait = false;
            return new WaitCommand();
        }
        Location here = super.getLocation();

        Set<Location> expansion = new HashSet<Location>();

        for (int x = -super.getExpansionRange(); x <= super.getExpansionRange(); x++) {
            for (int y = -super.getExpansionRange(); y <= super.getExpansionRange(); y++) {
                Location there = new Location(here.getX() + x, here.getY() + y);

                if (here.getDistance(there) <= super.getExpansionRange()) {
                    if (Math.random() > .8) {
                        if (expansion.isEmpty()) {
                            expansion.add(there);
                        }
                    }
                }
            }
        }

        return new ExpandCommand(this, expansion);
    }

    @Override
    public ActiveEnvironment spread() {
        River child = new River(super.getLocation());
        this.isHead = false;
        child.isHead = true;
        return child;
    }

    @Override
    public void consume(Item item) {
        super.setEnergy(super.getEnergy() + item.getPlantCalories()
                + item.getMeatCalories());

    }

}
