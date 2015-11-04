package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Griever implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 500;
    private static final int MAX_ENERGY = 1000;
    private static final int STRENGTH = 500;
    private static final int VIEW_RANGE = 6;
    private static final int MIN_BREEDING_ENERGY = 800;
    private static final int COOLDOWN = 3;
    private static final ImageIcon grieverImage = Util.loadImage("griever.gif");

    private final AI ai;

    private Location location;
    private int energy;
    
    private static final int MOVING_RANGE = 2;
    private static final int ENERGY_LOSS_RATE = 10;
    
    /**
     * Create a new {@link Griever} with an {@link AI} at
     * initialLocation. The initialLocation must be valid
     * and empty.
     * 
     * @param grieverAI
     *           : The AI designed for grievers.
     * @param initialLocation
     *           : The location where this griever will be created.
     */
    public Griever(AI grieverAI, Location initialLocation) {
        ai = grieverAI;
        location = initialLocation;
        energy = INITIAL_ENERGY;
    }
    
    @Override
    public int getEnergy() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public LivingItem breed() {
        Griever child = new Griever(ai, location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return MOVING_RANGE;
    }

    @Override
    public ImageIcon getImage() {
        return grieverImage;
    }

    @Override
    public String getName() {
        return "Griever";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy -= energyLoss;
    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return energy;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy -= ENERGY_LOSS_RATE;
        return nextAction;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

}
