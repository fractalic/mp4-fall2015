package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.items.structures.vehicles.ArenaVehicle;

public class AbstractAI implements AI {

	public Direction oppositeDir(Direction dir) { // returns opposite direction
													// of direction dir
		if (dir == Direction.EAST) {
			return Direction.WEST;
		} else if (dir == Direction.WEST) {
			return Direction.EAST;
		} else if (dir == Direction.SOUTH) {
			return Direction.NORTH;
		} else {
			return Direction.SOUTH;
		}
	}
	
	/**
     * Convert ordering to list of directions.
     * 
     * @param ordering
     *            Ordering of desired directions.
     * @return list of directions represented by ordering.
     */
    protected List<Direction> orderingToList(String ordering) {
        List<Direction> directions = new ArrayList<Direction>();

        for (int i = 0; i < ordering.length(); i++) {
            switch (ordering.charAt(i)) {
            case 'N':
                directions.add(Direction.NORTH);
                break;
            case 'S':
                directions.add(Direction.SOUTH);
                break;
            case 'E':
                directions.add(Direction.EAST);
                break;
            case 'W':
                directions.add(Direction.WEST);
                break;

            }
        }
        return directions;
    }

	/**
     * Determine the item from a list of items that is closest to an item.
     * 
     * @param source The source item to examine.
     * @param nearby A list of items near the animal.
     * @return The closest item to the animal;
     */
    protected Item closestItem(Item source, List<Item> nearby) {
        Item closest = nearby.get(0);
        for (Item item : nearby) {
            if (source.getLocation().getDistance(item.getLocation()) < source
                    .getLocation().getDistance(closest.getLocation())) {
                
                closest = item;
            }
        }
        
        return closest;
    }
    
    /**
     * Get the average location of a list of items.
     * 
     * @param list
     *            of items to be averaged.
     * @return the average location of the list
     */
    protected Location averageLocation(List<Item> list) {
        int averageX = 0;
        int averageY = 0;

        for (Item item : list) {
            averageX += item.getLocation().getX();
            averageY += item.getLocation().getY();
        }

        averageX = Math.round((float) averageX / (float) list.size());
        averageY = Math.round((float) averageY / (float) list.size());

        return new Location(averageX, averageY);
    }
    
	public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) { // returns
																								// true
																								// if
																								// location
																								// is
																								// empty
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(animal);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		return new WaitCommand();
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaVehicle vehicle) {
		// TODO Auto-generated method stub
		return null;
	}
}
