package ca.ubc.ece.cpen221.mp4.items.environment;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * {@link ActiveEnvironment}s are non-living environmental effects and objects.
 * They can change their size, or produce related items are nearby locations.
 * 
 * @author benhughes
 *
 */
public interface ActiveEnvironment extends MoveableItem, Actor {
    /**
     * Spread onto an adjacent item. This is effectively a combination
     * of eating and breeding of {@link LivingItem}s, where the child item
     * must be placed on an already occupied location.
     */
    public ActiveEnvironment spread();
    
    /**
     * The analogue of eating for a thing that isn't alive, 
     * consume acquires all the energy from the target item.
     * 
     * @param item The item to be consumed.
     */
    public void consume(Item item);
    
    /**
     * Create a related item which represents the expansion of this
     * {@link ActiveEnvironment}. Child will appear on a nearby empty location.
     */
    public ActiveEnvironment expand();
    
    /**
     * Determine the range of expansion of this {@link ActiveEnvironment}.
     * The range of expansion determines the maximum distance to which this
     * {@link ActiveEnvironment} can expand with a single command.
     * 
     * @return the maximum range of expansion of the item.
     */
    public int getExpansionRange();

}
