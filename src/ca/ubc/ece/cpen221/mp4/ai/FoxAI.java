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
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {

    boolean changeDefault = false;
    int     dx            = 0;
    int     dy            = 0;

    public FoxAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Set<Item> nearbyItems = world.searchSurroundings(animal);
        List<Item> rabbits = new ArrayList<Item>();
        List<Item> grass = new ArrayList<Item>();

        Boolean hungry = ((float) animal
                .getEnergy() < (float) animal.getMaxEnergy() / 1.3);
        Boolean breedable = (animal.getEnergy() > animal
                .getMinimumBreedingEnergy());

        Command nextCommand = new WaitCommand();

        Location desiredLocation = animal.getLocation();
        StringBuilder directionOrdering = new StringBuilder();

        for (Item item : nearbyItems) {
            if (item.getName() == "grass") {
                grass.add(item);
            }
            if (item.getName() == "Rabbit") {
                rabbits.add(item);
            }
        }

        boolean locationAvailable = false;
        boolean grassAvailable = false;
        boolean rabbitAvailable = false;
        boolean rabbitAdjacent = false;
        
        if (changeDefault) {
            dx = (int) Math.round(Math.random());
            dy = (int) Math.round(Math.random());
            changeDefault = false;
        }

        if (!grass.isEmpty()) {
            Grass closestGrass = (Grass) closestItem(animal, grass);
            Location averageGrass = averageLocation(grass);

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
        if (!rabbits.isEmpty()) {
            Rabbit closestRabbit = (Rabbit) closestItem(animal, rabbits);
            Location averageRabbit = averageLocation(rabbits);
            
            if (animal.getLocation()
                    .getDistance(closestRabbit.getLocation()) == 1) {
                nextCommand = new EatCommand(animal, closestRabbit);
                changeDefault = true;
                rabbitAdjacent = true;
            }

            dx = averageRabbit.getX() - animal.getLocation().getX();
            dy = averageRabbit.getY() - animal.getLocation().getY();

            if (animal.getLocation()
                    .getDistance(closestRabbit.getLocation()) <= 2) {
                rabbitAvailable = true;
            }
        }

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
        if (!rabbitAdjacent) {

            if (rabbitAvailable || hungry) {
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
    }

}
