package ca.ubc.ece.cpen221.mp4.items.structures.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;


public abstract class Sedan implements ArenaVehicle {
    private static final int INITIAL_ENERGY = 200;
    private static final int INITIAL_INTEGRITY = 300;
    private static final int MAX_ENERGY = 300;
    private static final int MAX_INTEGRITY = 400;
    private static final int STRENGTH = 100;
    private static final int VIEW_RANGE = 5;
    private static final int COOLDOWN = 1;
    
    private static final ImageIcon sedanImage = Util.loadImage("sedan.gif");;
    
    private int integrity = INITIAL_INTEGRITY;

    private final AI ai;

    private Location location;
    private int energy = INITIAL_ENERGY;

	/**
	 * Create a new {@link Sedan} with an {@link AI} at
	 * <code> initialLocation </code>. The <code> initialLoation
	 * </code> must be valid and empty.
	 *
	 * @param SedanAI
	 *            : The AI designed for Sedans
	 * @param initialLocation
	 *            : the location where this Sedan will be created
	 */
	public Sedan(AI sedanAI, Location initialLocation) {
		ai = sedanAI;
		location = initialLocation;
		energy = INITIAL_ENERGY;
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
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }
    
    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public ImageIcon getImage() {
        return sedanImage;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    
    @Override
    public int getIntegrity() {
        return integrity;
    }
    
    @Override
    public void loseIntegrity(int integrityLoss) {
        this.integrity = Math.min(0, this.integrity - integrityLoss);
    }
    
    @Override
    public int getMaxIntegrity() {
        return MAX_INTEGRITY;
    }
    
    @Override
    public boolean isDestroyed() {
        return integrity <= 0;
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public int getPlantCalories() {
        // This vehicle is not a plant.
        return 0;
    }
    
    @Override
    public int getMeatCalories() {
        // This vehicle is not meat.
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
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

}
