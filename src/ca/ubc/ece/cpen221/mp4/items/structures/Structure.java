package ca.ubc.ece.cpen221.mp4.items.structures;

import ca.ubc.ece.cpen221.mp4.items.Item;


/**
 * Structures are mechanical or other non-organic items.
 * Technically they are food, but their calorie count will be zero
 * (except of bio-mech hybrids).
 * Being items, {@link Structure}s recognize both Energy and Integrity.
 * Integrity corresponds to damage, while energy might be fuel or electricity.
 * Attempting to eat a structure is considered poor taste, and should be
 * handled appropriately by EatCommand, and optionally by anything using an
 * EatCommand.
 * For {@link Structure}s, isDead() should return true only if the item
 * should be removed from the screen. Energy or Integrity of zero need not
 * cause isDead() to return zero.
 * 
 * @author benhughes
 *
 */
public interface Structure extends Item {

    /**
     * Cause a structure to take damage.
     * 
     * @effects Structure loses integrity, and may change if its image
     *          or be removed from the screen if it is destroyed.
     * @param integrity is the amount of integrity to lose.
     */
    public void loseIntegrity(int integrity);
    
    /**
     * Check the current integrity of the structure.
     * 
     * @return the integrity of the structure.
     */
    public int getIntegrity();
    
    /**
     * Check the maximum integrity this structure can have.
     * 
     * @return the maximum integrity of this structure.
     */
    public int getMaxIntegrity();
    
    /**
     * Determine if the structure has very low integrity (usually zero).
     * 
     * @return true if the structure has a negligibly low integrity.
     */
    public boolean isDestroyed();
    
    /**
     * Return the energy of the structure.
     * 
     * @return The current energy of the structure.
     */
    public int getEnergy();
    
    /**
     * Determine the maximum energy of the structure.
     * 
     * @return The maximum energy of the structure.
     */
    public int getMaxEnergy();
    
    /**
     * Return the view distance of the structure.
     * 
     * @return the view distance (Manhattan distance) of the structure.
     */
    public int getViewRange();
    
    /**
     * Override isOrganic from {@link Item}.
     * @return false for all structures.
     */
    static boolean isOrganic() {
        return false;
    }
    
    /**
     * Override isStructure from {@link Item}.
     * 
     * @return true for all structures.
     */
    static boolean isStructure() {
        return true;
    }
}
