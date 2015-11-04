package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
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
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

    private int     closest = 10; // max number; greater than rabbit's view
                                  // range
    private int     temp;
    private boolean foxFound;

    public RabbitAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Set<Item> nearbyItems = world.searchSurroundings(animal);
        // int closestPlantDistance = Integer.MAX_VALUE;

        Command action = new MoveCommand(animal,
                Util.getRandomEmptyAdjacentLocation((World) world,
                        animal.getLocation()));

        for (Item item : nearbyItems) {
            int distance = item.getLocation()
                    .getDistance(animal.getLocation());
            if (item.getStrength() > animal.getStrength()) {
            }
            if (item.getPlantCalories() > 0) {
                if (distance == 1 && item.getStrength() < animal.getStrength()) {
                    return new EatCommand(animal, item);
                }
                Direction dirTo = Util.getDirectionTowards(animal.getLocation(),
                        item.getLocation());
                Location idealLocation = new Location(animal.getLocation(),
                        dirTo);
                if (Util.isLocationEmpty((World) world, idealLocation)) {
                    action = new MoveCommand(animal, idealLocation);
                }
            }
        }
        return action;
    }
}
