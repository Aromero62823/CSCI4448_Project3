import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

// Staff that is made up of 3 distinct group
public abstract class Staff implements SysOutPrint{
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

    // Getting an array list of instances from the staff list
    static ArrayList<Staff> getStaffByType(ArrayList<Staff> staffList, Enums.staffType s) {
        ArrayList<Staff> subInstances = new ArrayList<>();
        for(Staff i : staffList) {
            if(i.type.equals(s)) {
                subInstances.add(i);
            }
        }
        return subInstances;
    }

    // When a worker quits, sets their status to false
    void quit() {
        print(name + "has quit the FNDC. They worked "+ daysWorked +  " days and made $" + df.format(totalEarned));
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

    public double fix(ArrayList<Vehicle> vehicles) {
        double repairBonusTotal = 0;
        // Random vehicle from vehicle arraylist
        for(int i = 0;i < random.nextInt(1,3);i++) {
            int randVehicle = random.nextInt(vehicles.size());
            // Prior:
            Enums.vehicleCondition before = vehicles.get(randVehicle).vCondition;

            if (Boolean.TRUE.equals(vehicles.get(randVehicle).fixVehicle())) {
                print(name + " just fixed " + vehicles.get(randVehicle).vehicleName +
                        " from " + before + " to " + vehicles.get(randVehicle).vCondition);

                print(name + " just got a bonus of $" + vehicles.get(randVehicle).repairBonus);
                // Increment the bonus
                totalEarned += vehicles.get(randVehicle).repairBonus;
                repairBonusTotal += vehicles.get(randVehicle).repairBonus;
            } else {
                print(name + " couldn't fix the " + vehicles.get(randVehicle).vehicleName);
            }
        }
        return repairBonusTotal;
    }

}


class Interns extends Staff {
    private static int id = 0;
    protected final Enums.washTypes washType;
    public Interns() {
        super();
        name = "Intern_"+id;
        type = Enums.staffType.Intern;
        wage = 11.50;
        // Assigned a washing method
        washType = Enums.washTypes.values()[random.nextInt(Enums.washTypes.values().length)];
        id++;
    }

    // parameterizing the washing method
    public double wash(ArrayList<Vehicle> vehicles) {
        int randVehicle = random.nextInt(vehicles.size());
        Vehicle vehicle = vehicles.get(randVehicle);
        double totalWashBonus = 0;
        int lowerBound = 0;
        int upperBound = 0;

        for(int i = 0;i < random.nextInt(1, 3);i++) {
            print(name + " is washing using the " + washType + " method!");
            if(!vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
                switch (washType) {
                    case Chemical -> {
                        if (vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Dirty)) {
                            lowerBound = 79;
                            upperBound = 9;
                        } else if (vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Clean)) {
                            lowerBound = 9;
                            upperBound = 19;
                        }
                        if (vehicles.get(randVehicle).cleanCar(lowerBound, upperBound, washType)) {
                            if (vehicles.get(randVehicle).vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
                                print(name + " just got a bonus of $" + vehicle.washBonus + " for cleaning " + vehicle.vehicleName);
                                totalEarned += vehicle.washBonus;
                                totalWashBonus += vehicle.washBonus;
                            }
                            print(name + " washed " + vehicle.vehicleName + " from " + vehicle.vCleanliness +
                                    " to " + vehicles.get(randVehicle).vCondition);
                        } else {
                            print(name + " couldn't wash the vehicle...");
                        }
                    }


                    case ElbowGrease -> {
                        if (vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Dirty)) {
                            lowerBound = 69;
                            upperBound = 4;
                        } else if (vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Clean)) {
                            lowerBound = 14;
                            upperBound = 28;
                        }
                        if (vehicles.get(randVehicle).cleanCar(lowerBound, upperBound, washType)) {
                            if (vehicles.get(randVehicle).vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
                                print(name + " just got a bonus of $" + vehicle.washBonus + " for cleaning " + vehicle.vehicleName);
                                totalEarned += vehicle.washBonus;
                                totalWashBonus += vehicle.washBonus;
                            }
                            print(name + " washed " + vehicle.vehicleName + " from " + vehicle.vCleanliness +
                                    " to " + vehicles.get(randVehicle).vCondition);
                        } else {
                            print(name + " couldn't wash the vehicle...");
                        }
                    }
                    case Detailed -> {
                        if (vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Dirty)) {
                            lowerBound = 59;
                            upperBound = 19;
                        } else if (vehicle.vCleanliness.equals(Enums.vehicleCleanliness.Clean)) {
                            lowerBound = 4;
                            upperBound = 39;
                        }
                        if (vehicles.get(randVehicle).cleanCar(lowerBound, upperBound, washType)) {
                            if (vehicles.get(randVehicle).vCleanliness.equals(Enums.vehicleCleanliness.Sparkling)) {
                                print(name + " just got a bonus of $" + vehicle.washBonus + " for cleaning " + vehicle.vehicleName);
                                totalEarned += vehicle.washBonus;
                                totalWashBonus += vehicle.washBonus;
                            }
                            print(name + " washed " + vehicle.vehicleName + " from " + vehicle.vCleanliness +
                                    " to " + vehicles.get(randVehicle).vCondition);
                        } else {
                            print(name + " couldn't wash the vehicle...");
                        }

                    }
                }
            } else { print(vehicle.vehicleName + " is already Sparkling!"); }
        }
        return totalWashBonus;
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

    public double sell(Buyer cust, ArrayList<Vehicle> vehicles) {
        double salestotal = 0;
        // Iterate through the elements in the buyers array
        int chance = random.nextInt(10);
        // save the car
        int carPos = cust.buyCar(vehicles);

        Vehicle soldveh = vehicles.get(carPos);

        if(chance <= cust.buyingChance) {
            print(name + " just sold the " + soldveh.vehicleName + " for $" + df.format(soldveh.salesPrice) + " to " + cust.name);
            print(name + " got a bonus of $" + soldveh.salesBonus);
            vehicles.get(carPos).customerAddons(cust);
            vehicles.get(carPos).sold = true;
            totalEarned+=soldveh.salesBonus;
            salestotal+=soldveh.salesPrice;
        } else {
            print(cust.name + " couldn't decided not to buy the " + soldveh.vehicleName);
        }
        // if the car wasn't sold
        return salestotal;

    }
}

class Driver extends Staff {
    private static int id = 0;
    protected int winCount = 0;
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
            print(name + " got injured!");
            status = false;
        }
    }

    public double race(int position, Vehicle vehicle) {
        print(name + " just finished in " + position + " place with the " + vehicle.vehicleName);
        if(position > 14) {
            vehicle.vLost();
            lost();
        } else if(position < 4) {
            print(name + " is a winner and got a bonus of $" + vehicle.raceWinBonus + '!');
            totalEarned+= vehicle.raceWinBonus;
            winCount++;
            vehicle.vWin();
            vehicle.vWinCount++;
            return vehicle.raceWinBonus;
        }
        return 0;
    }

}