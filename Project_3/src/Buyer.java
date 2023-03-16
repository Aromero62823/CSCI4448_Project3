import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Creating the buyer class that will have a new name, preferred typing of vehicle.
public class Buyer implements SysOutPrint {
    String name;
    Enums.vehicleType preferredType;
    Enums.vehicleAddons preferredAddon;
    private static int id = 0;
    int buyingChance;
    int carPos;

    Random random = new Random();

    public Buyer() {
        // Increment values
        name = "Buyer_"+id;
        preferredType = Enums.vehicleType.values()[random.nextInt(3)];
        preferredAddon = Enums.vehicleAddons.values() [random.nextInt(Enums.vehicleAddons.values().length)];
        buyingChance = random.nextInt(10);

        // Instantiating their buying chance
        if(buyingChance <= 6) {
            buyingChance = 6; // 70% chance to buy 0 - 6
        } else if (buyingChance <= 8) {
            buyingChance = 2; // 20% chance to buy 7 - 8
        } else {
            buyingChance = 1; // 10% chance to buy
        }
        id++;
    }

    public Buyer(String name) {
        // Increment values
        this.name = name;
        preferredType = Enums.vehicleType.values()[random.nextInt(3)];
        preferredAddon = Enums.vehicleAddons.values() [random.nextInt(Enums.vehicleAddons.values().length)];
        buyingChance = random.nextInt(10);

        // Instantiating their buying chance
        if(buyingChance <= 6) {
            buyingChance = 6; // 70% chance to buy 0 - 6
        } else if (buyingChance <= 8) {
            buyingChance = 2; // 20% chance to buy 7 - 8
        } else {
            buyingChance = 1; // 10% chance to buy
        }
    }

    public int getBuyerMenu() {
        Scanner input = new Scanner(System.in);

        print("1. I would like to go to another FNDC location?");
        print("2. Ask for the salesperson's name? ");
        print("3. Ask for the time? ");
        print("4. Ask for a different salesperson? ");
        print("5. Ask the salesperson for the inventory(select an item)? ");
        print("6. Ask about the vehicle? ");
        print("7. Buy vehicle? ");
        print("8. End the interaction?");

        return input.nextInt();
    }

    // Given the inventory of the cars, and the preferred vehicle type, will return the inventory with new values
    public int buyCar(ArrayList<Vehicle> vehicles) {
        Vehicle car = new Vehicle();
        // The above arrow is to save the car that it lands on

        //Iterate through the vehicles and pick a car
        for(int i = 0;i < vehicles.size();i++) {
            if(vehicles.get(i).vType.equals(preferredType)
                    && (!vehicles.get(i).vCondition.equals(Enums.vehicleCondition.Broken))
                        && Boolean.TRUE.equals(!vehicles.get(i).sold)) {
                // save the car
                car = vehicles.get(i);
                carPos = i;
                break;
            }
            // changed the buyers preference and restart the loop
            if(i == vehicles.size() - 1) {
                preferredType = Enums.vehicleType.values()[random.nextInt(3)];
                i=0;
            }
        }

        if(car.vCondition.equals(Enums.vehicleCondition.New)) {
            buyingChance++;
        }

        if(car.vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
            buyingChance++;
        }
        return carPos;
    }

    public boolean addonChance() {
        boolean done = false;
        int chance = random.nextInt(100);
        switch (preferredAddon) {
            case ExtendedWarranty -> {
                if(chance < 25) {
                    done=true;
                }
            }
            case UnderCoating -> {
                if(chance < 10) {
                    done=true;
                }
            }
            case RoadRescue -> {
                if(chance < 5) {
                    done=true;
                }
            }
            case SatelliteRadio -> {
                if(chance < 40) {
                    done = true;
                }
            }
        }

        if(Boolean.TRUE.equals(done)) { Log.log(name + " just added the " + preferredAddon + '!' );}
        return done;
    }
}