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


public class Sedan extends AbstractArenaVehicle {
    private static final int INITIAL_ENERGY = 200;
    private static final int INITIAL_INTEGRITY = 300;
    private static final int MAX_ENERGY = 300;
    private static final int MAX_INTEGRITY = 400;
    private static final int STRENGTH = 100;
    private static final int VIEW_RANGE = 5;
    private static final int COOLDOWN = 1;
    private final int MAX_ACCELERATION = 1;
    
    private ImageIcon image = Util.loadImage("sedan.gif");;
    
    private int integrity;

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
		integrity = INITIAL_INTEGRITY;
	}

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }
    
    @Override
    public int getMaxAcceleration() {
        return MAX_ACCELERATION;
    }
    
    @Override
    public void crash(Item item) {
        
    }

    @Override
    public int getAcceleration() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxTurningSpeed() {
        // TODO Auto-generated method stub
        return ;
    }

    @Override
    public String getName() {
        return "Sedan";
    }

}
