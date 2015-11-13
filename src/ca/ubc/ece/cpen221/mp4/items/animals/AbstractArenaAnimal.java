package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public abstract class AbstractArenaAnimal implements ArenaAnimal { // abstract
                                                                   // class for
                                                                   // other
                                                                   // arena
                                                                   // animals

    private int       MAX_ENERGY;
    private int       STRENGTH;
    private int       VIEW_RANGE;
    private int       MIN_BREEDING_ENERGY;
    private int       COOLDOWN;
    private int       MOVING_RANGE;

    private AI        ai;
    private ImageIcon image;

    private Location  location;
    private int       energy;

    @Override
    public abstract LivingItem breed();

    @Override
    public void eat(Food food) {
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
    }

    protected void setEnergy(int i) {
        this.energy = Math.min(MAX_ENERGY, i);
    }
    
    protected void setMOVING_RANGE(int m) {
        this.MOVING_RANGE = m;
    }

    protected void setMAX_ENERGY(int i) {
        this.MAX_ENERGY = i;
    }

    protected void setSTRENGTH(int i) {
        this.STRENGTH = i;
    }

    protected void setVIEW_RANGE(int i) {
        this.VIEW_RANGE = i;
    }

    protected void setMIN_BREEDING_ENERGY(int i) {
        this.MIN_BREEDING_ENERGY = i;
    }

    protected void setCOOLDOWN(int i) {
        this.COOLDOWN = i;
    }

    protected void setLocation(Location l) {
        this.location = l;
    }

    protected void setImage(ImageIcon i) {
        this.image = i;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public ImageIcon getImage() {
        return image;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getMeatCalories() {
        return energy;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

    @Override
    public int getMovingRange() {
        return MOVING_RANGE;
    }

    @Override
    public abstract String getName();

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy--; // Loses 1 energy regardless of action.
        return nextAction;
    }

    @Override
    public int getPlantCalories() { // arena animals are not plants
        return 0;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public boolean isDead() {
        return this.energy <= 0;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }
}
