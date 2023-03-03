import java.util.ArrayList;
import java.util.Random;
// Vehicle class will be the parent that the other subclasses will be inheriting from this class.
public class Vehicle {
    // Random will be used for this
    Random random = new Random();

    // Vehicle Attributes:
    // The sold status
    Boolean sold;
    // The cost of the vehicle
    Double cost;
    //Name of the vehicle
    protected String vehicleName;
    // Sales price
    double salesPrice;
    // bonus for each vehicle
    public int bonus;
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

    void soldCar() {
        sold = true;
    }

    // When the interns clean a car, the car object is being called
    void cleanCar() {
        // The percentage of cleanliness
        int cleanPer = random.nextInt(100);
        // Will only clean the Dirty and Clean cars
        if(vCleanliness.equals(Enums.vehicleCleanliness.Dirty))
        {
            if(cleanPer <= 79)
            {
                vCleanliness = Enums.vehicleCleanliness.Clean;
            } else {
                vCleanliness = Enums.vehicleCleanliness.Sparkling;
            }
        } else if (vCleanliness.equals(Enums.vehicleCleanliness.Clean)) {
            if(cleanPer <= 4)
            {
                vCleanliness = Enums.vehicleCleanliness.Dirty;
            } else {
                vCleanliness = Enums.vehicleCleanliness.Sparkling;
            }
        }
    }

    Boolean fixVehicle() {
        // temp var
        boolean fixed = false;
        // random val for the percentage
        int fixPer = random.nextInt(10);

        if(vCondition.equals(Enums.vehicleCondition.Broken) || vCondition.equals(Enums.vehicleCondition.Used)) {
            if(fixPer <= 7)
            {
                // Increment the cleanliness
                vCondition = Enums.vehicleCondition.values()[vCondition.ordinal() - 1];
                //print statement
                switch (vCondition) {
                    case Used -> salesPrice = salesPrice + (salesPrice * 0.5);
                    case New ->  salesPrice = salesPrice + (salesPrice * 0.25);
                }

                fixed = true;
            }
        }
        // Every fix will lower the cleanliness down by 1
        if (vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
            vCleanliness = Enums.vehicleCleanliness.Clean;
        } else if(vCleanliness.equals(Enums.vehicleCleanliness.Clean)) {
            vCleanliness = Enums.vehicleCleanliness.Dirty;
        }
        return fixed;
    }

}

// Vehicles //

class PerformanceCar extends Vehicle {
    private final String[] perfCarNames = {"Armstrong", "Climber", "Runner", "Lifter", "Pusher"};
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
        // The bonus with the vehicle
        bonus = 15000;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}


class Car extends Vehicle {
    private final String[] carNames = {"Jeep", "Cruiser", "Corolla", "Nissan", "Rider"};
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
        // Bonus for the normal cars when fixed, cleaned, or sold
        bonus = 12000;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}


class Pickup extends Vehicle {
    private final String[] pickupNames = {"Mater", "Ford", "CyberTruck", "TowTruck"};
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
        // Bonus for the Pickup Trucks
        bonus = 13000;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

class electricCar extends Vehicle {
    private final String[] elecNames = {"Lightning", "Bolt", "Shock", "Volt"};
    // New attribute miles (60 - 400 miles)
    protected int range = random.nextInt(60, 401);
    private static int id = 0;
    public electricCar() {
        super();
        vType = Enums.vehicleType.ElecticCar;
        //Name
        vehicleName = elecNames[random.nextInt(elecNames.length)] + '_' + id;
        // The cost will be between 15000 and 50000
        cost = random.nextDouble(15000, 50001);
        // Random state of cleanliness and condition
        vCondition = Enums.vehicleCondition.values()[random.nextInt(3)];
        // if new, increment miles by 100
        if(vCondition == Enums.vehicleCondition.New) { range += 100; }
        vCleanliness = Enums.vehicleCleanliness.values()[random.nextInt(3)];
        // Bonus for the electric cars
        bonus = 12500;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

class Motorcycle extends Vehicle {
    private final String[] motorNames = {"HotRod", "Cycle", "2Wheeler"};
    // New attribute engineSize

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
        // Bonus for the motorcycles
        bonus = 9000;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}

class MonsterTruck extends Vehicle {
    private final String[] monsterNames = {"Zombie", "Bigfoot", "Batman", "Max-D", "Cyborg", "Brutus"};
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
        // Bonus for the motorcycles
        bonus = 30000;
        // sales price is double the cost
        salesPrice = cost * 2;
        id++;
    }
}