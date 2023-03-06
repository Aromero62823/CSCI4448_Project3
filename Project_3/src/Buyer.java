import java.util.Random;

// Creating the buyer class that will have a new name, preferred typing of vehicle.
public class Buyer {
    String name;
    Enums.vehicleType preferredType;
    private static int id = 0;
    int buyingChance;

    Random random = new Random();

    public Buyer() {
        // Increment values
        name = "Buyer_"+id;
        preferredType = Enums.vehicleType.values()[random.nextInt(3)];
        buyingChance = random.nextInt(10);

        // Instantiating their buying chance
        if(buyingChance <= 6) {
            buyingChance = 6; // 70% chance to buy 0 - 6
        } else if (buyingChance <= 8) {
            buyingChance = 2; // 20% chance to buy 7 - 8
        } else {
            buyingChance = 1; // 10% chance to buys
        }
        id++;
    }

    // Given the inventory of the cars, and the preferred vehicle type, will return the inventory with new values
    public int buyCar() {
        Vehicle car = new Vehicle();
        int pos = 0;
        // The above arrow is to save the car that it lands on

        //Iterate through the vehicles and pick a car
        for(int i = 0;i < FNDC.inventory.size();i++) {
            if(FNDC.inventory.get(i).vType.equals(preferredType)
                    && (!FNDC.inventory.get(i).vCondition.equals(Enums.vehicleCondition.Broken))
                        && Boolean.TRUE.equals(!FNDC.inventory.get(i).sold)) {
                // save the car
                car = FNDC.inventory.get(i);
                pos = i;
                break;
            }
            // changed the buyers preference and restart the loop
            if(i == FNDC.inventory.size() - 1) {
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
        return pos;
    }
}