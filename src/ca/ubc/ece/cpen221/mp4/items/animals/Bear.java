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
 * @author khyjoon
 */
public class Bear implements ArenaAnimal {

	private static final int INITIAL_ENERGY = 300;
	private static final int MAX_ENERGY = 350;
	private static final int STRENGTH = 400;
	private static final int VIEW_RANGE = 3;
	private static final int MIN_BREEDING_ENERGY = 230;
	private static final int COOLDOWN = 5;
	private static final ImageIcon bearImage = Util.loadImage("bear.gif");

	private final AI ai;

	private Location location;
	private int energy;

    private static final int MOVING_RANGE = 1;
    private static final int ENERGY_LOSS_RATE = 3;
    
	/**
	 * Create a new {@link Bear} with an {@link AI} at initialLocation. The
	 * initialLocation must be valid and empty.
	 * 
	 * @param bearAI
	 *            : The AI designed for grievers.
	 * @param initialLocation
	 *            : The location where this bear will be created.
	 */
	public Bear(AI bearAI, Location initialLocation) {
		ai = bearAI;
		location = initialLocation;
		energy = INITIAL_ENERGY;
	}

	@Override
	public LivingItem breed() {
		Bear child = new Bear(ai, location);
		child.energy = energy / 2;
		this.energy = energy / 2;
		return child;
	}

	@Override
	public void eat(Food food) {
		energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
	}

	@Override
	public int getCoolDownPeriod() {
		return COOLDOWN;
	}

	@Override
	public int getEnergy() {
		return energy;
	}

	@Override
	public ImageIcon getImage() {
		return bearImage;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getMaxEnergy() {
		return MAX_ENERGY;
	}

	@Override
	public int getMeatCalories() {
		// number for the amount of meat calories provided is equal to the
		// number for the amount of energy it has
		return energy;
	}

	@Override
	public int getMinimumBreedingEnergy() {
		return MIN_BREEDING_ENERGY;
	}

	@Override
	public int getMovingRange() {
		return MOVING_RANGE; // Can only move to adjacent locations
	}

	@Override
	public String getName() {
		return "Bear";
	}

	@Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		this.energy -= ENERGY_LOSS_RATE; // Loses 1 energy regardless of action.
		return nextAction;
	}

	@Override
	public int getPlantCalories() { // bears are not a plant
		return 0;
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getViewRange() {
		return VIEW_RANGE;
	}

	@Override
	public boolean isDead() {
		return energy <= 0;
	}

	@Override
	public void loseEnergy(int energyLoss) {
		this.energy -= energyLoss;
	}

	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}
}