import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.System.*;

//FNDC Object that will run when object is created
public class FNDC {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    //Random instance
    static final Random random = new Random();

    // The starting budget of half a million dollars
    protected static double operatingBudget = 500000.00;

    // There must be 3 of each type to begin
    protected static final Mechanics[] mechanics = new Mechanics[3];
    protected static final Interns[] ints = new Interns[3];
    protected static final Salespeople[] salespeople = new Salespeople[3];
    protected static final Driver[] drivers = new Driver[3];

    // Every vehicle sold will be stored
    protected static ArrayList<Vehicle> inventory = new ArrayList<>();
    // Every vehicle bought will also be stored
    protected static ArrayList<Vehicle> soldVehicles;

    // FNDC constructor
    protected FNDC() {
        for(int i = 0;i < 4;i++) {
            inventory.add(new Car());
            inventory.add(new Pickup());
            inventory.add(new PerformanceCar());
            // added the new vehicles
            inventory.add(new ElectricCar());
            inventory.add(new MonsterTruck());
            inventory.add(new Motorcycle());
        }

        for(int i = 0;i < 3;i++) {
            mechanics[i] = new Mechanics();
            salespeople[i] = new Salespeople();
            ints[i] = new Interns();
            drivers[i] = new Driver();
        }

        // Set the sold vehicles
        soldVehicles = new ArrayList<>();
    }

    //private methods for helping out public methods
    private static void checkBalance() {
        // Checking the budget on opening day
        if(operatingBudget <= 0)
        {
            out.println("Oh no! We are in debt! Running to the bank!, Added $250,000!");
            operatingBudget+= 250000;
        }
    }
    // When the FNDC opens, call this method, everyday it will instantiate new objects
    protected static void opening() {
        out.println("Opening...");
        checkBalance();
        // Mechanics and salespeople, checking every array if there has been anyone that was fired
        for(int i = 0;i < 3;i++)
        {
            //Checking for which mechanic quit so that I can promote an intern
            if(!mechanics[i].status)
            {
                for(int j = 0;j < 3;j++)
                {
                    // if the interns status isn't fired, meaning that it is true
                    if(ints[j].status)
                    {
                        mechanics[i] = new Mechanics(ints[j]);
                        out.println(ints[j].name + " just got promoted to a Mechanic!");
                        // set the intern to be removed and re-incremented when the FNDC opens
                        ints[j].remove();
                        break;
                    }
                }
            }
            // Checking on the Salespeople to promote an intern
            if(!salespeople[i].status)
            {
                for(int j= 0;j < 3;j++)
                {
                    if(ints[j].status)
                    {
                        salespeople[i] = new Salespeople(ints[j]);
                        out.println(ints[j].name + " just got promoted to Salesperson!");
                        ints[j].remove();
                        break;
                    }
                }
            }
        }

        for(int i = 0;i < ints.length;i++) {
            if(drivers[i].injured) {
                out.println(drivers[i].name + " is too injured to race, they left the FNDC! (Wins: " + drivers[i].winCount + ')');
                drivers[i] = new Driver();
                out.println(drivers[i].name + " just got hired and is ready to race!");
            }
            if(!ints[i].status) {
                ints[i] = new Interns();
                out.println(ints[i].name + " just got hired!");
            }
        }

    // Checking the inventory for sold vehicles
        for(int i = 0;i < inventory.size();i++) {
            if(Boolean.TRUE.equals(inventory.get(i).sold)) {
                // add to the sold vehicles
                soldVehicles.add(inventory.get(i));
                // switch statement to get new vehicles
                switch (inventory.get(i).vType) {
                    case PerformanceCar -> inventory.set(i, new PerformanceCar());
                    case Car -> inventory.set(i, new Car());
                    case Pickup -> inventory.set(i, new Pickup());
                    // Added the new vehicles to be bought if sold
                    case ElectricCar -> inventory.set(i, new ElectricCar());
                    case MonsterTruck -> inventory.set(i, new MonsterTruck());
                    case Motorcycle -> inventory.set(i, new Motorcycle());
                }

                Vehicle display = inventory.get(i);
                out.println("Purchased " + display.vCondition + ", " + display.vCleanliness + "  " + display.vehicleName + " for $" + df.format(display.cost));
                operatingBudget = operatingBudget - inventory.get(i).cost;
            }
        }

    }

