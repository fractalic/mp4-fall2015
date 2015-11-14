package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.structures.vehicles.ArenaVehicle;

/**
 * An EatCommand is a {@link Command} which represents a {@link LivingItem}
 * eating a {@link Food}.
 * 
 * @author khyjoon
 */
public final class CrashCommand implements Command {

	private final ArenaVehicle vehicle;
	private final Item item;

	/**
	 * Construct a {@link EatCommand}, where <code> item </code> is the eater
	 * and <code> food </code> is the food. Remember that the food must be
	 * adjacent to the eater, and the eater must have greater strength than the
	 * food.
	 * 
	 * @param vehicle
	 *            the eater
	 * @param item
	 *            : the food
	 *            
	 * @author khyjoon
	 */
	public CrashCommand(ArenaVehicle vehicle, Item item) {
		this.vehicle = vehicle;
		this.item = item;
	}

	@Override
	public void execute(World w) throws InvalidCommandException {
		if (vehicle.getStrength() >= item.getStrength())
			item.loseEnergy(Integer.MAX_VALUE);
		if (item.getLocation().getDistance(item.getLocation()) != 1)
			throw new InvalidCommandException("Invalid CrashCommand: Item is not adjacent");

		vehicle.crash(item);
	}

}
