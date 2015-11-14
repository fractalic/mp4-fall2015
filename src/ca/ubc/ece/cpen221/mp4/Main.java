package ca.ubc.ece.cpen221.mp4;

import javax.swing.SwingUtilities;

import ca.ubc.ece.cpen221.mp4.ai.*;
import ca.ubc.ece.cpen221.mp4.items.Gardener;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.items.environment.Nature;
import ca.ubc.ece.cpen221.mp4.items.environment.Volcano;
import ca.ubc.ece.cpen221.mp4.items.structures.vehicles.Lamborghini;
import ca.ubc.ece.cpen221.mp4.items.structures.vehicles.Sedan;
import ca.ubc.ece.cpen221.mp4.items.structures.vehicles.Truck;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;

/**
 * The Main class initialize a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 *
 * You may modify or add Items/Actors to the World.
 *
 */
public class Main {

    static final int X_DIM                = 40;
    static final int Y_DIM                = 40;
    static final int SPACES_PER_GRASS     = 7;
    static final int INITIAL_GRASS        = X_DIM * Y_DIM / SPACES_PER_GRASS;
    static final int INITIAL_GNATS        = INITIAL_GRASS / 4;
    static final int INITIAL_RABBITS      = INITIAL_GRASS / 4;
    static final int INITIAL_FOXES        = INITIAL_GRASS / 32;
    static final int INITIAL_TIGERS       = INITIAL_GRASS / 32;
    static final int INITIAL_BEARS        = INITIAL_GRASS / 40;
    static final int INITIAL_HYENAS       = INITIAL_GRASS / 32;
    static final int INITIAL_CARS         = INITIAL_GRASS / 100;
    static final int INITIAL_TRUCKS       = INITIAL_GRASS / 150;
    static final int INITIAL_MOTORCYCLES  = INITIAL_GRASS / 64;
    static final int INITIAL_GRIEVERS     = INITIAL_GRASS / 70;
    static final int INITIAL_LAMBORGHINIS = INITIAL_GRASS / 32;
    static final int INITIAL_WOLVES       = INITIAL_GRASS / 64;
    static final int INITIAL_VOLCANOS     = INITIAL_GRASS / 70;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Main().createAndShowWorld();
            }
        });
    }

    public void createAndShowWorld() {
        World world = new WorldImpl(X_DIM, Y_DIM);
        initialize(world);
        new WorldUI(world).show();
    }

    public void initialize(World world) {
        addGrass(world);
        world.addActor(new Gardener());
        world.addActor(new Nature());

        addGnats(world);
        addRabbits(world);
        addFoxes(world);

        addGrievers(world);
        addTrucks(world);
        addLamborghinis(world);
        addSedans(world);
        addBears(world);
        addWolves(world);
        addVolcanos(world);

    }

    private void addGrass(World world) {
        for (int i = 0; i < INITIAL_GRASS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            world.addItem(new Grass(loc));
        }
    }

    private void addGnats(World world) {
        for (int i = 0; i < INITIAL_GNATS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Gnat gnat = new Gnat(loc);
            world.addItem(gnat);
            world.addActor(gnat);
        }
    }

    private void addFoxes(World world) {
        FoxAI foxAI = new FoxAI();
        for (int i = 0; i < INITIAL_FOXES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Fox fox = new Fox(foxAI, loc);
            world.addItem(fox);
            world.addActor(fox);
        }
    }

    private void addRabbits(World world) {
        RabbitAI rabbitAI = new RabbitAI();
        for (int i = 0; i < INITIAL_RABBITS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Rabbit rabbit = new Rabbit(rabbitAI, loc);
            world.addItem(rabbit);
            world.addActor(rabbit);
        }
    }

    private void addGrievers(World world) {
        FoxAI grieverAI = new FoxAI();
        for (int i = 0; i < INITIAL_GRIEVERS; i++) {
            Location location = Util.getRandomEmptyLocation(world);
            Griever griever = new Griever(grieverAI, location);
            world.addItem(griever);
            world.addActor(griever);
        }
    }

    private void addTrucks(World world) {
        VehicleAI truckAI = new VehicleAI();
        for (int i = 0; i < INITIAL_TRUCKS; i++) {
            Location location = Util.getRandomEmptyLocation(world);
            Truck truck = new Truck(truckAI, location);
            world.addItem(truck);
            world.addActor(truck);
        }
    }

    private void addSedans(World world) {
        VehicleAI sedanAI = new VehicleAI();
        for (int i = 0; i < INITIAL_CARS; i++) {
            Location location = Util.getRandomEmptyLocation(world);
            Sedan sedan = new Sedan(sedanAI, location);
            world.addItem(sedan);
            world.addActor(sedan);
        }
    }

    private void addLamborghinis(World world) {
        VehicleAI lamborghiniAI = new VehicleAI();
        for (int i = 0; i < INITIAL_LAMBORGHINIS; i++) {
            Location location = Util.getRandomEmptyLocation(world);
            Lamborghini lamborghini = new Lamborghini(lamborghiniAI, location);
            world.addItem(lamborghini);
            world.addActor(lamborghini);
        }
    }

    private void addWolves(World world) {
        WolfAI wolfAI = new WolfAI();
        for (int i = 0; i < INITIAL_WOLVES; i++) {
            Location location = Util.getRandomEmptyLocation(world);
            Wolf wolf = new Wolf(wolfAI, location);
            world.addItem(wolf);
            world.addActor(wolf);
        }
    }

    private void addBears(World world) {
        BearAI bearAI = new BearAI();
        for (int i = 0; i < INITIAL_BEARS; i++) {
            Location location = Util.getRandomEmptyLocation(world);
            Bear bear = new Bear(bearAI, location);
            world.addItem(bear);
            world.addActor(bear);
        }
    }

    private void addVolcanos(World world) {
        for (int i = 0; i < INITIAL_VOLCANOS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Volcano volcano = new Volcano(loc);
            world.addItem(volcano);
            world.addActor(volcano);
        }
    }
}