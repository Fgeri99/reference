import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Base {

    private static final int STARTER_PEASANT_NUMBER = 5;
    private static final int PEASANT_NUMBER_GOAL = 10;

    // lock to ensure only one unit can be trained at one time
    private final ReentrantLock trainingLock = new ReentrantLock();

    private final String name;
    private final Resources resources = new Resources();
    private final List<Peasant> peasants = Collections.synchronizedList(new LinkedList<>());
    private final List<Building> buildings = Collections.synchronizedList(new LinkedList<>());

    public Base(String name){
        this.name = name;
        //Create the initial 5 peasants - Use the STARTER_PEASANT_NUMBER constant
        //3 of them should mine gold
        //1 of them should cut tree
        //1 should do nothing
        //Use the createPeasant() method
        for(int i = 0; i < STARTER_PEASANT_NUMBER; i++) {
            Peasant p = createPeasant();
            peasants.add(p);
            if(i < 3) {
                peasants.get(i).startMining();
            }
            if(i == 3) {
                peasants.get(i).startCuttingWood();
            }
        }
    }

    public void startPreparation(){
        //Start the building and training preparations on separate threads
        //Tip: use the hasEnoughBuilding method

        //Build 3 farms - use getFreePeasant() method to see if there is a peasant without any work

        //Create remaining 5 peasants - Use the PEASANT_NUMBER_GOAL constant
        //5 of them should mine gold
        //2 of them should cut tree
        //3 of them should do nothing
        //Use the createPeasant() method

        Thread peasantMakingThread = new Thread(){
            public void run(){
                for(int i = STARTER_PEASANT_NUMBER; i < PEASANT_NUMBER_GOAL; i++) {
                    Peasant p;
                    do{
                        p = createPeasant();
                    } while(p == null);
                    peasants.add(p);
                    if(i == 5 || i == 6) {
                        peasants.get(i).startMining();
                    }
                    if(i == 7) {
                        peasants.get(i).startCuttingWood();
                    }
                }
            }                      
        };
        peasantMakingThread.start();        

        //Build a lumbermill - use getFreePeasant() method to see if there is a peasant without any work
        //Build a blacksmith - use getFreePeasant() method to see if there is a peasant without any work

        Thread baseBuildingThread = new Thread(){
            public void run(){
                while(!hasEnoughBuilding(UnitType.FARM, 3)) {
                    Peasant farmBuilder = getFreePeasant();
                    if(farmBuilder != null) {
                        farmBuilder.tryBuilding(UnitType.FARM);
                    }
                }
                while(!hasEnoughBuilding(UnitType.LUMBERMILL, 1)) {
                    Peasant millBuilder = getFreePeasant();
                    if(millBuilder != null) {
                        millBuilder.tryBuilding(UnitType.LUMBERMILL);
                    }
                }
                while(!hasEnoughBuilding(UnitType.BLACKSMITH, 1)) {
                    Peasant smithBuilder = getFreePeasant();
                    if(smithBuilder != null) {
                        smithBuilder.tryBuilding(UnitType.BLACKSMITH);
                    }                   
                }
            }                      
        };
        baseBuildingThread.start();        

        //Wait for all the necessary preparations to finish

        try {
            peasantMakingThread.join();
            baseBuildingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Stop harvesting with the peasants once everything is ready

        for(Peasant peasant: peasants) {
            peasant.stopHarvesting();
        }

        System.out.println(this.name + " finished creating a base");
        System.out.println(this.name + " peasants: " + this.peasants.size());
        for(Building b : buildings){
            System.out.println(this.name + " has a  " + b.getUnitType().toString());
        }

    }


    /**
     * Returns a peasants that is currently free.
     * Being free means that the peasant currently isn't harvesting or building.
     *
     * @return Peasant object, if found one, null if there isn't one
     */
    private Peasant getFreePeasant(){
        //implement - use the peasant's isFree() method
        synchronized(peasants) {
            for(Peasant peasant: peasants) {
                if(peasant.isFree()) {
                    return peasant;
                }
            }
        }
        return null;
    }

    /**
     * Creates a peasant.
     * A peasant could only be trained if there are sufficient
     * gold, wood and food for him to train.
     *
     * At one time only one Peasant can be trained.
     *
     * @return The newly created peasant if it could be trained, null otherwise
     */
    private Peasant createPeasant(){
        Peasant result;
        if(resources.canTrain(UnitType.PEASANT.goldCost, UnitType.PEASANT.woodCost, UnitType.PEASANT.foodCost)){

            //Sleep as long as it takes to create a peasant - use sleepForMsec() method
            //Remove costs
            //Update capacity
            //Use the Peasant class' createPeasant method to create the new Peasant
            //Remember that at one time only one peasant can be trained

            trainingLock.lock();
            try {
                sleepForMsec(UnitType.PEASANT.buildTime);
                resources.removeCost(UnitType.PEASANT.goldCost, UnitType.PEASANT.woodCost);
                resources.updateCapacity(UnitType.PEASANT.foodCost);
                result = Peasant.createPeasant(this);
            } finally {
                trainingLock.unlock();
            }

            return result;
        }
        return null;
    }

    public Resources getResources(){
        return this.resources;
    }

    public List<Building> getBuildings(){
        return this.buildings;
    }

    public String getName(){
        return this.name;
    }

    /**
     * Helper method to determine if a base has the required number of a certain building.
     *
     * @param unitType Type of the building
     * @param required Number of required amount
     * @return true, if required amount is reached (or surpassed), false otherwise
     */
    private boolean hasEnoughBuilding(UnitType unitType, int required){
        //check in the buildings list if the type has reached the required amount
        int sum = 0;
        synchronized(buildings) {
            for(Building building : buildings) {
                if(building.getUnitType().equals(unitType)) {
                    sum = sum + 1;
                }
            }
        }        
        if(sum >= required) {
            return true;
        }
        return false;
    }

    private static void sleepForMsec(int sleepTime) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }

}
