package ca.ubc.ece.cpen221.mp4.items.environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Item;

/**
 * Nature does not show up in the world, but it places {@link ActiveEnvironment}
 * items at random intervals.
 */
public class Nature implements Actor {

    @Override
    public int getCoolDownPeriod() {
        return (int) Math.random() * 10 + 50;
    }

    @SuppressWarnings("unused")
    @Override
    public Command getNextAction(World world) {
        List<Item> igniters = new ArrayList<Item>();
        for (Item item : world.getItems()) {
            if (item.getName() == "grass") {
                igniters.add(item);
            }
        }

        if (igniters.isEmpty()) {
            return new WaitCommand();
        }

        final Item source = igniters
                .get((int) Math.floor(Math.random() * igniters.size()));

        final Location riverhead = Util.getRandomEmptyLocation(world);

        if (Math.random() > 0.5) {
            // An anonymous Command class which starts fires.
            return new Command() {

                @Override
                public void execute(World world) {
                    Fire fire = new Fire(source.getLocation());
                    source.loseEnergy(Integer.MAX_VALUE);
                    world.addItem(fire);
                    world.addActor(fire);
                }
            };
        } else {
            // An anonymous Command class which creates rivers.
            return new Command() {

                @Override
                public void execute(World world) {
                    River river = new River(riverhead);
                    world.addItem(river);
                    world.addActor(river);
                }
            };
        }
    }

}
