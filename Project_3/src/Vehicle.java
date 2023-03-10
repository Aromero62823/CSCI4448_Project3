import java.util.ArrayList;
import java.util.Random;
// Vehicle class will be the parent that the other subclasses will be inheriting from this class.
public class Vehicle implements SysOutPrint {
    // Random will be used for this
    Random random = new Random();

    // Vehicle Attributes:
    //Win count:
    int vWinCount;
    // The sold status
    Boolean sold;
    // The cost of the vehicle
    Double cost;
    //Name of the vehicle
    protected String vehicleName;
    // Sales price
    double salesPrice;
    // bonus for each vehicle
    protected int washBonus;
    protected int repairBonus;
    protected int salesBonus;
    protected int raceWinBonus;

    // race eligibility
    Boolean raceEligible;

    // Added on Addons
    Enums.vehicleAddons vAddon;
    // type of the vehicle
    Enums.vehicleType vType;
    // condition of the vehicle
    Enums.vehicleCondition vCondition;
    // cleanliness of the vehicle
    Enums.vehicleCleanliness vCleanliness;

    // Will take a name for the vehicle with inheritance classes taking from this
    protected Vehicle() {
        // if-statement condition given the condition of the car
        if (vCondition == Enums.vehicleCondition.Used) {
            cost = cost - (cost * 0.2);
        } else if(vCondition == Enums.vehicleCondition.Broken) {
            cost = cost - (cost * 0.6);
        }
        // the vehicle isn't sold
        sold = false;
    }

    // getting the vehicles by the type
    static ArrayList<Vehicle> getRacingTypes(ArrayList<Vehicle> vehicles, Enums.vehicleType t) {
        ArrayList<Vehicle> racingVehicles = new ArrayList<>();
        for(Vehicle i : vehicles) {
            if(i.vType.equals(t) && !i.vCondition.equals(Enums.vehicleCondition.Broken) && racingVehicles.size()!=3) {
                racingVehicles.add(i);
            }
        }
        return racingVehicles;
    }

    // When the interns clean a car, the car object is being called
     boolean cleanCar(int lowerBound, int upperBound, Enums.washTypes w) {
        int chance = random.nextInt(100);
        boolean cleaned = false;

        if(vCleanliness.equals(Enums.vehicleCleanliness.Dirty)) {
            if(chance<=upperBound) {
                vCleanliness = Enums.vehicleCleanliness.values()[vCleanliness.ordinal()-2];
                cleaned=true;
            } else if(chance <= lowerBound) {
                vCleanliness = Enums.vehicleCleanliness.values() [vCleanliness.ordinal() - 1];
                cleaned=true;
            }
        } else {
            if(chance<=lowerBound) {
                print(vehicleName + " just went from " + vCleanliness + " to " + Enums.vehicleCleanliness.values() [vCleanliness.ordinal() + 1]);
                vCleanliness = Enums.vehicleCleanliness.values() [vCleanliness.ordinal() + 1];
                cleaned=true;
            } else if(chance<= upperBound){
                vCleanliness = Enums.vehicleCleanliness.values() [vCleanliness.ordinal() - 1];
            }
        }

        int externalChance = random.nextInt(100);
        if(w.equals(Enums.washTypes.Chemical) && externalChance <= 9) {
            vCondition = Enums.vehicleCondition.Broken;
            print("Oh no! " + vehicleName + " just broke!");
        } else if(w.equals(Enums.washTypes.ElbowGrease) && externalChance <= 9) {
            vCondition = Enums.vehicleCondition.New;
            print("The " + vehicleName + " just became Like New!");
        }
        return cleaned;
    }

    Boolean fixVehicle() {
        // temp var
        boolean fixed = false;
        // random val for the percentage
        int fixPer = random.nextInt(10);

        if(vCondition.equals(Enums.vehicleCondition.Broken) || vCondition.equals(Enums.vehicleCondition.Used) && fixPer <= 7) {
            // Increment the cleanliness
            vCondition = Enums.vehicleCondition.values()[vCondition.ordinal() - 1];
            //print statement
            if (vCondition.equals(Enums.vehicleCondition.Used)) {
                salesPrice = salesPrice + (salesPrice * 0.5);
            } else if(vCondition.equals(Enums.vehicleCondition.New)) {
                salesPrice = salesPrice + (salesPrice * 0.25);
            }
            fixed = true;
        }

        // Every fix will lower the cleanliness down by 1
        if (vCleanliness.equals(Enums.vehicleCleanliness.Sparkling) || vCleanliness.equals(Enums.vehicleCleanliness.Clean)) {
            vCleanliness = Enums.vehicleCleanliness.values()[vCleanliness.ordinal() + 1];
        }
        return fixed;
    }

