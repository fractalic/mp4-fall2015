package ca.ubc.ece.cpen221.mp4.items.environment;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;


public interface ActiveEnvironment extends MoveableItem, Actor {
    /**
     * Spread to an adjacent square. This is very similar to
     * breeding of {@link LivingItem}s, but the child item
     * may be placed on already occupied locations.
     */
    public ActiveEnvironment spread();
    
    /**
     * The analogue of eating for a thing that isn't alive, 
     * consume acquires all the energy from the target item.
     * 
     * @param item The item to be consumed.
     */
    public void consume(Item item);

}
