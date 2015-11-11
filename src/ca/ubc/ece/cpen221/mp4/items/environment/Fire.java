package ca.ubc.ece.cpen221.mp4.items.environment;

import ca.ubc.ece.cpen221.mp4.Location;


public class Fire extends AbstractActiveEnvironment {

    @Override
    public void moveTo(Location targetLocation) {
        super.setLocation(targetLocation);
    }

    @Override
    public void loseEnergy(int energyLoss) {
        super.setEnergy(super.getEnergy() - energyLoss);
    }

    @Override
    public String getName() {
        return "Fire";
    }

}
