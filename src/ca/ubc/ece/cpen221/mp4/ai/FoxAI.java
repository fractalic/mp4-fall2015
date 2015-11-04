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

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {

    private int  closest             = 2;                // max number;
                                                         // greater than fox's
                                                         // view range

    private int  closestFoodDistance = Integer.MAX_VALUE;

    private Item closestFood         = null;

    public FoxAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Set<Item> nearbyItems = world.searchSurroundings(animal);
        closestFoodDistance = Integer.MAX_VALUE;

        Command action = new WaitCommand();
        List<Location> desiredLocations = new ArrayList<Location>();
        List<Location> otherLocations = new ArrayList<Location>();
        Location animalLocation = animal.getLocation();

        for (Item item : nearbyItems) {
            int distanceToItem = item.getLocation()
                    .getDistance(animal.getLocation());
            int deltaXToItem = animalLocation.getX()
                    - item.getLocation().getX();
            int deltaYToItem = animalLocation.getY()
                    - item.getLocation().getY();
            int stepXTowardItem = deltaXToItem > 0 ? animal.getMovingRange()
                    : -animal.getMovingRange();
            int stepYTowardItem = deltaYToItem > 0 ? animal.getMovingRange()
                    : -animal.getMovingRange();

            if (item.getStrength() >= animal.getStrength()) {
                desiredLocations.add(
                        new Location(animalLocation.getX() - stepXTowardItem,
                                animalLocation.getY() - stepYTowardItem));
            } else if (item.getMeatCalories() > 0) {

                if (distanceToItem == 1) {
                    return new EatCommand(animal, item);
                }

                desiredLocations.add(
                        new Location(animalLocation.getX() + stepXTowardItem,
                                animalLocation.getY() + stepYTowardItem));
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                otherLocations.add(new Location(animalLocation.getX() + x,
                        animalLocation.getY() + y));
            }
        }

        for (Location potentialLocation : desiredLocations) {
            if (isLocationEmpty(world, animal, potentialLocation)) {
                if (animal.getEnergy() > animal.getMinimumBreedingEnergy()) {
                    return new BreedCommand(animal, potentialLocation);
                }
                return new MoveCommand(animal, potentialLocation);
            }
            
        }
        return action;
    }

}