    // When the vehicle loses
    public void vLost() {
        print(vehicleName + " got damaged during the race!");
        vCondition = Enums.vehicleCondition.Broken;
    }
    //When the vehicle wins
    public void vWin() {
        print(vehicleName + " increased in price!");
        salesPrice = salesPrice + (salesPrice+0.1);
    }

    public void customerAddons(Buyer buyer) {
        if(buyer.addonChance()) {
            switch (buyer.preferredAddon) {
                case ExtendedWarranty -> salesPrice=salesPrice + (salesPrice*0.2);
                case UnderCoating -> salesPrice=salesPrice + (salesPrice*0.05);
                case SatelliteRadio -> salesPrice=salesPrice + (salesPrice*0.05);
                case RoadRescue -> salesPrice=salesPrice + (salesPrice*0.02);
            }
        }
    }
}

// Vehicles //

class PerformanceCar extends Vehicle {
    private static final String[] perfCarNames = {"Armstrong", "Climber", "Runner", "Lifter", "Pusher"};
    private static int id = 0;
    public PerformanceCar() {
        super();
        // The type of Vehicle
        vType = Enums.vehicleType.PerformanceCar;
        // Name
        vehicleName = perfCarNames[random.nextInt(perfCarNames.length)] + '_' + id;
        // Random price between this range
        cost = random.nextDouble(20000, 40001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // The bonuses with the vehicle
        washBonus = 600;
        repairBonus = 1200;
        salesBonus = 750;
        raceWinBonus = 2000;
        raceEligible = true;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

/**
 * Vehicle subclasses
 */

class Car extends Vehicle {
    private static final String[] carNames = {"Jeep", "Cruiser", "Corolla", "Nissan", "Rider"};
    private static int id = 0;
    public Car() {
        super();
        vType = Enums.vehicleType.Car;
        //Name
        vehicleName = carNames[random.nextInt(carNames.length)] + '_' + id;
        // cost is between 10k and 20k
        cost = random.nextDouble(10000, 20001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // The bonuses with the vehicle
        washBonus = 500;
        repairBonus = 1000;
        salesBonus = 500;
        raceEligible = false;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}


class Pickup extends Vehicle {
    private static final String[] pickupNames = {"Mater", "Ford", "CyberTruck", "TowTruck"};
    private static int id = 0;
    public Pickup() {
        super();
        vType = Enums.vehicleType.Pickup;
        //Name
        vehicleName = pickupNames[random.nextInt(pickupNames.length)] + '_' + id;
        // The cost will be between 10000 and 40000
        cost = random.nextDouble(10000, 40001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // The bonuses with the vehicle
        washBonus = 900;
        repairBonus = 1500;
        salesBonus = 950;
        raceWinBonus = 3000;
        raceEligible = false;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

class ElectricCar extends Vehicle {
    private static final String[] elecNames = {"Lightning", "Bolt", "Shock", "Volt"};
    // New attribute miles (60 - 400 miles)
    protected int range = random.nextInt(60, 401);
    private static int id = 0;
    public ElectricCar() {
        super();
        vType = Enums.vehicleType.ElectricCar;
        //Name
        vehicleName = elecNames[random.nextInt(elecNames.length)] + '_' + id;
        // The cost will be between 15000 and 50000
        cost = random.nextDouble(15000, 50001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        // if new, increment miles by 100
        if(vCondition == Enums.vehicleCondition.New) { range += 100; }
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // The bonuses with the vehicle
        washBonus = 700;
        repairBonus = 1250;
        salesBonus = 650;
        raceEligible = false;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

class Motorcycle extends Vehicle {
    private static final String[] motorNames = {"HotRod", "Cycle", "2Wheeler"};
    // New attribute engineSize
    double engineRating = 700 + (random.nextDouble(-2.2, 1))*300;
    private static int id = 0;
    public Motorcycle() {
        super();
        vType = Enums.vehicleType.Motorcycle;
        //Name
        vehicleName = motorNames[random.nextInt(motorNames.length)] + '_' + id;
        // The cost will be between 10000 and 25000
        cost = random.nextDouble(10000, 25001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // The bonuses with the vehicle
        washBonus = 300;
        repairBonus = 800;
        salesBonus = 750;
        raceWinBonus = 2500;
        raceEligible = true;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

class MonsterTruck extends Vehicle {
    private static final String[] monsterNames = {"Zombie", "Bigfoot", "Batman", "Max-D", "Cyborg", "Brutus"};
    private static int id = 0;
    public MonsterTruck() {
        super();
        vType = Enums.vehicleType.MonsterTruck;
        //Name
        vehicleName = monsterNames[random.nextInt(monsterNames.length)] + '_' + id;
        // The cost will be between 80000 and 200000
        cost = random.nextDouble(80000, 200001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // The bonuses with the vehicle
        washBonus = 1000;
        repairBonus = 2000;
        salesBonus = 4000;
        raceWinBonus = 10000;
        raceEligible=true;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}