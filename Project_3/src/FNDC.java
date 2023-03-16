import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//FNDC Object that will run when object is created
public class FNDC implements SysOutPrint {
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final Scanner input = new Scanner(System.in);
    //Random instance
    final Random random = new Random();

    // Name of the FNDC
    String name;

    // The starting budget of half a million dollars
    private double operatingBudget;

    // store staff list and inventory with vehicles(also sold)
    protected ArrayList<Staff> storeStaff;
    protected ArrayList<Staff> firedStaff;
    protected ArrayList<Vehicle> inventory;
    protected ArrayList<Vehicle> soldVehicles;

    // FNDC constructor
    FNDC(String name) {
        storeStaff = new ArrayList<>();
        firedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();

        // String and the logger
        this.name = name;

        // Creating the objects and initializing instances
        for(int i = 0;i < 6;i++) {
            inventory.add(new Car());
            inventory.add(new Pickup());
            inventory.add(new PerformanceCar());
            // added the new vehicles
            inventory.add(new ElectricCar());
            inventory.add(new MonsterTruck());
            inventory.add(new Motorcycle());
            // 3 new subclass adds
            inventory.add(new Bicycle());
            inventory.add(new SuperCar());
            inventory.add(new OffRoad());
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
            Log.log("Oh no! We are in debt! Running to the bank!, Added $250,000!");
            operatingBudget+= 250000.00;
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
                Log.log("Just hired a new " + newEmployee.type+ " named " + newEmployee.name);
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
                    case Bicycle -> inventory.set(inventory.indexOf(x), new Bicycle());
                    case SuperCar -> inventory.set(inventory.indexOf(x), new SuperCar());
                    case OffRoad -> inventory.set(inventory.indexOf(x), new OffRoad());
                }
                Vehicle display = inventory.get(position);
                Log.log("Purchased " + display.vCondition + ", " + display.vCleanliness + "  " + display.vehicleName + " for $" + df.format(display.cost));
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
        Log.log("The top employee is " + bestEmployee.name + " has made $" + df.format(bestEmployee.totalEarned));
    }

    /**
     * FNDC functionality
     */
    // When the FNDC opens, call this method, everyday it will instantiate new objects
    protected void opening(Enums.daysInWeek d) {
        if(d.equals(Enums.daysInWeek.Sunday)) { return; }
        Log.log("Opening:");
        checkBalance();
        hireStaff();
        updateInventory();
    }

    //Washing at the FNDC()
    public void washing(Enums.daysInWeek d)
    {
        if(d.equals(Enums.daysInWeek.Sunday)){return;}

        // Prompt the user:
        Log.log("Washing....");
        ArrayList<Staff> interns = Staff.getStaffByType(storeStaff, Enums.staffType.Intern);
        for(Staff x : interns) {
            Interns i = (Interns) x;
            operatingBudget-=i.wash(inventory);
        }
    }

    //Mechanics call to get the action going
    public void repairing(Enums.daysInWeek d) {
        if(d.equals(Enums.daysInWeek.Sunday)) {return;}
        Log.log("Repairing....");
        ArrayList<Staff> mechanics = Staff.getStaffByType(storeStaff, Enums.staffType.Mechanic);
        for(Staff i : mechanics) {
            Mechanics m = (Mechanics) i;
            operatingBudget-=m.fix(inventory);
        }
    }

    //Selling the Vehicles under circumstances
    public void selling(Enums.daysInWeek x) {
        if(x.equals(Enums.daysInWeek.Sunday)) { return; }
        Log.log("Selling....");

        Buyer[] buyers = getBuyers(x);
        Log.log(buyers.length + " buyers showed up today...");

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

    //User will now be using a human-integrated interface to engage with salespeople
    public String userSelling(Buyer user, ArrayList<String> names) {
        Log.log("Welcome to the " + name + '!');
        int vehicleIndex = user.buyCar(inventory);
        Vehicle vehicleChoice = inventory.get(vehicleIndex);

        ArrayList<Staff> sales = Staff.getStaffByType(storeStaff, Enums.staffType.Salesperson);
        int salespersonChoice = 0;

        int userChoice = user.getBuyerMenu();
        while(userChoice!=8) {
            Salespeople personChoice = (Salespeople) sales.get(salespersonChoice%sales.size());
            switch (userChoice) {
                case 1 -> {
                    for(String s:names) {
                        print(names.indexOf(s) + ") " + s);
                    }
                    int fndcChoice = input.nextInt();
                    if(fndcChoice < names.size()) {
                        return names.get(fndcChoice);
                    } else {
                        print("Pick a valid FNDC store location!!!");
                    }
                }
                case 2 -> print("The salesperson's name is " + sales.get(salespersonChoice).name);
                case 3 -> print("The time is " + java.time.LocalTime.now());
                case 4 -> {
                    salespersonChoice+=1;
                    print("Yes, " + sales.get(salespersonChoice%sales.size()).name + " will now serve you.");
                }
                case 5 -> {
                    for(Vehicle v : inventory) {
                        print(inventory.indexOf(v) + ')' + v.vehicleName + ": " + v.vType + ", cost: " + v.salesPrice);
                    }
                    print("Choice of vehicle : ");
                    user.carPos = input.nextInt();
                }
                case 6 -> vehicleChoice.getInfo();
                case 7 -> {
                    operatingBudget+=personChoice.sell(user, inventory);
                    return "Quit";
                }
                default -> print("Please enter a valid integer!");
            }
            userChoice = user.getBuyerMenu();
        }
        return "Quit";
    }

    // Will only be executed on Wednesday and Sunday
    public void raceDay(Enums.daysInWeek x) {
        if(x.equals(Enums.daysInWeek.Wednesday) || x.equals(Enums.daysInWeek.Sunday)) {
            Log.log("\t\t--- Race Day ---");
        } else { return;}

        // Random VALID vehicle type will be chosen
        Vehicle randomVehicle = inventory.get(random.nextInt(inventory.size()));

        if (randomVehicle.raceEligible.equals(false)) {
            do {
                Log.log(randomVehicle.vehicleName);
                randomVehicle = inventory.get(random.nextInt(inventory.size()));
            } while (randomVehicle.raceEligible.equals(false));
        }

        Enums.vehicleType randType = randomVehicle.vType;

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
            Log.log("Length:  " + racingCars.size());
            operatingBudget-=d.race(numPositions.get(position), racingCars.get(drivers.indexOf(d)));
            numPositions.remove(position);
            if(drivers.indexOf(i) + 1 == racingCars.size()) {
                break;
            }
        }
    }

    public void ending() {
        // Incrementing values
        for(Staff i : storeStaff) {
            operatingBudget-=i.endDay();
        }
        //IMPLEMENT QUITTING!
        quitChance();
        Log.log("Closing:");
        Log.log(inventory.size() + " vehicles in inventory.");
        Log.log(soldVehicles.size() + " sold vehicles.");
        Log.log(firedStaff.size() + " employees terminated.");
        commendEmployees();
        // Outputting the Budget in the FNDC
        Log.log("Ending budget: $" + df.format(operatingBudget));
    }

    // Getting the totals made by staff and the operating budget
    public double getStaffTotal() {
        double staffTotal = 0.00;
        for(Staff i : firedStaff) {
            staffTotal+=i.totalEarned;
        }
        for(Staff i : storeStaff) {
            staffTotal+=i.totalEarned;
        }
        return staffTotal;
    }

    public double getOperatingBudget() {
        return operatingBudget;
    }
}
