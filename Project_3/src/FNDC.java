import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

//FNDC Object that will run when object is created
public class FNDC implements SysOutPrint {
    private final DecimalFormat df = new DecimalFormat("0.00");
    //Random instance
    final Random random = new Random();

    // The starting budget of half a million dollars
    private double operatingBudget;

    // store staff list and inventory with vehicles(also sold)
    protected ArrayList<Staff> storeStaff;
    protected ArrayList<Staff> firedStaff;
    protected ArrayList<Vehicle> inventory;
    protected ArrayList<Vehicle> soldVehicles;

    // FNDC constructor
    FNDC() {
        storeStaff = new ArrayList<>();
        firedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();

        // Creating the objects and initializing instances
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
            storeStaff.add(new Mechanics());
            storeStaff.add(new Salespeople());
            storeStaff.add(new Driver());
            storeStaff.add(new Interns());
        }

        operatingBudget=5000000.00;
    }

    //private methods for helping out public methods
    private void checkBalance() {
        // Checking the budget on opening day
        if(operatingBudget < 0)
        {
            print("Oh no! We are in debt! Running to the bank!, Added $250,000!");
            operatingBudget+= 250000;
        }
    }

    // Hiring new staff
    private void hireStaff() {
        // interns have a remove
        for(Staff i : storeStaff) {
            if(!i.status) {
                int position = storeStaff.indexOf(i);
                firedStaff.add(i);
                switch (i.type) {
                    case Intern -> storeStaff.set(storeStaff.indexOf(i), new Interns());
                    case Mechanic -> storeStaff.set(storeStaff.indexOf(i), new Mechanics());
                    case Salesperson -> storeStaff.set(storeStaff.indexOf(i), new Salespeople());
                    case Driver -> storeStaff.set(storeStaff.indexOf(i), new Driver());
                }
                Staff newEmployee = storeStaff.get(position);
                print("Just hired a new " + newEmployee.type+ " named " + newEmployee.name);
            }
        }
    }

    // updating the inventory, operating budget, and the sold vehicles array list
    private void updateInventory() {
        for(Vehicle x : inventory) {
            if(Boolean.TRUE.equals(x.sold)) {
                soldVehicles.add(x);
                int position = inventory.indexOf(x);
                switch (x.vType) {
                    case Car -> inventory.set(inventory.indexOf(x), new Car());
                    case Pickup -> inventory.set(inventory.indexOf(x), new Pickup());
                    case PerformanceCar -> inventory.set(inventory.indexOf(x), new PerformanceCar());
                    case ElectricCar -> inventory.set(inventory.indexOf(x), new ElectricCar());
                    case Motorcycle -> inventory.set(inventory.indexOf(x), new Motorcycle());
                    case MonsterTruck -> inventory.set(inventory.indexOf(x), new MonsterTruck());
                }
                Vehicle display = inventory.get(position);
                print("Purchased " + display.vCondition + ", " + display.vCleanliness + "  " + display.vehicleName + " for $" + df.format(display.cost));
                operatingBudget-=display.cost;
            }
        }
    }
    // Getting a list of buyers depending on the day of the week
    private Buyer[] getBuyers(Enums.daysInWeek w) {
        Buyer[] buyersList;
        // Initializing the size of the array of objects given the day
        if(w.equals(Enums.daysInWeek.Friday) || w.equals(Enums.daysInWeek.Saturday)) {
            buyersList = new Buyer[random.nextInt(2, 9)];
        } else {
            buyersList = new Buyer[random.nextInt(6)];
        }
        for(int i = 0;i < buyersList.length;i++) { buyersList[i] = new Buyer();}
        return buyersList;
    }

    // quitting chance of the employees
    private void quitChance(){
        int length = random.nextInt(3);
        for(int i = 0;i < length;i++) {
            storeStaff.get(random.nextInt(storeStaff.size())).quit();
        }
    }

    // Commending the best employees for their type for each day
    private void commendEmployees() {
        double max = 0;
        Staff bestEmployee = storeStaff.get(0);
        for(Staff i : storeStaff) {
            if(i.totalEarned > max) {
                max = i.totalEarned;
                bestEmployee = i;
            }
        }
        print("The top employee is " + bestEmployee.name + " has made $" + bestEmployee.totalEarned);

    }

    /**
     * FNDC functionality
     */
    // When the FNDC opens, call this method, everyday it will instantiate new objects
    protected void opening() {
        print("Opening...");
        checkBalance();
        hireStaff();
        updateInventory();
    }

    //Washing at the FNDC()
    public void washing()
    {
        // Prompt the user:
        print("Washing...");
        ArrayList<Staff> interns = Staff.getStaffByType(storeStaff, Enums.staffType.Intern);
        for(Staff x : interns) {
            Interns i = (Interns) x;
            operatingBudget-=i.wash(inventory);
        }
    }

    //Mechanics call to get the action going
    public void repairing() {
        print("Repairing....");
        ArrayList<Staff> mechanics = Staff.getStaffByType(storeStaff, Enums.staffType.Mechanic);
        for(Staff i : mechanics) {
            Mechanics m = (Mechanics) i;
            operatingBudget-=m.fix(inventory);
        }
    }

    //Selling the Vehicles under circumstances
    public void selling(Enums.daysInWeek x) {
        print("Selling....");
        Buyer[] buyers = getBuyers(x);
        print(buyers.length + " buyers showed up today...");

        ArrayList<Staff> sales = Staff.getStaffByType(storeStaff, Enums.staffType.Salesperson);
        for(int j = 0;j < buyers.length;j++) {
            for(Staff i : sales) {
                Salespeople s = (Salespeople) i;
                operatingBudget-=s.sell(buyers[j], inventory);
                if(j == buyers.length -1) {
                    break;
                }
                j++;
            }
        }
    }

    // Will only be executed on Wednesday and Sunday
    public void raceDay(Enums.daysInWeek x) {
        if(x.equals(Enums.daysInWeek.Wednesday) || x.equals(Enums.daysInWeek.Sunday)) {
            print("\t\t--- Race Day ---");
        } else { return;}

        // Random VALID vehicle type will be chosen
        Enums.vehicleType randType = Enums.vehicleType.values()[random.nextInt(Enums.vehicleType.values().length)];

        while (randType.equals(Enums.vehicleType.ElectricCar) || randType.equals(Enums.vehicleType.Car))
        {
            randType = Enums.vehicleType.values()[random.nextInt(Enums.vehicleType.values().length)];
        }

        // Race positions
        ArrayList<Integer> numPositions = new ArrayList<>(20);
        for(int i = 0;i < 20;i++) {numPositions.add(i);}

        ArrayList<Vehicle> racingCars = Vehicle.getRacingTypes(inventory, randType);
        ArrayList<Staff> drivers = Staff.getStaffByType(storeStaff, Enums.staffType.Driver);
        int position;
        //Race:(While assigning vehicle to driver: )
        for(Staff i:drivers) {
            position = random.nextInt(numPositions.size());
            Driver d = (Driver) i;
            operatingBudget-=d.race(numPositions.get(position), racingCars.get(drivers.indexOf(i)));
            numPositions.remove(position);
            if(drivers.indexOf(i) + 1 == racingCars.size()) {
                break;
            }
        }
    }

    public void ending() {
        // Incrementing values
        for(Staff i : storeStaff) {
            i.endDay();
        }
        //IMPLEMENT QUITTING!
        quitChance();
        print(inventory.size() + " vehicles in inventory.");
        print(soldVehicles.size() + " sold vehicles.");
        print(firedStaff.size() + " employees terminated.");
        commendEmployees();
        // Outputting the Budget in the FNDC
        print("Opening...(Current budget $" + df.format(operatingBudget) + ')');


    }
}

