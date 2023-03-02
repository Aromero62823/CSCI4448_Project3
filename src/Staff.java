import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

// Staff that is made up of 3 distinct group
public abstract class Staff {
    protected static final DecimalFormat df = new DecimalFormat("0.00");
    Random random = new Random();
//Employee Attributes:
    // name of the employee
    String name;
    // wage of the employee
    double wage;
    // the amount of days worked by the employee
    protected int daysWorked;
    // their employment status
    protected boolean status;
    // what they have earned
    protected double totalEarned;
    // Enum from Bruce's code
    Enums.staffType type;

    // Every staff member object will be instantiated
    protected Staff() {
        // Staff type, name, their total, wage, and the amount of days will be initialized in this constructor
        totalEarned = 0.00;
        daysWorked = 0;
        status = true;
    }

    // When a worker quits, sets their status to false
    void quit() {
        System.out.printf("%s has quit the FNDC. They worked %d days and made $%s.\n", name, daysWorked, df.format(totalEarned));
        status = false;
    }
    // Get employment status of worker
    boolean getStatus() {
        return status;
    }

    // Incrementing the days worked by the employees by 1. 8-hour shifts
    void endDay() {
        daysWorked++;
        totalEarned = totalEarned + (wage * 8);
    }
}

// Employees //

class Mechanics extends Staff {
    static int id = 0;
    public Mechanics() {
        super();
        name = "Mechanic_"+id;
        type = Enums.staffType.Mechanic;
        wage = 17.00;
        id++;
    }
    // Instantiate the same object with new values
    public Mechanics (Interns intern) {
        name = intern.name;
        totalEarned = intern.totalEarned;
        daysWorked = intern.daysWorked;
        status = true;
    }

    public double fix(ArrayList<Vehicle> inventory) {
        // Random vehicle from vehicle arraylist
        int randVehicle = random.nextInt(inventory.size());
        // Prior:
        Enums.vehicleCondition before = inventory.get(randVehicle).vCondition;
        if(inventory.get(randVehicle).fixVehicle())
        {
            System.out.println(name + " just fixed " + inventory.get(randVehicle).vehicleName +
                    " from " + before + " to " + inventory.get(randVehicle).vCondition);

            System.out.println(name + " just got a bonus of $" + inventory.get(randVehicle).bonus);
            // Increment the bonus
            totalEarned += inventory.get(randVehicle).bonus;
            // return the bonus
            return inventory.get(randVehicle).bonus;
        } else {
            System.out.println(name + " couldn't fix the " + inventory.get(randVehicle).vehicleName);
        }
        return 0;
    }
}


class Interns extends Staff {
    static int id = 0;
    public Interns() {
        super();
        name = "Intern_"+id;
        type = Enums.staffType.Intern;
        wage = 11.50;
        id++;
    }
    // Removing by having the intern's status set to false
    void remove() {
        status = false;
    }

    // parameterizing the washing method
    double wash(ArrayList<Vehicle> inventory) {
        // Randomly pick a vehicle from the 3 types, from the 4 cars
        int carclean = random.nextInt(inventory.size());

        // So I made it that they have a chance to wash 0-2 cars
        if (inventory.get(carclean).vCleanliness != Enums.vehicleCleanliness.Sparkling) {
            // Prior:
            Enums.vehicleCleanliness before = inventory.get(carclean).vCleanliness;
            // Execute cleaning chance
            inventory.get(carclean).cleanCar(); // Cleaning the vehicle
            if(!inventory.get(carclean).equals(before)) {
                System.out.println(name + " just washed the " + inventory.get(carclean).vehicleName +
                        " from " + before + " to " + inventory.get(carclean).vCleanliness);
                if (inventory.get(carclean).vCleanliness == Enums.vehicleCleanliness.Sparkling) {
                    System.out.println(name + " got a bonus of $" + inventory.get(carclean).bonus);
                    // The intern will get a bonus if the cleanliness is now Sparkling
                    totalEarned += inventory.get(carclean).bonus;
                    // Remove from operatingBudget
                    return inventory.get(carclean).bonus;
                }
            }
        }
        return 0;
    }

}

class Salespeople extends Staff {
    static int id = 0;
    public Salespeople() {
        super();
        name = "Sales_"+id;
        type = Enums.staffType.Intern;
        wage = 15.00;
        id++;
    }

    // Instantiate the same object with newly transported variables and values
    public Salespeople (Interns intern) {
        name = intern.name;
        totalEarned = intern.totalEarned;
        daysWorked = intern.daysWorked;
        status = true;
    }

    public double sell(ArrayList<Vehicle> inventory, Buyer cust) {
         // Iterate through the elements in the buyers array
         int chance = random.nextInt(10);
         // save the car
         int carPos = cust.buyCar(inventory);
         Vehicle soldveh = inventory.get(carPos);
         if(chance <= cust.buyingChance) {
             System.out.println(name + " just sold the " + soldveh.vehicleName + " for $" + df.format(soldveh.salesPrice) + " to " + cust.name);
             System.out.println(name + " got a bonus of $" + soldveh.bonus);
             inventory.get(carPos).sold = true;
             totalEarned+=soldveh.bonus;
         } else {
             System.out.println(cust.name + " couldn't decided not to buy the " + soldveh.vehicleName);
         }
         // if the car wasn't sold
         return 0.00;

    }
}