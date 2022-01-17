import java.util.concurrent.atomic.AtomicBoolean;

public class Peasant extends Unit {

    private static final int HARVEST_WAIT_TIME = 100;
    private static final int HARVEST_AMOUNT = 10;

    private AtomicBoolean isHarvesting = new AtomicBoolean(false);
    private AtomicBoolean isBuilding = new AtomicBoolean(false);

    private Peasant(Base owner) {
        super(owner, UnitType.PEASANT);
    }

    public static Peasant createPeasant(Base owner){
        return new Peasant(owner);
    }

    /**
     * Starts gathering gold.
     */
    public void startMining(){
        //Set isHarvesting to true        
        //Start harvesting on a new thread
        //Harvesting: Sleep for HARVEST_WAIT_TIME, then add the resource - HARVEST_AMOUNT
        if(isFree()) {
            isHarvesting.set(true);
            Thread miningThread = new Thread(){
                public void run(){
                    while(isHarvesting.get()) {
                        try {
                            sleep(HARVEST_WAIT_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        transferGold();
                    }
                }                      
            };
            miningThread.start();     
            System.out.println("Peasant starting mining");
        }   

    }

    private void transferGold(){
        this.getOwner().getResources().addGold(HARVEST_AMOUNT);
    }
    /**
     * Starts gathering wood.
     */
    public void startCuttingWood(){
        //Set isHarvesting to true        
        //Start harvesting on a new thread
        //Harvesting: Sleep for HARVEST_WAIT_TIME, then add the resource - HARVEST_AMOUNT
        if(isFree()) {
            isHarvesting.set(true);
            Thread cuttingThread = new Thread(){
                public void run(){
                    while(isHarvesting.get()) {
                        try {
                            sleep(HARVEST_WAIT_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        transferWood();
                    }
                }                      
            };
            cuttingThread.start();     
            System.out.println("Peasant starting cutting wood");
        }

    }

    private void transferWood() {
        this.getOwner().getResources().addWood(HARVEST_AMOUNT);
    }

    /**
     * Peasant should stop all harvesting once this is invoked
     */
    public void stopHarvesting(){
        this.isHarvesting.set(false);
    }

    /**
     * Tries to build a certain type of building.
     * Can only build if there are enough gold and wood for the building
     * to be built.
     *
     * @param buildingType Type of the building
     * @return true, if the building process has started
     *         false, if there are insufficient resources
     */
    public boolean tryBuilding(UnitType buildingType){
        //Start building on a separate thread if there are enough resources
        //Use the Resources class' canBuild method to determine
        //Use the startBuilding method if the process can be started
        if(this.getOwner().getResources().canBuild(buildingType.goldCost, buildingType.woodCost)) {
            Thread buildingThread = new Thread(){
                public void run(){
                    startBuilding(buildingType);
                }                      
            };
            buildingThread.start();
            return true;
        }
        return false;
    }

    /**
     * Start building a certain type of building.
     * Keep in mind that a peasant can only build one building at one time.
     *
     * @param buildingType Type of the building
     */
    private void startBuilding(UnitType buildingType){
        //Ensure that only one building can be built at a time - use isBuilding atomic boolean
        //Building steps: Remove cost, build the building, wait the wait time
        //Use Building's createBuilding method to create the building
        if(isFree()) {
            isBuilding.set(true);
            this.getOwner().getResources().removeCost(buildingType.goldCost, buildingType.woodCost);
            this.getOwner().getBuildings().add(Building.createBuilding(buildingType, this.getOwner()));
            try {
                java.lang.Thread.sleep(buildingType.buildTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isBuilding.set(false);
        }
    }

    /**
     * Determines if a peasant is free or not.
     * This means that the peasant is neither harvesting, nor building.
     *
     * @return Whether he is free
     */
    public boolean isFree(){
        return !isHarvesting.get() && !isBuilding.get();
    }


}