    //Washing at the FNDC()
    public static void washing()
    {
        //Prompt the user
        out.println("Washing...");
        int intern = random.nextInt(random.nextInt(ints.length));
        // Random intern will wash the vehicles, increment budget as well
        for(int i = 0;i < random.nextInt(3, 12);i++) {
            ints[intern].wash(intern);
            intern = random.nextInt(ints.length);
        }
    }

    //Mechanics call to get the action going
    public static void repairing() {
        out.println("Repairing....");
        for(int i = 0;i < random.nextInt(3, 12);i++) {
            operatingBudget -= mechanics[random.nextInt(mechanics.length)].fix();
        }
    }


    //Selling the Vehicles under circumstances
    public static void selling(Enums.daysInWeek x) {
        out.println("Selling... ");
        // Initializing the array of buyers chances of getting a vehicle
        Buyer[] buyers;

        // Initializing the size of the array of objects given the day
        if(x.equals(Enums.daysInWeek.Friday) || x.equals(Enums.daysInWeek.Saturday)) {
            buyers = new Buyer[random.nextInt(2, 9)];
        } else {
            buyers = new Buyer[random.nextInt(6)];
        }
        out.println(buyers.length + "buyers showed up today...");


        //instantiating the objects in array
        for(int i = 0;i < buyers.length;i++) {
            buyers[i] = new Buyer();
            operatingBudget -= salespeople[random.nextInt(salespeople.length)].sell(buyers[i]);
        }
    }

    // Will only be executed on Wednesday and Sunday
    public static void raceDay(Enums.daysInWeek x) {
        if(x.equals(Enums.daysInWeek.Wednesday) || x.equals(Enums.daysInWeek.Sunday)) {
            out.println("\t\t--- Race Day ---");
        } else { return;}

        ArrayList<Integer> carPos = new ArrayList<>();
        // Random VALID vehicle type will be chosen
        Enums.vehicleType randType = Enums.vehicleType.values()[random.nextInt(Enums.vehicleType.values().length)];

        while (randType.equals(Enums.vehicleType.ElectricCar) || randType.equals(Enums.vehicleType.Car))
        {
            randType = Enums.vehicleType.values()[random.nextInt(Enums.vehicleType.values().length)];
        }

        for(Vehicle i : inventory) {
            if(randType.equals(i.vType) && !i.vCondition.equals(Enums.vehicleCondition.Broken)) {
                carPos.add(inventory.indexOf(i));
                if(carPos.size() == 3) {
                    break;
                }
            }
        }

        // Race positions
        ArrayList<Integer> numPositions = new ArrayList<>(20);
        for(int i = 0;i < 20;i++) {numPositions.add(i);}

        int position;
        //Race:(While assigning vehicle to driver: )
        for(int i = 0;i < carPos.size();i++) {
            position = random.nextInt(numPositions.size());
            drivers[i].race(carPos.get(0), numPositions.get(position));
            carPos.remove(0);
            numPositions.remove(position);
        }
    }

    public static void ending() {
        // Incrementing values
        for(int i = 0;i < 3;i++) {
            mechanics[i].endDay();
            ints[i].endDay();
            salespeople[i].endDay();
            drivers[i].endDay();
        }
        /*
         Having the chance that a staff member quits
        */
        int quitChance = random.nextInt(10);
        int randEmp = random.nextInt(3);
        if(quitChance < 1)
        {
            mechanics[randEmp].quit();
        }
        quitChance = random.nextInt(10);
        randEmp = random.nextInt(3);
        if(quitChance < 1)
        {
            salespeople[randEmp].quit();
        }
        quitChance = random.nextInt(10);
        randEmp = random.nextInt(3);
        if(quitChance < 1)
        {
            ints[randEmp].quit();
        }

        out.println(inventory.size() + " vehicles in inventory.");
        out.println(soldVehicles.size() + " sold vehicles.");
        // Outputting the Budget in the FNDC
        out.println("Opening...(Current budget $" + df.format(operatingBudget) + ')');

    }
}
