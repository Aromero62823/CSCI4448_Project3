import java.text.DecimalFormat;
import java.util.Random;

import static java.lang.System.*;

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
        out.printf("%s has quit the FNDC. They worked %d days and made $%s.\n", name, daysWorked, df.format(totalEarned));
        status = false;
    }

    // Incrementing the days worked by the employees by 1. 8-hour shifts
    void endDay() {
        daysWorked++;
        totalEarned = totalEarned + (wage * 8);
    }
}

// Employees //

class Mechanics extends Staff {
    private static int id = 0;
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

    public double fix() {
        // Random vehicle from vehicle arraylist
        int randVehicle = random.nextInt(FNDC.inventory.size());
        // Prior:
        Enums.vehicleCondition before = FNDC.inventory.get(randVehicle).vCondition;

        if(Boolean.TRUE.equals(FNDC.inventory.get(randVehicle).fixVehicle()))
        {
            out.println(name + " just fixed " + FNDC.inventory.get(randVehicle).vehicleName +
                    " from " + before + " to " + FNDC.inventory.get(randVehicle).vCondition);

            out.println(name + " just got a bonus of $" + FNDC.inventory.get(randVehicle).repairBonus);
            // Increment the bonus
            totalEarned += FNDC.inventory.get(randVehicle).repairBonus;
            // return the bonus
            return FNDC.inventory.get(randVehicle).repairBonus;
        } else {
            out.println(name + " couldn't fix the " + FNDC.inventory.get(randVehicle).vehicleName);
        }
        return 0;
    }

}


class Interns extends Staff implements Wash{
    private static int id = 0;
    protected final Enums.washTypes washType;
    public Interns() {
        super();
        name = "Intern_"+id;
        type = Enums.staffType.Intern;
        wage = 11.50;
        // Assigned a washing method
        washType = Enums.washTypes.values()[FNDC.random.nextInt(Enums.washTypes.values().length)];
        id++;
    }
    // Removing by having the intern's status set to false
    void remove() {
        status = false;
    }

    // parameterizing the washing method
    public void wash(int index){

        washing(name, washType, index);
    }

}

class Salespeople extends Staff {
    private static int id = 0;
    public Salespeople() {
        super();
        name = "Sales_"+id;
        type = Enums.staffType.Salesperson;
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

    public double sell(Buyer cust) {
        // Iterate through the elements in the buyers array
        int chance = random.nextInt(10);
        // save the car
        int carPos = cust.buyCar();
        Vehicle soldveh = FNDC.inventory.get(carPos);
        if(chance <= cust.buyingChance) {
            out.println(name + " just sold the " + soldveh.vehicleName + " for $" + df.format(soldveh.salesPrice) + " to " + cust.name);
            out.println(name + " got a bonus of $" + soldveh.salesBonus);
            FNDC.inventory.get(carPos).sold = true;
            totalEarned+=soldveh.salesBonus;
        } else {
            out.println(cust.name + " couldn't decided not to buy the " + soldveh.vehicleName);
        }
        // if the car wasn't sold
        return 0.00;

    }
}

class Driver extends Staff {
    private static int id = 0;
    protected int winCount = 0;
    // injured as a boolean
    boolean injured = false;
    public Driver() {
        super();
        name = "Driver"+id;
        type = Enums.staffType.Driver;
        wage = 20.80;
        id++;
    }

    // Add lost method() which will be a 30% chance to be injured if the driver came in the last 5 places
    public void lost() {
        int injureChance = random.nextInt(10);

        if(injureChance <= 3) {
            out.println(name + " got injured!");
            injured = true;
        }
    }

    public void race( int carPosition, int position) {
        out.println(name + " just finished in " + position + " place with the " + FNDC.inventory.get(carPosition).vehicleName);
        if(position > 14) {
            FNDC.inventory.get(carPosition).vLost();
            lost();
        } else if(position < 4) {
            out.println(name + " is a winner and got a bonus of $" + FNDC.inventory.get(carPosition).raceWinBonus + '!');
            totalEarned+= FNDC.inventory.get(carPosition).raceWinBonus;
            FNDC.operatingBudget+= FNDC.inventory.get(carPosition).raceWinBonus;
            winCount++;
            FNDC.inventory.get(carPosition).vWin();
            FNDC.inventory.get(carPosition).vWinCount++;
        }
    }

}