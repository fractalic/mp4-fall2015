package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * A Griever from Maze Runner. A Griever is a like a spider with mechanical
 * legs.
 * 
 * @author benhughes
 *
 */
public class Griever extends AbstractArenaAnimal {

    private final int       INITIAL_ENERGY      = 500;
    private final int       MAX_ENERGY          = 1000;
    private final int       STRENGTH            = 500;
    private final int       VIEW_RANGE          = 6;
    private final int       MIN_BREEDING_ENERGY = 800;
    private final int       COOLDOWN            = 3;
    private final int       MOVING_RANGE        = 2;
    private final int       ENERGY_LOSS_RATE    = 10;

    private final ImageIcon grieverImage        = Util.loadImage("griever.gif");
    private final AI        ai;

    /**
     * Create a new {@link Griever} with an {@link AI} at initialLocation. The
     * initialLocation must be valid and empty.
     * 
     * @param grieverAI
     *            : The AI designed for grievers.
     * @param initialLocation
     *            : The location where this griever will be created.
     */
    public Griever(AI grieverAI, Location initialLocation) {
        initSuper();
        ai = grieverAI;
        super.setLocation(initialLocation);
        super.setEnergy(INITIAL_ENERGY);
        super.setImage(grieverImage);
    }

    /**
     * Initialize constants in the superclass {@link AbstractArenaAnimal}.
     */
    private void initSuper() {
        super.setINITIAL_ENERGY(INITIAL_ENERGY);
        super.setMAX_ENERGY(MAX_ENERGY);
        super.setMIN_BREEDING_ENERGY(MIN_BREEDING_ENERGY);
        super.setCOOLDOWN(COOLDOWN);
        super.setSTRENGTH(STRENGTH);
        super.setVIEW_RANGE(VIEW_RANGE);
    }

    @Override
    public LivingItem breed() {
        Griever child = new Griever(ai, super.getLocation());
        child.setEnergy(super.getEnergy() / 2);
        this.setEnergy(super.getEnergy() / 2);
        return child;
    }

    @Override
    public void eat(Food food) {
        super.setEnergy(super.getEnergy() + food.getMeatCalories());
    }

    @Override
    public void moveTo(Location targetLocation) {
        super.setLocation(targetLocation);
    }

    @Override
    public String getName() {
        return "Griever";
    }

    @Override
    public void loseEnergy(int energyLoss) {
        super.setEnergy(super.getEnergy() - energyLoss);
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        super.setEnergy(super.getEnergy() - ENERGY_LOSS_RATE);
        return nextAction;
    }

}
