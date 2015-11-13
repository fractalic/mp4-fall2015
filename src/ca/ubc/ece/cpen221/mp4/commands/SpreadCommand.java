package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.environment.ActiveEnvironment;

/**
 * A SpreadCommand is a {@link Command} which represents a
 * {@link ActiveEnvironment} producing another item on top of an existing item.
 * This Command inserts a 'child' into the given World, and deletes objects
 * where the child will appear.
 */
public final class SpreadCommand implements Command {

    private final ActiveEnvironment parent;
    private final Item              recipient;

    /**
     * Constructor where <code>item</code> is the ActiveEnvironment that is
     * spreading and <code>target</code> is the location where the child will
     * appear. The child must be created at a location adjacent to the parent.
     *
     * @param parent
     *            the parent ActiveEnvironment
     * @param recipient
     *            the item onto which the parent is spreading
     */
    public SpreadCommand(ActiveEnvironment parent, Item item) {
        this.parent = parent;
        this.recipient = item;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        if (recipient.getStrength() >= parent.getStrength()) {
            throw new InvalidCommandException(
                    "Invalid SpreadCommand: Insufficient strength");
        }

        ActiveEnvironment child = parent.spread();
        child.consume(recipient);
        child.moveTo(recipient.getLocation());
        recipient.loseEnergy(Integer.MAX_VALUE);
        world.addItem(child);
        world.addActor(child);
    }

}
