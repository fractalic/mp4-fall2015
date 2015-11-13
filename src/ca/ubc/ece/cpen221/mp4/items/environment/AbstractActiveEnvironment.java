package ca.ubc.ece.cpen221.mp4.items.environment;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;


public abstract class AbstractActiveEnvironment implements ActiveEnvironment {
    // TODO: add ai or overlord to control environment
    // TODO: add command to spread
    private int MAX_ENERGY;
    private int STRENGTH;
    
    private int movingRange;
    private int cooldown;
    
    private ImageIcon image;

    private Location location;
    private int energy;
    
    
    @Override
    public abstract String getName();
    
    @Override
    public abstract ActiveEnvironment spread();
    
    @Override
    public abstract void consume(Item item);
    
    
    protected void setMAX_ENERGY(int MAX_ENERGY) {
        this.MAX_ENERGY = MAX_ENERGY;
    }
    
    protected void setSTRENGTH(int STRENGTH) {
        this.STRENGTH = STRENGTH;
    }
    
    
    protected void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    protected void setLocation(Location location) {
        this.location = location;
    }
    
    protected void setEnergy(int energy) {
        this.energy = energy;
    }
    
    protected void setImage(ImageIcon image) {
        this.image = image;
    }
    
    protected void setMovingRange(int movingRange) {
        this.movingRange = movingRange;
    }
    
    @Override
    public int getStrength() {
        return STRENGTH;
    }
    
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }
    

    public int getEnergy() {
        return energy;
    }
    
    @Override
    public int getCoolDownPeriod() {
        return cooldown;
    }
    
    @Override
    public int getMovingRange() {
        return movingRange;
    }

    @Override
    public ImageIcon getImage() {
        return image;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

}
