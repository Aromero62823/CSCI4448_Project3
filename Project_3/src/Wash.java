import static java.lang.System.out;

import java.util.Random;
public interface Wash {
    Random random = new Random();
    default void washing(String name, Enums.washTypes x, int index) {

        // Randomly chosen vehicle from the inventory
        int carclean = random.nextInt(FNDC.inventory.size());

        // So I made it that they have a chance to wash 0-2 cars
        if (!FNDC.inventory.get(carclean).vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
            // Before the cleaning
            Enums.vehicleCleanliness before = FNDC.inventory.get(carclean).vCleanliness;

            out.println(name + " is using " + x + " washing method!");

            // Execute cleaning chance
            FNDC.inventory.get(carclean).cleanCar(FNDC.ints[index].washType, random.nextInt(100), index);

            if (!FNDC.inventory.get(carclean).vCleanliness.equals(before)) {
                out.println(name + " just washed the " + FNDC.inventory.get(carclean).vehicleName +
                        " from " + before + " to " + FNDC.inventory.get(carclean).vCleanliness);

                if (FNDC.inventory.get(carclean).vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
                    out.println(name + " got a bonus of $" + FNDC.inventory.get(carclean).washBonus);
                    // The intern will get a bonus if the cleanliness is now Sparkling
                    FNDC.ints[index].totalEarned += FNDC.inventory.get(carclean).washBonus;
                    // Remove from operatingBudget
                    FNDC.operatingBudget -= FNDC.inventory.get(carclean).washBonus;
                }
            }
        }
    }
}
