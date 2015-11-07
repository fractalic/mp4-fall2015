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

    private int  closestFoodDistance = Integer.MAX_VALUE;
    private Item closestFood         = null;

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
                                animalLocation.getY()));
                desiredLocations.add(new Location(animalLocation.getX(),
                        animalLocation.getY() - stepYTowardItem));
            } else if (item.getPlantCalories() > 0) {

                if (distanceToItem == 1
                        && animal.getEnergy() < animal.getMaxEnergy()) {
                    return new EatCommand(animal, item);
                }

                desiredLocations.add(
                        new Location(animalLocation.getX() + stepXTowardItem,
                                animalLocation.getY()));
                desiredLocations.add(new Location(animalLocation.getX(),
                        animalLocation.getY() + stepYTowardItem));
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Location potentialLocation = new Location(
                        animalLocation.getX() + x, animalLocation.getY() + y);
                if (isLocationEmpty(world, animal, potentialLocation)) {
                    otherLocations.add(potentialLocation);
                }
            }
        }

        for (Location potentialLocation : desiredLocations) {
            if (isLocationEmpty(world, animal, potentialLocation)) {
                if (animal.getEnergy() > 4
                        * animal.getMinimumBreedingEnergy()) {
                    return new BreedCommand(animal, potentialLocation);
                }
                return new MoveCommand(animal, potentialLocation);
            }

        }

        if (!otherLocations.isEmpty()) {
            return new MoveCommand(animal, otherLocations.get((int) Math
                    .round(Math.random() * (otherLocations.size() - 1))));
        }
        return action;
    }
}
