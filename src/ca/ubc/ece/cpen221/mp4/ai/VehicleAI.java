package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.CrashCommand;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.*;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.items.structures.vehicles.ArenaVehicle;

public class VehicleAI implements AI {
	private int energy;

	public VehicleAI() {}
	
	public VehicleAI(int energy) {
		this.energy = energy;
	}

	public boolean isLocationEmpty(ArenaWorld world, ArenaVehicle vehicle, Location location) {
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(vehicle);
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
	public Command getNextAction(ArenaWorld world, ArenaVehicle vehicle) {
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(vehicle.getLocation(), dir);
		Set<Item> possibleRoadKill = world.searchSurroundings(vehicle);
		Location current = vehicle.getLocation();
		Iterator<Item> it = possibleRoadKill.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (current.getDistance(item.getLocation()) == 1) {
				return new CrashCommand(vehicle, item); // car crashes into environment
			}
		}
		if (Util.isValidLocation(world, targetLocation) && this.isLocationEmpty(world, vehicle, targetLocation)) {
			return new MoveCommand(vehicle, targetLocation);
		}
		return new WaitCommand();
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO Auto-generated method stub
		return null;
	}

}
