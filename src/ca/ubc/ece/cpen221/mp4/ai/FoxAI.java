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
import ca.ubc.ece.cpen221.mp4.items.animals.*;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {
    private int  closest               = 2;                // max number;
                                                           // greater than fox's
                                                           // view range
    private int  closestRabbitDistance = Integer.MAX_VALUE;
    private Item closestRabbit         = null;

    public FoxAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Set<Item> nearbyItems = world.searchSurroundings(animal);
        closestRabbitDistance = Integer.MAX_VALUE;
        
        Command action = new WaitCommand();;
        
        for (Item item : nearbyItems) {
            if (item.getName() == "Rabbit") {
                int distance = item.getLocation()
                        .getDistance(animal.getLocation());
                if (distance == 1) {
                    return new EatCommand(animal, item);
                }
                if (distance <= closestRabbitDistance) {
                    closestRabbit = item;
                    closestRabbitDistance = distance;
                    Direction dirTo = Util.getDirectionTowards(
                            animal.getLocation(), item.getLocation());
                    Location idealLocation = new Location(animal.getLocation(),
                            dirTo);
                    if (Util.isLocationEmpty((World) world, idealLocation)) {
                        action = new MoveCommand(animal, idealLocation);
                    } else {
                        action = new MoveCommand(animal,
                                Util.getRandomEmptyAdjacentLocation(
                                        (World) world, animal.getLocation()));
                    }
                }
            }
        }
        return action;
    }

}
