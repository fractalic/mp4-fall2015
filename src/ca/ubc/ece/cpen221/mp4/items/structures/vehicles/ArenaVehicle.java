package ca.ubc.ece.cpen221.mp4.items.structures.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.structures.Structure;


/**
 * Vehicles are autonomous mechanical objects.
 * Vehicles are not strictly edible, but for now we will derive from the
 * food root class anyway.
 * 
 * In a vehicle derived from {@link Item}, energy is interpreted as fuel or
 * other power source. For now, it never decreases.
 * 
 * Vehicle speed is controlled by their cooldown period (see {@link Actor}).
 * 
 * Damage is counted by "integrity", as in structural integrity, to avoid
 * confusion of damage with energy for living organisms.
 * 
 * @author benhughes
 *
 */
public interface ArenaVehicle extends Structure, MoveableItem, Actor {

    // TODO: it would be super cool if dead vehicles were not removed from
    // the screen until being collected by a tow truck.
    // TODO: add a command for crashes.
    // TODO: add a command for towing?
    // TODO: change image of vehicle when destroyed?
    // TODO: add command to call tow truck.
    
    
    /**
     * Return the maximum change in speed of the vehicle. The vehicle's speed
     * may increase or decrease by at most this amount, unless a crash occurs.
     * Acceleration itself may change instantaneously. Jerk <= ∞ (+ infinity).
     * 
     * @return the acceleration of the vehicle.
     */
    public int getMaxAcceleration();
    
    /**
     * Return the current rate at which this vehicle is accelerating.
     * This value will be zero if the vehicle reaches its maximum speed.
     * 
     * @return value of current acceleration
     *
     */
    // TODO: probably don't need this
    //public int getAcceleration();
    
    /**
     * Return the current speed of the vehicle
     * Speed is increased by the current value of the acceleration and controlled
     * by the cooldown period
     * @return value of current speed
     */
    public int getSpeed();
    
    /**
     * Return the maximum speed of the vehicle.
     * Beyond this point the vehicle should explode, which is the same as being
     * destroyed
     * @return the speed of vehicle
     */
    public int getMaxSpeed();
    

    /**
     * Return the maximum speed at which this vehicle can change direction.
     * 
     * @return the maximum speed for vehicle to change direction.
     */
    public int getMaxTurningSpeed();
    
    /**
     * Change the speed of a vehicle by at most the maximum acceleration
     * of the vehicle.
     * 
     * @param deltaV the change in speed.
     */
    public void accelerate(int deltaV);
    
    /**
     * Take appropriate action when the vehicle will collide with another item.
     * A crash occurs when the item attempts to move onto another item.
     * 
     * @param item that this vehicle is colliding with.
     */
    public void crash(Item item);
    
    /**
     * Take appropriate action when the vehicle goes too fast.
     * Vehicle is destroyed if it exceeds a particular speed.
     * 
     * @param current speed of vehicle
     */
    public void tooFastTooFurious(ArenaVehicle vehicle);
}
