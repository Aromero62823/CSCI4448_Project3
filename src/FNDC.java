import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

//FNDC Object that will run when object is created
public class FNDC {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    //Random instance
    Random random = new Random();

    // The starting budget of half a million dollars
    private double operatingBudget;

    // There must be 3 of each type to begin
    private static Mechanics[] mechs = new Mechanics[3];
    private static Interns[] ints = new Interns[3];
    private static Salespeople[] salesp = new Salespeople[3];

    // Every vehicle sold will be stored
    public static ArrayList<Vehicle> inventory;
    // Every vehicle bought will also be stored
    private static ArrayList<Vehicle> soldVehicles;

    // FNDC constructor
    public FNDC() {
        // Set the inventory to 0 objects inside
        inventory = new ArrayList<>(){
            {
                add(new PerformanceCar());add(new PerformanceCar());add(new PerformanceCar());add(new PerformanceCar());
                add(new Car());add(new Car());add(new Car());add(new Car());
                add(new Pickup());add(new Pickup());add(new Pickup());add(new Pickup());
            }
        };

        for(int i = 0;i < 3;i++) {
            mechs[i] = new Mechanics();
            ints[i] = new Interns();
            salesp[i] = new Salespeople();
        }

        // Set the sold vehicles
        soldVehicles = new ArrayList<>();
        // Set the amount
        operatingBudget = 500000.00;
    }

    // When the FNDC opens, call this method, everyday it will instantiate new objects
    void Opening() throws InterruptedException {
        // Checking the budget on opening day
        if(operatingBudget <= 0)
        {
            System.out.println("Oh no! We are in debt! Running to the bank!, Added $250,000!");
            // When the program executes, it would make it dramatic!!!
            Thread.sleep(1000);
            operatingBudget = operatingBudget + 250000;
        }
        // Outputting the Budget in the FNDC
        System.out.println("Opening...(Current budget $" + df.format(operatingBudget) + ')');

        // Mechanics and salespeople, checking every array if there has been anyone that was fired
        for(int i = 0;i < 3;i++)
        {
            //Checking for which mechanic quit so that I can promote an intern
            if(!mechs[i].status)
            {
                for(int j = 0;j < 3;j++)
                {
                    // if the interns status isn't fired, meaning that it is true
                    if(ints[j].getStatus())
                    {
                        mechs[i] = new Mechanics(ints[j]);
                        System.out.println(ints[j].name + " just got promoted to a Mechanic!");
                        // set the intern to be removed and re-incremented when the FNDC opens
                        ints[j].remove();
                        break;
                    }
                }
            }
            // Checking on the Salespeople to promote an intern
            if(!salesp[i].status)
            {
                for(int j= 0;j < 3;j++)
                {
                    if(ints[j].getStatus())
                    {
                        salesp[i] = new Salespeople(ints[j]);
                        System.out.println(ints[j].name + " just got promoted to Salesperson!");
                        ints[j].remove();
                        break;
                    }
                }
            }

        }

        //Checking if all staff types have enough interns
        for(int i = 0;i < 3;i++) {
            if(!ints[i].status) {
                ints[i] = new Interns();
                System.out.println("Hired intern: " + ints[i].name);
            }
        }


        for(int i = 0;i < inventory.size();i++) {
            if(inventory.get(i).sold) {
                soldVehicles.add(inventory.get(i));

                switch (inventory.get(i).vType) {
                    case PerformanceCar -> inventory.set(i, new PerformanceCar());
                    case Car -> inventory.set(i, new Car());
                    case Pickup -> inventory.set(i, new Pickup());
                }

                Vehicle display = inventory.get(i);
                System.out.println("Purchased " + display.vCondition + ", " + display.vCleanliness + "  " + display.vehicleName + " for $" + df.format(display.cost));
                operatingBudget = operatingBudget - inventory.get(i).cost;
            }
        }

    }

    //Washing at the FNDC()
    void Washing()
    {
        //Prompt the user
        System.out.println("Washing...");
        // Random intern will wash the vehicles
        // Also, the same intern can wash multiple cars while incrementing the budget
        for(int i = 0;i < random.nextInt(12);i++) {
            operatingBudget = operatingBudget - ints[random.nextInt(ints.length)].wash(inventory);
        }
    }

    //Mechanics call to get the action going
    void Repairing() {
        System.out.println("Repairing....");
        for(int i = 0;i < random.nextInt(12);i++) {
            operatingBudget = operatingBudget - mechs[random.nextInt(mechs.length)].fix(inventory);
        }
    }


    //Selling the Vehicles under circumstances
    void Selling(String x) {
        System.out.println("Selling... ");
        // Initializing the array of buyers chances of getting a vehicle
        Buyer[] buyers;

        // Initializing the size of the array of objects given the day
        if(x.equals("Friday") || x.equals("Saturday")) {
            buyers = new Buyer[random.nextInt(2, 9)];
        } else {
            buyers = new Buyer[random.nextInt(6)];
        }

        if(buyers.length == 0) {
            System.out.println("No buyers showed up today....");
        } else {
            System.out.println(buyers.length + " showed up today...");
        }

        //instantiating the objects in array
        for(int i = 0;i < buyers.length;i++) {
            buyers[i] = new Buyer();
        }

        for(Buyer buy : buyers) {
            operatingBudget = operatingBudget - salesp[random.nextInt(salesp.length)].sell(inventory, buy);
        }
    }

    void Ending() {
        // Incrementing values
        for(int i = 0;i < 3;i++) {
            mechs[i].endDay();
            ints[i].endDay();
            salesp[i].endDay();
        }
        /*
         Having the chance that a staff member quits
        */
        int quitChance = random.nextInt(10);
        int randEmp = random.nextInt(3);
        if(quitChance < 1)
        {
            mechs[randEmp].quit();
        }
        quitChance = random.nextInt(10);
        randEmp = random.nextInt(3);
        if(quitChance < 1)
        {
            salesp[randEmp].quit();
        }
        quitChance = random.nextInt(10);
        randEmp = random.nextInt(3);
        if(quitChance < 1)
        {
            ints[randEmp].quit();
        }

        System.out.println(inventory.size() + " vehicles in inventory.");
        System.out.println(soldVehicles.size() + " sold vehicles.");

    }
}
