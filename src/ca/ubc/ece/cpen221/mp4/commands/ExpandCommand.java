package ca.ubc.ece.cpen221.mp4.commands;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.environment.ActiveEnvironment;

/**
 * An ExpandCommand is a {@link Command} which represents an
 * {@link ActiveEnvironment} producing related items adjacent to itself. This
 * Command inserts multiple children into the given World.
 */
public final class ExpandCommand implements Command {

    private final ActiveEnvironment parent;
    private final Set<Location>     targetSet;

    /**
     * Constructor where parent is the ActiveEnvironment that is spreading and
     * target is the set of locations where the children will appear. The
     * children must be created at a location adjacent to the parent.
     * This command automatically ignores locations which are occupied.
     *
     * @param parent
     *            the parent ActiveEnvironment
     * @param target
     *            the locations to which this item expands.
     */
    public ExpandCommand(ActiveEnvironment parent, Set<Location> target) {
        this.parent = parent;
        this.targetSet = target;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        for (Location target : targetSet) {
            if (!Util.isValidLocation(world, target)) {
                throw new InvalidCommandException(
                        "Invalid ExpandCommand: Invalid/non-empty expansion target location");
            }

            if (Util.isLocationEmpty(world, target)) {
                ActiveEnvironment child = parent.expand();
                child.moveTo(target);
                world.addItem(child);
                world.addActor(child);
            }
        }
    }

}
