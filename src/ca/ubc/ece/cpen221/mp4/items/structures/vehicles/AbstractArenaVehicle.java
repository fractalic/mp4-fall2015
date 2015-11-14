package ca.ubc.ece.cpen221.mp4.items.structures.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * @author khyjoon
 */
public abstract class AbstractArenaVehicle implements ArenaVehicle {
    private int INITIAL_ENERGY;
    private int INITIAL_INTEGRITY;
    private int INITIAL_ACCELERATION;
    private int INITIAL_SPEED;
    private int MAX_ENERGY;
    private int MAX_INTEGRITY;
    private int MAX_ACCELERATION;
    private int MAX_SPEED;
    private int MAX_TURNING_SPEED;
    private int STRENGTH;
    private int VIEW_RANGE;
    private int COOLDOWN;
    
    private ImageIcon image;
    
    private int integrity = INITIAL_INTEGRITY;
    private int acceleration = INITIAL_ACCELERATION;
    private int speed = INITIAL_SPEED;
    
    private AI ai;

    private Location location;
    private int energy = INITIAL_ENERGY;
    
    private boolean isDestroyed; // says whether the vehicle is crashed or not
	
    
    @Override
    public int getMaxAcceleration() {
		return MAX_ACCELERATION;
	}
    
    @Override
    public void accelerate(int deltaV) {
        this.speed += Math.max(deltaV, MAX_ACCELERATION);
    }
    
    @Override
    public int getSpeed() {
    	return speed;
    }
    
    @Override
    public int getMaxSpeed() {
    	return MAX_SPEED;
    }
    
    @Override
    public int getMaxTurningSpeed() {
    	return MAX_TURNING_SPEED;
    }
    
    @Override
    public void crash(Item item) {
    	if (item.getStrength() > STRENGTH)
    		isDestroyed = true;
    }
    
    @Override
    public void tooFastTooFurious(ArenaVehicle vehicle) {
    	if (vehicle.getSpeed() > MAX_SPEED)
    		isDestroyed = true;
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
        return image;
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
