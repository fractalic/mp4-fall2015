package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

    boolean changeDefault = false;
    int dx = 0;
    int dy = 0;

    public RabbitAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Set<Item> nearbyItems = world.searchSurroundings(animal);
        List<Item> foxes = new ArrayList<Item>();
        List<Item> grass = new ArrayList<Item>();

        Boolean hungry = ((float)animal.getEnergy() < (float)animal.getMaxEnergy() / 1.3);
        Boolean breedable = (animal.getEnergy() > animal
                .getMinimumBreedingEnergy());

        Command nextCommand = new WaitCommand();

        Location desiredLocation = animal.getLocation();
        StringBuilder directionOrdering = new StringBuilder();

        for (Item item : nearbyItems) {
            if (item.getName() == "grass") {
                grass.add(item);
            }
            if (item.getName() == "Fox") {
                foxes.add(item);
            }
        }

        boolean locationAvailable = false;
        boolean grassAvailable = false;
        boolean danger = false;
        if (changeDefault) {
            dx = (int)Math.round(Math.random());
            dy = (int)Math.round(Math.random());
            changeDefault = false;
        }

        if (!grass.isEmpty()) {
            Grass closestGrass = (Grass) closestItem(animal, grass);
            Location averageGrass = averageLocation(grass);

            if (animal.getLocation()
                    .getDistance(closestGrass.getLocation()) == 1) {
                nextCommand = new EatCommand(animal, closestGrass);
                changeDefault = true;
                grassAvailable = true;
            }

            if (hungry || !breedable) {
                dx = closestGrass.getLocation().getX()
                        - animal.getLocation().getX();
                dy = closestGrass.getLocation().getY()
                        - animal.getLocation().getY();
            } else {
                dx = averageGrass.getX() - animal.getLocation().getX();
                dy = averageGrass.getY() - animal.getLocation().getY();
            }
        }
        if (!foxes.isEmpty()) {
            Fox closestFox = (Fox) closestItem(animal, foxes);
            Location averageFox = averageLocation(foxes);

            dx = -(averageFox.getX() - animal.getLocation().getX());
            dy = -(averageFox.getY() - animal.getLocation().getY());

            if (animal.getLocation()
                    .getDistance(closestFox.getLocation()) <= 2) {
                danger = true;
            }
        }

        // TODO: if hungry or foxes close, look for closest grass
        // TODO: if foxes far and breedable, breed toward closest grass
        // TODO: if hungry and next to grass and no foxes on other side of
        // grass, eat grass

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                directionOrdering = directionOrdering.append("EW");
            } else {
                directionOrdering = directionOrdering.append("WE");
            }
            if (dy > 0) {
                directionOrdering = directionOrdering.append("SN");
            } else {
                directionOrdering = directionOrdering.append("NS");
            }
        } else {
            if (dy > 0) {
                directionOrdering = directionOrdering.append("SN");
            } else {
                directionOrdering = directionOrdering.append("NS");
            }
            if (dx > 0) {
                directionOrdering = directionOrdering.append("EW");
            } else {
                directionOrdering = directionOrdering.append("WE");
            }
        }

        for (Direction direction : orderingToList(
                directionOrdering.toString())) {
            Location potential = new Location(animal.getLocation(), direction);
            if (isLocationEmpty(world, animal, potential)) {
                locationAvailable = true;
                desiredLocation = potential;
                break;
            }
        }
        if (!grassAvailable) {

            if (danger || hungry) {
                if (locationAvailable) {
                    nextCommand = new MoveCommand(animal, desiredLocation);
                } else {
                    nextCommand = new WaitCommand();
                }
            } else if (breedable) {
                if (locationAvailable) {
                    nextCommand = new BreedCommand(animal, desiredLocation);
                } else {
                    nextCommand = new WaitCommand();
                }
            }
        }

        return nextCommand;

        /*
         * int averageFoxX = foxes.isEmpty() ? -1 : 0; int averageFoxY =
         * foxes.isEmpty() ? -1 : 0;
         * 
         * if () for (Fox fox : foxes) { averageFoxX +=
         * fox.getLocation().getX(); averageFoxY += fox.getLocation().getY(); }
         * averageFoxX /= foxes.size(); averageFoxY /= foxes.size();
         */

        /*
         * closestFoodDistance = Integer.MAX_VALUE;
         * 
         * Command action = new WaitCommand(); List<Location> desiredLocations =
         * new ArrayList<Location>(); List<Location> otherLocations = new
         * ArrayList<Location>(); Location animalLocation =
         * animal.getLocation();
         * 
         * for (Item item : nearbyItems) { int distanceToItem =
         * item.getLocation() .getDistance(animal.getLocation()); int
         * deltaXToItem = animalLocation.getX() - item.getLocation().getX(); int
         * deltaYToItem = animalLocation.getY() - item.getLocation().getY(); int
         * stepXTowardItem = deltaXToItem > 0 ? animal.getMovingRange() :
         * -animal.getMovingRange(); int stepYTowardItem = deltaYToItem > 0 ?
         * animal.getMovingRange() : -animal.getMovingRange();
         * 
         * if (item.getStrength() >= animal.getStrength()) {
         * desiredLocations.add( new Location(animalLocation.getX() -
         * stepXTowardItem, animalLocation.getY())); desiredLocations.add(new
         * Location(animalLocation.getX(), animalLocation.getY() -
         * stepYTowardItem)); } else if (item.getPlantCalories() > 0) {
         * 
         * if (distanceToItem == 1 && animal.getEnergy() <
         * animal.getMaxEnergy()) { return new EatCommand(animal, item); }
         * 
         * desiredLocations.add( new Location(animalLocation.getX() +
         * stepXTowardItem, animalLocation.getY())); desiredLocations.add(new
         * Location(animalLocation.getX(), animalLocation.getY() +
         * stepYTowardItem)); } }
         * 
         * for (int x = -1; x <= 1; x++) { for (int y = -1; y <= 1; y++) {
         * Location potentialLocation = new Location( animalLocation.getX() + x,
         * animalLocation.getY() + y); if (isLocationEmpty(world, animal,
         * potentialLocation)) { otherLocations.add(potentialLocation); } } }
         * 
         * for (Location potentialLocation : desiredLocations) { if
         * (isLocationEmpty(world, animal, potentialLocation)) { if
         * (animal.getEnergy() > 4 animal.getMinimumBreedingEnergy()) { return
         * new BreedCommand(animal, potentialLocation); } return new
         * MoveCommand(animal, potentialLocation); }
         * 
         * }
         * 
         * if (!otherLocations.isEmpty()) { return new MoveCommand(animal,
         * otherLocations.get((int) Math .round(Math.random() *
         * (otherLocations.size() - 1)))); } return action;
         */
    }
}
