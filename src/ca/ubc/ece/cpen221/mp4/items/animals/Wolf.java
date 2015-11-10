package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Wolf implements ArenaAnimal {

	private static final int INITIAL_ENERGY = 150;
	private static final int MAX_ENERGY = 240;
	private static final int STRENGTH = 135;
	private static final int VIEW_RANGE = 7;
	private static final int MIN_BREEDING_ENERGY = 50;
	private static final int COOLDOWN = 2;
	private static final ImageIcon wolfImage = Util.loadImage("wolf.gif");

	private final AI ai;

	private Location location;
	private int energy;

    private static final int MOVING_RANGE = 2;
    private static final int ENERGY_LOSS_RATE = 1;
	/**
	 * Create a new {@link Wolf} with an {@link AI} at initialLocation. The
	 * initialLocation must be valid and empty.
	 * 
	 * @param wolfAI
	 *            : The AI designed for grievers.
	 * @param initialLocation
	 *            : The location where this wolf will be created.
	 */
	public Wolf(AI wolfAI, Location initialLocation) {
		ai = wolfAI;
		location = initialLocation;
		energy = INITIAL_ENERGY;
	}

	@Override
	public LivingItem breed() {
		Wolf child = new Wolf(ai, location);
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
		return wolfImage;
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
		return MOVING_RANGE; // Can only move to one tile further than its adjacent locations.
	}

	@Override
	public String getName() {
		return "Wolf";
	}

	@Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		this.energy -= ENERGY_LOSS_RATE; // Loses 1 energy regardless of action.
		return nextAction;
	}

	@Override
	public int getPlantCalories() { // wolves are unfortunately not vegetarian
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